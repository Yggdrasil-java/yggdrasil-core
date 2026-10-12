# 0023 — Complementação e vocabulário do core

## Status

Implementado no escopo autorizado. Nome `UNKNOWN` e licença Apache-2.0
escolhidos pelo responsável. Aprovação de consumidores reais pendente por
escolha explícita; cenários compiláveis foram solicitados.

## Contexto

A revisão identificou lacunas na configuração temporal, investigação,
consistência de componentes versionados, diagnósticos e distribuição.

## Decisão

- Permitir SAME_DATE sobre atributos de datas civis sem fuso; verificar
  configurações de instantes antes de executar as Trees.
- Usar `UNKNOWN` para condições, estado da avaliação e outcomes; preservar
  a lógica de três valores e a política explícita `unknownDecision`.
- Capturar operandos somente quando `EvaluationOptions.INVESTIGATION` é
  fornecida na chamada; manter diagnóstico sem valores no modo padrão.
- Vincular UUID e revisão à definição imutável em `ComponentRevision`;
  resolver snapshots no catálogo do host e verificar materialização no Forest.
- Distribuir Wrapper oficial, Apache-2.0 e CI Java 25 para Linux/Windows.
- Executar cenários de consumo do JAR modular e benchmarks exploratórios.

## Consequências

A API inicial muda nomes e construtores antes de estabilização. Valores de
investigação ficam sob controle da aplicação consumidora. O catálogo garante
consistência local dos snapshots, sem autenticar fontes externas ou armazenar
histórico. Resultados do avaliador puro não comprovam metas de integrações.

## Evidência e pendências

Ver [complementação](../core-completion.md),
[revisão de consumo](../api-review.md) e
[benchmarks](../../benchmarks/README.md).
O workflow é preparado localmente; execução hospedada depende de publicação
em um repositório GitHub. Nenhuma publicação é feita por esta decisão.
