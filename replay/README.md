# Replay e backfill

Artefato `dev.yggdrasil:yggdrasil-replay`, módulo JPMS `dev.yggdrasil.replay`.

## Organização

- `domain/`: EvaluationSnapshot, HistoricalInput, ReplayResult, BackfillJob e BackfillRepository.
- `application/`: ReplayService e BackfillService.
- `infrastructure/`: InMemoryBackfillRepository, adaptador de referência.
- `src/test/java`: contratos observáveis e casos de conflito/isolamento.

## Comportamento

Reproduzir revisão/dados/opções capturados e comparar o resultado completo. Simulação aplica a candidata a snapshots ordenados temporalmente. Backfill confirma saída e checkpoint atomicamente por evento, permitindo retomada sem duplicar resultados. Projeções devem ser puras e snapshots não podem conter dados futuros.

## Verificação

Execute na raiz:

```sh
./mvnw -pl replay -am test
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
