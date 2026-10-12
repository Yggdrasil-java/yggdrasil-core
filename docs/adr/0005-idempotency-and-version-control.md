# ADR 0005: Idempotência e versão esperada

## Status

Aceito no princípio; contrato pendente. Registro: 2026-10-10.

## Contexto

Repetições não devem duplicar efeitos e clientes não devem sobrescrever alterações feitas após a leitura.

## Alternativas, prós e contras

Versão esperada: detecta alterações concorrentes sem bloqueio permanente; requer tratamento de conflito pelo cliente. Bloqueio exclusivo: pode serializar autoria; exige liberação e recuperação de sessões. Chaves de idempotência: reconhecem repetições; exigem escopo e retenção.

## Decisão

Operações idempotentes. Frontend informa a versão esperada; apenas uma alteração baseada naquela versão deve ser aceita.

## Consequências e pendências

Definir atomicidade, chaves, retenção e retorno de operações repetidas. Esta decisão não determina ordenação de avaliações simultâneas de uma entidade.
