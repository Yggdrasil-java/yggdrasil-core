# ADR 0006: Virtual threads e operações terminais

## Status

Aceito no princípio; mecanismos pendentes. Registro: 2026-10-10.

## Contexto

Consultas devem caber no prazo de avaliação, e resultados terminais encerram todo o trabalho restante.

## Alternativas, prós e contras

Virtual threads: permitem código bloqueante com concorrência; fontes ainda têm limites de conexão e capacidade. APIs assíncronas: oferecem outra integração de I/O; aumentam exigências de composição. Cancelamento cooperativo: permite encerrar tarefas compatíveis; não garante parada imediata de toda integração.

## Decisão

Usar virtual threads em Java 25 para consultas. Operação terminal encerra a avaliação inteira e solicita cancelamento de pendências.

## Consequências e pendências

Validar suporte dos drivers, deadlines e isolamento de resultados tardios. Biblioteca de concorrência e eventual uso de APIs preview não estão escolhidos.
