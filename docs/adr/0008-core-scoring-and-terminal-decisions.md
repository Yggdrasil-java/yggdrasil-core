# ADR 0008: Score aditivo e decisões terminais no core

## Status

Aceito no comportamento. Registro: 2026-10-10. API e detalhes numéricos pendentes.

## Contexto

O core precisa combinar sinais de risco e permitir decisões imediatas quando uma regra for suficiente para encerrar a avaliação.

## Alternativas, prós e contras

Soma de contribuições: torna a composição e a explicação diretas; exige definir precisão, limites e tratamento de sinais correlacionados. Decisão apenas por threshold: uniformiza a saída; não cobre o encerramento explícito requerido. Decisões terminais: permitem encerramento imediato; exigem ordem e precedência bem definidas.

## Decisão

Somar contribuições positivas ou negativas como comportamento padrão, sem escala obrigatória ou normalização automática para 0–100. Permitir que uma Tree produza decisão terminal e encerre toda a avaliação. O responsável aprovou ambos os comportamentos e definiu que o Forest determina uma sequência explícita de Trees. A primeira decisão terminal nessa sequência encerra a avaliação. A ordem também é explícita dentro das Trees, incluindo condições e resolução de atributos; não há reordenação automática.

Uma Tree pode contribuir para o score e produzir uma decisão terminal ao mesmo tempo. Somar sua contribuição antes do retorno. O retorno terminal apresenta o score acumulado até o encerramento, incluindo essa contribuição quando configurada, identificado como parcial. A explicação identifica a Tree responsável e as Trees não avaliadas.

## Consequências e pendências

Definir representação e validação das sequências, dependências de atributos e encerramento antecipado das composições, representação decimal, arredondamento e limites, intervalos de thresholds e propagação de UNKNOWN. Decisões e números do exemplo em core.md não são padrões aprovados.
