# Aplicação antifraude de referência

Consumidor Java 25 independente. Usa o core como dependência Maven e mantém
HTTP, histórico e ações de negócio no host.

## Compilar e executar

Na raiz, com JAVA_HOME configurado:

```sh
./mvnw install
./mvnw -f examples/antifraud-app/pom.xml verify
java --module-path core/target/yggdrasil-core-0.1.0-SNAPSHOT.jar --add-modules dev.yggdrasil.core,jdk.httpserver -cp examples/antifraud-app/target/classes dev.yggdrasil.example.AntifraudApplication 8080 /tmp/yggdrasil-history.tsv
```

No Windows, usar `mvnw.cmd` e um caminho gravável para o histórico.
O servidor escuta apenas em `127.0.0.1`. `POST /evaluate` recebe formulário:

```sh
curl -X POST http://127.0.0.1:8080/evaluate -d 'eventId=16e2e0d8-b656-4b93-a8f8-a4b3eb44045e&accountId=a2e75d70-ea58-492e-8531-a707d9b8b10d&amount=1500&suspicious=false&confirmedFraud=false'
```

A resposta contém decisão REVIEW, score 30 e ação MANUAL_REVIEW. Campos
booleanos omitidos preservam UNKNOWN; fraude confirmada produz BLOCK terminal.
Um evento repetido devolve o resultado original sem nova gravação.
O histórico em arquivo é recuperado ao iniciar; sua frequência integra o input.

## Verificar e medir

```sh
python3 scripts/verify_application.py
python3 scripts/verify_application_in_process.py
```

O primeiro comando inicia o servidor, verifica HTTP, idempotência e recuperação,
e mede 2.000 requisições a 167/s com oito workers. Requer sockets locais.
O segundo executa o mesmo serviço de aplicação diretamente e mede parsing de
campos, consulta de histórico, avaliação, ação e gravação durável. **Exclui HTTP**.

A referência usa uma instância e histórico serializado. Não implementa
armazenamento distribuído, autenticação HTTP, retenção ou recuperação de uma
última gravação interrompida. Não é um serviço de produção pronto para exposição.
Veja [o contrato](../../docs/consumer-application.md).
