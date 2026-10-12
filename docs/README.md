# Documentação

A documentação descreve a direção acordada para Yggdrasil. Propostas não equivalem a decisões aceitas. Escolhas relevantes serão discutidas com o responsável antes de implementação.

## Leitura

1. [Visão e escopo](vision-and-scope.md)
2. [Glossário](glossary.md)
3. [Modelo de domínio](domain-model.md)
4. [Arquitetura](architecture.md)
5. [Módulos](modules.md) — [contrato do core](core.md)
6. [Ciclo de avaliação](evaluation-lifecycle.md)
7. [Histórico e identidade](history-and-identity.md)
8. [Experimentação](experimentation.md)
9. [Replay e backfill](replay-and-backfill.md)
10. [Desempenho](performance.md)
11. [Referências](references.md)
12. [ADRs](adr/README.md)

## Composição pelo consumidor

Cada aplicação escolhe os módulos que importa. O uso somente de core não exige os bancos, frontend ou plugins dos módulos adicionais.

## Estado do core

O contrato funcional inicial foi consolidado em [core.md](core.md). Os detalhes finais foram definidos sob autorização e depois validados pelo responsável, conforme [ADR 0021](adr/0021-core-contract-consolidation.md). A implementação inicial do core está descrita em [java-core.md](java-core.md).

## Convenções

Veja [organização do repositório](repository-organization.md) e
[ADR 0025](adr/0025-repository-layers.md) para as camadas e os comandos locais.

Documentação em português; conceitos e API em inglês. Usar metáforas de árvores quando tornarem o domínio mais claro. Manter termos técnicos como `Feature`, `Score` e `Threshold`.

A [documentação anterior](archive/legacy-backend.md) é um arquivo histórico. Seus exemplos com SpEL, MongoDB e Spring não representam decisões vigentes.

## Implementação e verificação

- [Módulos implementados e limites](modules.md)
- [Consumidor das sete capacidades](../examples/modular-consumer/README.md)

- [Complementação do core](core-completion.md)
- [Revisão da API por consumidores](api-review.md)
- [Benchmarks exploratórios](../benchmarks/README.md)

- [Aplicação consumidora](consumer-application.md)
- [Revisão da release 1.0.0](release-1.0.0-review.md)
