# Extensões

Artefato `dev.yggdrasil:yggdrasil-extensions`, módulo JPMS `dev.yggdrasil.extensions`.

## Organização

- `domain/`: ProviderKey, ExtensionProvider, ExtensionRequest, ProviderValue, Resolution e ExtensionStateRepository.
- `application/`: ExtensionRunner.
- `infrastructure/`: InMemoryExtensionState, adaptador de referência.
- `src/test/java`: contratos observáveis e casos de conflito/isolamento.

## Comportamento

Resolver providers por nome/versão exatos com prazo e limite de concorrência. Timeout não libera a capacidade de um worker que ignora interrupção. Valores e argumentos são imutáveis; estado é separado por namespace e versão. Erros não expõem mensagens da fonte.

Use `ScopedState.update` para agregações concorrentes atômicas; a transformação
deve ser pura e curta. `get` seguido de `put` não é uma operação atômica.
Providers devem respeitar `knownAt` e devolver os tipos aceitos por
`EvaluationInput`. Configurações são fornecidas explicitamente pelo host.

## Verificação

Execute na raiz:

```sh
./mvnw -pl extensions -am test
./mvnw verify
python3 scripts/verify_architecture.py
python3 scripts/verify_modular_consumer.py
```

O primeiro comando inclui os módulos necessários. Leia o
[consumidor modular](../examples/modular-consumer/README.md) para composição e
[modules.md](../docs/modules.md) para contratos e dependências.

## Limites

Adaptadores locais não oferecem durabilidade ou coordenação entre processos.
Banco de grafo de produção, frontend e autenticação não fazem parte desta
entrega. A API permanece candidata, sujeita à revisão antes da versão estável.
