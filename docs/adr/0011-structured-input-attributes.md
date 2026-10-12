# ADR 0011: Atributos estruturados de entrada

## Status

Aceito no modelo. Registro: 2026-10-10. Representação Java e gramática de caminhos pendentes.

## Contexto

O core recebe dados da aplicação consumidora para avaliar entidades e eventos de diferentes domínios.

## Alternativas, prós e contras

Atributos planos simplificam o acesso, mas exigem achatar estruturas do domínio. Atributos estruturados preservam objetos e coleções, mas exigem resolução de caminhos e regras de tipos explícitas. Acesso por expressões arbitrárias amplia flexibilidade, mas não corresponde à avaliação estruturada sem SpEL acordada.

## Decisão

Aceitar objetos aninhados, listas, textos, booleanos, números e datas. Leaves acessam atributos por caminhos, como transaction.amount e device.trusted. Distinguir campo ausente de campo presente com valor nulo.

## Consequências e pendências

Definir representação Java, representação de instante e data civil, representação decimal e arredondamento, acesso a listas, escape de nomes de campos e comportamento de tipos incompatíveis. Comparações com campos ausentes ou tipos incompatíveis retornam UNKNOWN, conforme ADR 0012. Operadores de presença tratam esses estados explicitamente. Comparações com nulo e detalhes por operador permanecem pendentes. Nenhum formato de serialização ou parser específico foi escolhido.
