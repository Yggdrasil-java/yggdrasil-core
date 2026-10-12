# 0022 — Implementação inicial do core Java

## Status

Implementado no escopo autorizado de core Java 25 com Maven e testes.
A API inicial está sujeita a evolução antes de uma versão estável.

## Contexto

O contrato funcional do ADR 0021 está consolidado. O responsável solicitou
iniciar a implementação do core com Maven e testes, mantendo-o embarcado e
independente de infraestrutura.

## Opções

- Um módulo core sem dependências de produção: permite consumo independente e
  validação da semântica; integrações precisam materializar seus próprios dados.
- Criar antecipadamente módulos adicionais: amplia a estrutura, mas fixa limites
  e contratos ainda pendentes. Não foi escolhido para esta etapa.

## Decisão

Criar agregador Maven e módulo `yggdrasil-core`, com release Java 25 e JPMS.
Usar records imutáveis, condições/operandos selados e avaliador síncrono sem estado.
JUnit Jupiter verifica avaliação, validação, imutabilidade e explicações.
As coordenadas `dev.yggdrasil` são locais; não representam publicação nem
propriedade de domínio. Detalhes em [java-core.md](../java-core.md).

## Consequências

O host fornece os atributos e revisões já materializadas. Componentes externos
não são consultados pelo avaliador. Autoria, persistência e promoção de revisões
permanecem em módulos futuros. O histórico de revisões e sua monotonicidade por
identidade são responsabilidade do host; o core valida revisão positiva.

## Pendências

Validar a API com consumidores antes de estabilizar nomes e publicar artefatos.
Definir ferramentas e contratos dos módulos adicionais em decisões próprias.
