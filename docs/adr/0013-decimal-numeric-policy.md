# ADR 0013: Precisão decimal e tipos numéricos

## Status

Aceito na política. Registro: 2026-10-10. Representação Java e regras de escala/arredondamento pendentes.

## Contexto

Valores monetários, scores e thresholds precisam de comparações numéricas previsíveis. Conversão implícita de textos pode ocultar tipos incorretos na integração.

## Alternativas, prós e contras

Precisão decimal permite representar frações decimais sem o arredondamento binário de double, mas requer definir representação, escala e regras para cálculos com arredondamento. Ponto flutuante binário oferece operações rápidas e tipos comuns, mas certas frações decimais não são representadas exatamente. Conversão automática de texto amplia aceitação de entradas, mas esconde incompatibilidades de tipos.

## Decisão

Usar precisão decimal para scores e comparações numéricas. Não converter automaticamente texto em número: "100" não é tratado como o número 100. Comparações com tipos incompatíveis seguem o tratamento UNKNOWN já definido.

## Consequências e pendências

Definir representação Java, interoperabilidade entre tipos numéricos, limites, escala e arredondamento. BigDecimal é uma possibilidade a avaliar, não uma classe pública escolhida nesta decisão. Precisão decimal não elimina a necessidade de políticas para operações que exigem arredondamento.
