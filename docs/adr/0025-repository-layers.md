# ADR 0025 — Organização por camadas

## Status

Aceito pelo responsável: usar TicketHub como referência e reorganizar também
os pacotes públicos Java. A aprovação da versão estável continua pendente.

## Contexto

O único pacote do core mistura invariantes do domínio com execução. A pasta
de documentação e os comandos de desenvolvimento diferem do TicketHub.

## Decisão

Usar `core/` para o módulo Maven, `docs/adr/` para decisões e `mise.toml` para
tarefas. Separar `dev.yggdrasil.core.domain` de
`dev.yggdrasil.core.application`, com dependência da aplicação para o domínio.
Preservar artefato `yggdrasil-core` e módulo JPMS `dev.yggdrasil.core`.
Os adaptadores permanecem nos consumidores em `examples/`.

## Alternativas e consequências

Copiar os três módulos do TicketHub introduziria infraestrutura sem função no
core embarcado. Manter o pacote único conservaria os imports, mas não tornaria
visíveis as responsabilidades. A organização escolhida mantém uma dependência
Maven para o consumidor e permite verificar as camadas com `jdeps`.

Os imports antigos precisam ser migrados. Exemplos, testes e a candidata são
regenerados; o contrato semântico de avaliação permanece o mesmo.
