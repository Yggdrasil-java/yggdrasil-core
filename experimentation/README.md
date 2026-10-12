# Experimentação

Artefato `dev.yggdrasil:yggdrasil-experimentation`, módulo JPMS `dev.yggdrasil.experimentation`.

## Organização

- `domain/`: Experiment, ShadowComparison, Activation, ExperimentRepository e ExperimentAuthorization.
- `application/`: ExperimentationService.
- `infrastructure/`: InMemoryExperimentRepository, adaptador de referência.
- `src/test/java`: contratos observáveis e casos de conflito/isolamento.

## Comportamento

Comparar Live/Shadow, aprovar, promover com versão esperada e reverter para revisões anteriormente Live. Registrar ator e histórico de ativação. O host fornece autorização obrigatória e a revisão inicial já aprovada. Aprovação encerra coleta de novas comparações.

## Verificação

Execute na raiz:

```sh
./mvnw -pl experimentation -am test
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
