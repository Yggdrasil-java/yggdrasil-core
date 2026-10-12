# ADR 0009: Composição de Branches

## Status

Aceito no comportamento, incluindo propagação de UNKNOWN. Registro: 2026-10-10.

## Contexto

Condições precisam ser combinadas na ordem explícita, evitando trabalho restante quando o resultado da composição já estiver definido.

## Alternativas, prós e contras

Avaliar todas as condições oferece uma explicação completa, mas executa trabalho desnecessário. Encerramento antecipado reduz consultas e processamento; a explicação deve distinguir elementos avaliados e não avaliados.

## Decisão

ALL exige todas as condições e encerra na primeira falsa. ANY exige pelo menos uma condição e encerra na primeira verdadeira. NOT inverte uma única condição e preserva UNKNOWN como desconhecido. Respeitar a sequência explícita e não avaliar elementos restantes após resultado decisivo da Branch.

Em ALL, falsa prevalece; sem falsa, qualquer desconhecida resulta em UNKNOWN. Em ANY, verdadeira prevalece; sem verdadeira, qualquer desconhecida resulta em UNKNOWN. Desconhecido não encerra a Branch por si só.

Uma Tree indeterminada não contribui para o score. Na ausência de decisão terminal, qualquer Tree indeterminada torna o Forest UNKNOWN, preservando o score conhecido e aplicando a política de risco do conjunto.

## Consequências e pendências

Encerramento de Branch não equivale a decisão terminal do Forest. Branches vazias são rejeitadas e NOT exige exatamente uma condição, conforme ADR 0016. Validar representação da explicação e representação da política de risco no resultado. Operadores de comparação das Leaves permanecem pendentes.
