# ADR 0003: Persistência em grafo

## Status

Aceito no requisito; ferramenta pendente. Registro: 2026-10-10.

Atualização de 2026-10-12: o responsável escolheu Neo4j.
O [ADR 0027](0027-neo4j-history-adapter.md) registra o adaptador e os limites
da validação. O texto abaixo preserva o contexto da decisão inicial.

## Contexto

A avaliação usa relações e interseções entre identidades, empresas, recursos e eventos.

## Alternativas, prós e contras

Banco de grafo: representa relações e percursos explicitamente; custo de agregações e operação depende do produto e da modelagem. Armazenamento documental: facilita documentos de configuração; relações e consistência exigem desenho próprio.

## Decisão

Banco de grafo obrigatório no módulo de histórico e relações. O consumidor pode usar somente core, sem banco. Este esclarecimento do responsável restringe a obrigatoriedade do grafo ao módulo que utiliza histórico e relações. Nenhum produto foi escolhido.

## Consequências e pendências

Comparar ferramentas com documentação oficial, consultas representativas, índices, transações, cancelamento, licença, custo e operação. A distribuição do histórico e dos agregados permanece pendente; não se assume ganho automático de latência.
