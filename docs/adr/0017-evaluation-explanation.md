# ADR 0017: Explicação e inclusão explícita de valores

## Status

Aceito no conteúdo e no padrão de visibilidade. Registro: 2026-10-10. Representação pública e configuração pendentes.

## Contexto

A avaliação deve explicar sua decisão e permitir investigação. Atributos podem conter informações que a aplicação não deseja incluir automaticamente no resultado.

## Alternativas, prós e contras

Incluir todos os valores facilita investigação imediata, mas amplia dados no resultado. Omitir valores por padrão reduz essa exposição, mas exige habilitação explícita quando necessária. Retornar somente a decisão simplifica o resultado, mas não atende à explicabilidade acordada.

## Decisão

Registrar identificador da condição, operador, resultado e contribuição ao score quando aplicável. Valores dos atributos ficam ocultos por padrão. A aplicação pode habilitar explicitamente sua inclusão para investigação.

## Consequências e pendências

Identificar revisão, decisão terminal e condições não avaliadas conforme contratos já aceitos. A contribuição de uma Tree não deve ser contada novamente em cada Leaf de sua condição. Definir representação da explicação, alcance da configuração de valores e distinção entre motivos de UNKNOWN. Omissão de valores no resultado do core não define a estratégia de captura necessária ao módulo de replay fiel.
