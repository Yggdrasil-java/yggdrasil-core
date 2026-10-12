package dev.yggdrasil.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/** Socket-free application checks and measurements; deliberately excludes HTTP. */
public final class ApplicationVerifier {
    public static void main(String[] args) throws Exception {
        Path file = Path.of(args[0]);
        int samples = Integer.parseInt(args[1]);
        var application = new AntifraudApplication(file);
        var first = event(10, false, false);
        String original = application.evaluate(first);
        require(original.contains("\"decision\":\"ALLOW\"") && original.contains("\"action\":\"APPROVED\""), "Allow action");
        require(application.evaluate(event(1500, false, false)).contains("\"decision\":\"REVIEW\""), "Review decision");
        require(application.evaluate(event(1500, true, false)).contains("\"decision\":\"BLOCK\""), "Block decision");
        String terminal = application.evaluate(event(10, false, true));
        require(terminal.contains("\"reason\":\"TERMINAL\"") && terminal.contains("\"partialScore\":true"), "Terminal decision");
        var missing = event(10, false, false); missing.remove("suspicious"); missing.remove("confirmedFraud");
        require(application.evaluate(missing).contains("\"state\":\"UNKNOWN\""), "Unknown input");
        long rows = rows(file);
        require(application.evaluate(first).equals(original), "Idempotent response");
        require(rows(file) == rows, "Idempotent persistence");
        try {
            application.evaluate(Map.of("amount", "invalid"));
            throw new AssertionError("Invalid event accepted");
        } catch (IllegalArgumentException | NullPointerException expected) { }
        application = new AntifraudApplication(file);
        require(application.evaluate(first).equals(original), "Recovery preserves original response");
        String account = UUID.randomUUID().toString();
        for (int i = 0; i < 3; i++) {
            var request = event(10, false, false); request.put("accountId", account); application.evaluate(request);
        }
        var frequent = event(10, false, false); frequent.put("accountId", account);
        require(application.evaluate(frequent).contains("\"score\":20"), "Recovered frequency");
        for (int i = 0; i < 100; i++) application.evaluate(event(10, false, false));
        Map<String, String>[] events = events(samples);
        long[] latency = new long[samples];
        long begin = System.nanoTime();
        for (int i = 0; i < samples; i++) {
            long start = System.nanoTime();
            application.evaluate(events[i]);
            latency[i] = System.nanoTime() - start;
        }
        long elapsed = System.nanoTime() - begin;
        long[] sorted = latency.clone(); Arrays.sort(sorted);
        System.err.println("Application actions, history, idempotency, UNKNOWN and recovery: OK");
        System.out.printf(Locale.ROOT, "{\"kind\":\"in-process-application-with-durable-history\",\"http_included\":false,\"samples\":%d,\"errors\":0,\"warmup_requests\":100,\"elapsed_seconds\":%.6f,\"ops_per_second\":%.2f,\"p50_ns\":%d,\"p95_ns\":%d,\"p99_ns\":%d,\"max_ns\":%d,\"java\":\"%s\",\"raw_latency_ns\":%s}%n",
                samples, elapsed / 1e9, samples * 1e9 / elapsed, percentile(sorted, .50), percentile(sorted, .95),
                percentile(sorted, .99), sorted[samples - 1], System.getProperty("java.version"), Arrays.toString(latency));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String>[] events(int size) {
        Map<String, String>[] events = (Map<String, String>[]) new Map<?, ?>[size];
        String[] accounts = new String[16];
        for (int i = 0; i < accounts.length; i++) accounts[i] = UUID.randomUUID().toString();
        for (int i = 0; i < size; i++) {
            events[i] = event(i % 10 == 0 ? 1500 : 10, i % 25 == 0, false);
            events[i].put("accountId", accounts[i % accounts.length]);
        }
        return events;
    }

    private static Map<String, String> event(int amount, boolean suspicious, boolean confirmed) {
        return new HashMap<>(Map.of("eventId", UUID.randomUUID().toString(), "accountId", UUID.randomUUID().toString(),
                "amount", Integer.toString(amount), "suspicious", Boolean.toString(suspicious), "confirmedFraud", Boolean.toString(confirmed)));
    }
    private static long rows(Path file) throws java.io.IOException {
        try (var lines = Files.lines(file)) { return lines.count(); }
    }
    private static long percentile(long[] values, double fraction) { return values[(int) Math.ceil(values.length * fraction) - 1]; }
    private static void require(boolean valid, String scenario) { if (!valid) throw new AssertionError(scenario); }
}
