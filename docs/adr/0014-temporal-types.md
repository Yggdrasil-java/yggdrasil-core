# ADR 0014: Instante e data civil

## Status

Aceito na distinção semântica. Registro: 2026-10-10. Representação Java e contratos de conversão pendentes.

## Contexto

Atributos temporais representam tanto momentos de eventos quanto datas sem horário. Confundir essas categorias pode alterar o resultado de condições conforme o fuso.

## Alternativas, prós e contras

Um tipo temporal único simplifica a entrada, mas pode atribuir horário e fuso inexistentes a uma data civil. Tipos distintos preservam o significado, mas exigem compatibilidade explícita nas comparações. Conversão automática facilita uso, mas introduz escolhas implícitas de fuso.

## Decisão

Distinguir instante, um momento com referência global, de data civil, um dia sem horário. Não converter automaticamente entre esses tipos. Regras que solicitarem conversão devem informar o fuso horário.

SAME_DATE compara duas datas civis diretamente. Para comparar dois instantes pelo dia, exige fuso explícito na Leaf, sem padrão baseado no fuso da máquina. Isso não autoriza comparação automática entre instante e data civil.

## Consequências e pendências

Comparações com tipos incompatíveis seguem UNKNOWN. Definir representação Java, formatos de entrada e contratos de conversão. Quando converter uma data civil em instante, o fuso por si só não determina o horário: a convenção de horário ainda precisa ser validada. Não escolher silenciosamente início do dia ou horário local da máquina.
