# ADR 0007: Armazenamento de configurações

## Status

Proposto. Registro: 2026-10-10.

## Contexto

O responsável apontou MongoDB como possibilidade para configurações; o grafo é obrigatório no módulo de histórico e relações. O core pode ser utilizado sem armazenamento de configurações externo.

## Alternativas, prós e contras

Configurações no grafo: pode reduzir quantidade de sistemas; requer avaliar snapshots e modelo de revisão. MongoDB separado: pode acomodar documentos de configuração; introduz outra dependência operacional e coordenação de consistência. Configuração fornecida pelo host: oferece flexibilidade; exige contrato e integração próprios.

## Decisão

Nenhuma opção selecionada. Validar requisitos de autoria, versões, publicação e consistência antes da escolha.

## Consequências e pendências

Comparação detalhada de ferramentas, drivers, licenças e garantias transacionais exige pesquisa específica antes de decisão. Não importar automaticamente MongoDB, Kafka ou Spring do arquivo legado.
