# ADR 0021: Consolidação do contrato funcional do core

## Status

Consolidação validada pelo responsável após autorização inicial. Registro: 2026-10-10. Não representa implementação concluída.

## Contexto

Após aprovar a semântica de presença, o responsável autorizou definir autonomamente os detalhes restantes para reduzir a entrevista granular.

## Alternativas, prós e contras

Continuar validando cada detalhe individualmente amplia controle, mas prolonga a definição. Consolidar escolhas conservadoras permite fechar o contrato funcional, exigindo rastreabilidade do que foi aprovado diretamente e do que foi definido sob autorização.

## Decisão

Consolidar os detalhes em [core.md](../core.md): operadores públicos, presença, nulo, caminhos segmentados, representação decimal e temporal de referência, igualdade estrutural, coleções, resultado e validação. Preservar as decisões anteriores e não escolher ferramentas dos módulos adicionais.

## Ajustes na validação do responsável

Os seis grupos foram validados. Acrescentar aliases para caminhos de atributos e padronizar representação textual de tempo em ISO 8601. Detalhes operacionais conservadores: aliases apontam diretamente a caminhos, sem expressões ou encadeamento; instantes são representados em UTC com Z e datas civis em YYYY-MM-DD. Preservar frações de segundo e distinção de tipos, sem inferir fuso para data/hora sem offset.

## Consequências

O contrato de core.md é a referência vigente para os detalhes que ADRs anteriores indicavam como pendentes. BigDecimal, Instant, LocalDate e ZoneId são referências de representação; classes da API e assinaturas finais serão materializadas na implementação. Os ADRs anteriores preservam a evolução da decisão e devem ser lidos junto desta consolidação.

A primeira versão não oferece conversão temporal implícita, expressões arbitrárias, curingas de caminhos, arredondamento de score ou paralelismo que altere a ordem explícita. O core continua utilizável isoladamente.
