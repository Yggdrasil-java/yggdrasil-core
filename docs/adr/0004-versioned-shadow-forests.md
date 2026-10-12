# ADR 0004: Forests versionados e shadow

## Status

Aceito. Registro: 2026-10-10.

## Contexto

Mudanças rápidas precisam ser comparadas antes de afetar decisões antifraude.

## Alternativas, prós e contras

Shadow por conjunto: compara o comportamento completo; exige recursos, revisões e estado isolado. Ativação imediata: reduz etapas; não atende à aprovação e à experimentação exigidas.

## Decisão

Comparar Forests completos em shadow, incluindo configuração das extensões. Promover mediante aprovação de usuário autorizado. Manter estado próprio quando atributos mudarem.

## Consequências e pendências

Definir auditoria, isolamento de recursos, rollback e ativação entre instâncias. Identidade de versões do código dos plugins ainda está pendente.
