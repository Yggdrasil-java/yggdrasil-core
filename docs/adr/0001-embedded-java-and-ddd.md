# ADR 0001: Biblioteca embarcada, Java 25 e DDD

## Status

Aceito. Registro: 2026-10-10.

## Contexto

O motor deve ser consumido dentro da aplicação e crescer em módulos públicos.

## Alternativas, prós e contras

Biblioteca embarcada: evita uma chamada remota obrigatória de avaliação; compartilha recursos e ciclo de vida com o host. Serviço dedicado: permite isolamento operacional; exige integração remota e infraestrutura própria.

## Decisão

Usar Java 25 e DDD em uma biblioteca modular embarcada. O consumidor escolhe módulos e pode importar somente core, sem exigir banco, frontend ou extensões. Frontend administra o motor por uma integração ainda a definir.

## Consequências e pendências

API pública, limites de domínio e compatibilidade precisam de desenho explícito. Spring, build e publicação de artefatos permanecem pendentes.
