# Histórico e relações

Artefato `dev.yggdrasil:yggdrasil-history`, módulo JPMS `dev.yggdrasil.history`.

## Organização

- `domain/`: EntityId, HistoryEvent, EventWindow, Relation e HistoryRepository.
- `application/`: HistoryService.
- `infrastructure/`: InMemoryHistoryRepository, adaptador de referência.
- `src/test/java`: contratos observáveis e casos de conflito/isolamento.

## Comportamento

Registrar eventos sem duplicação, contar por janelas temporais e consultar vizinhos/interseções. Ocorrência e ingestão são distintas; knownAt limita dados conhecidos. Eventos atuais são explícitos. Relações usam validade semiaberta e tempo de registro.

## Verificação

A suíte `HistoryRepositoryContract` verifica diretamente o port, sem depender
do serviço ou da implementação em memória. Um futuro adaptador deve criar uma
subclasse de teste e implementar `createRepository()` com armazenamento vazio
e isolado para cada teste. Recursos externos devem ser encerrados em
`@AfterEach`. A suíte cobre ordenação, horizontes temporais, conflitos de IDs,
isolamento, relações e escrita concorrente; testes de durabilidade e recuperação
devem ser adicionados junto ao adaptador de banco.

Execute na raiz:

```sh
./mvnw -pl history -am test
./mvnw verify
python3 scripts/verify_architecture.py
python3 scripts/verify_modular_consumer.py
```

O primeiro comando inclui os módulos necessários. Leia o
[consumidor modular](../examples/modular-consumer/README.md) para composição e
[modules.md](../docs/modules.md) para contratos e dependências.

## Limites

Adaptadores locais não oferecem durabilidade ou coordenação entre processos.
O [adaptador Neo4j](../adapters/neo4j/README.md) implementa este port em um
artefato opcional; sua compilação e integração no banco ainda precisam ser
validadas. Frontend e autenticação continuam fora deste módulo.
A API permanece candidata, sujeita à revisão antes da versão estável.
