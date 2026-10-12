# ADR 0010: Faixas de score por Forest

## Status

Aceito no modelo. Registro: 2026-10-10. Representação pública pendente; decisões extensíveis aceitas.

## Contexto

O score aceita contribuições positivas e negativas, sem escala obrigatória. Cada conjunto precisa configurar como converter o resultado conhecido em decisão.

## Alternativas, prós e contras

Limite único simplifica a configuração, mas restringe decisões intermediárias. Faixas configuráveis permitem diferentes níveis de resposta, mas exigem validação de cobertura e limites. Políticas arbitrárias oferecem flexibilidade, mas ampliam a complexidade do contrato inicial.

## Decisão

Usar faixas configuráveis de score associadas a decisões por Forest. Impedir lacunas e sobreposições. Explicitar inclusividade dos limites. Oferecer ALLOW, REVIEW e BLOCK e permitir decisões adicionais definidas pela aplicação. O core retorna a decisão, e a aplicação executa a ação correspondente. Tratar UNKNOWN por política separada, sem converter ausência de dados em score zero.

## Exemplo ilustrativo

ALLOW abaixo de 30; REVIEW de 30 até abaixo de 80; BLOCK a partir de 80. Os números não estabelecem valores padrão da biblioteca. ALLOW, REVIEW e BLOCK são decisões oferecidas; outras podem ser definidas pela aplicação.

## Consequências e pendências

Validar as faixas antes da avaliação. Definir representação dos limites sem fronteira, representação decimal, arredondamento e identificadores das decisões extensíveis. O score parcial de uma decisão terminal não é convertido por thresholds para substituir a decisão terminal.
