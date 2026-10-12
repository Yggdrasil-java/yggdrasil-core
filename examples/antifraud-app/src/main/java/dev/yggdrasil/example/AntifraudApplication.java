package dev.yggdrasil.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dev.yggdrasil.core.domain.*;
import dev.yggdrasil.core.application.Evaluator;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

/** Host-owned HTTP, history and actions; the embedded evaluator remains pure. */
public final class AntifraudApplication {
    private final Forest forest;
    private final Evaluator evaluator = new Evaluator();
    private final History history;

    AntifraudApplication(Path historyFile) throws IOException {
        history = new History(historyFile);
        forest = new Forest(UUID.fromString("9d3932c4-245c-4e25-98b7-6c30d5538ed1"), 1, List.of(
                tree("confirmed-fraud", "confirmedFraud", Operator.EQUAL, true, "0", Decision.BLOCK),
                tree("high-amount", "amount", Operator.GREATER_THAN, 1000, "30", null),
                tree("suspicious-device", "suspicious", Operator.EQUAL, true, "50", null),
                tree("frequent-account", "priorEvents", Operator.GREATER_THAN_OR_EQUAL, 3, "20", null)),
                List.of(new Threshold(null, false, new BigDecimal("30"), false, Decision.ALLOW),
                        new Threshold(new BigDecimal("30"), true, new BigDecimal("80"), false, Decision.REVIEW),
                        new Threshold(new BigDecimal("80"), true, null, false, Decision.BLOCK)), Decision.REVIEW, Map.of());
    }

    private static Tree tree(String name, String attribute, Operator operator, Object right, String score, Decision terminal) {
        UUID treeId = UUID.nameUUIDFromBytes(("tree:" + name).getBytes(StandardCharsets.UTF_8));
        UUID leafId = UUID.nameUUIDFromBytes(("leaf:" + name).getBytes(StandardCharsets.UTF_8));
        return new Tree(treeId, name, Leaf.of(leafId, operator, Operand.attribute(attribute), Operand.literal(right)),
                new BigDecimal(score), terminal);
    }

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        Path file = Path.of(args.length > 1 ? args[1] : "target/antifraud-history.tsv");
        var app = new AntifraudApplication(file);
        HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), port), 128);
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        server.setExecutor(executor);
        server.createContext("/evaluate", app::handle);
        server.createContext("/health", exchange -> reply(exchange, 200, "{\"status\":\"UP\"}"));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop(0);
            executor.close();
        }));
        server.start();
        System.out.println("PORT=" + server.getAddress().getPort());
        System.out.flush();
    }

    private void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            reply(exchange, 405, "{\"error\":\"POST required\"}");
            return;
        }
        try {
            byte[] body = exchange.getRequestBody().readNBytes(8193);
            if (body.length > 8192) throw new IllegalArgumentException("Request too large");
            Map<String, String> fields = new HashMap<>();
            for (String pair : new String(body, StandardCharsets.UTF_8).split("&")) {
                String[] parts = pair.split("=", 2);
                if (parts.length != 2) throw new IllegalArgumentException("Invalid form");
                String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
                if (fields.putIfAbsent(key, URLDecoder.decode(parts[1], StandardCharsets.UTF_8)) != null)
                    throw new IllegalArgumentException("Duplicate field");
            }
            reply(exchange, 200, evaluate(fields));
        } catch (IllegalArgumentException | NullPointerException invalidInput) {
            reply(exchange, 400, "{\"error\":\"Invalid event fields\"}");
        } catch (IOException persistenceFailure) {
            reply(exchange, 500, "{\"error\":\"History persistence failed\"}");
        }
    }

    String evaluate(Map<String, String> fields) throws IOException {
        UUID event = UUID.fromString(fields.get("eventId"));
        UUID account = UUID.fromString(fields.get("accountId"));
        BigDecimal amount = new BigDecimal(fields.get("amount"));
        if (amount.precision() > 32 || Math.abs((long) amount.scale()) > 8)
            throw new IllegalArgumentException("Unsupported decimal size");
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("amount", amount);
        addBoolean(fields, attributes, "suspicious");
        addBoolean(fields, attributes, "confirmedFraud");
        String response;
        synchronized (history) {
            response = history.responses.get(event);
            if (response == null) {
                attributes.put("priorEvents", history.counts.getOrDefault(account, 0L));
                EvaluationResult result = evaluator.evaluate(forest, new EvaluationInput(attributes));
                String action = result.decision().equals(Decision.ALLOW) ? "APPROVED"
                        : result.decision().equals(Decision.BLOCK) ? "DECLINED" : "MANUAL_REVIEW";
                response = "{\"eventId\":\"" + event + "\",\"state\":\"" + result.state()
                        + "\",\"decision\":\"" + result.decision().id() + "\",\"score\":" + result.score().toPlainString()
                        + ",\"revision\":" + result.revision() + ",\"partialScore\":" + result.partialScore()
                        + ",\"reason\":\"" + result.reason() + "\",\"action\":\"" + action + "\"}";
                history.append(event, account, response);
            }
        }
        return response;
    }

    private static void addBoolean(Map<String, String> fields, Map<String, Object> attributes, String key) {
        if (!fields.containsKey(key)) return;
        String value = fields.get(key);
        if (!value.equals("true") && !value.equals("false")) throw new IllegalArgumentException("Invalid boolean");
        attributes.put(key, Boolean.valueOf(value));
    }

    private static void reply(HttpExchange exchange, int status, String body) throws IOException {
        try (exchange) {
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
        }
    }

    private static final class History {
        final Path file;
        final Map<UUID, String> responses = new HashMap<>();
        final Map<UUID, Long> counts = new HashMap<>();

        History(Path file) throws IOException {
            this.file = file;
            Path parent = file.toAbsolutePath().getParent();
            Files.createDirectories(parent);
            if (Files.exists(file)) {
                for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                    String[] fields = line.split("\t", 3);
                    if (fields.length != 3) throw new IOException("Invalid history record");
                    UUID event = UUID.fromString(fields[0]);
                    UUID account = UUID.fromString(fields[1]);
                    if (responses.putIfAbsent(event, fields[2]) != null) throw new IOException("Duplicate history event");
                    counts.merge(account, 1L, Long::sum);
                }
            }
        }

        void append(UUID event, UUID account, String response) throws IOException {
            byte[] record = (event + "\t" + account + "\t" + response + "\n").getBytes(StandardCharsets.UTF_8);
            try (FileChannel channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
                ByteBuffer bytes = ByteBuffer.wrap(record);
                while (bytes.hasRemaining()) channel.write(bytes);
                channel.force(true);
            }
            responses.put(event, response);
            counts.merge(account, 1L, Long::sum);
        }
    }
}
