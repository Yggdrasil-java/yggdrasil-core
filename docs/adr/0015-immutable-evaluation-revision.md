# ADR 0015: Revisão imutável por avaliação

## Status

Aceito na garantia. Registro: 2026-10-10. Representação e publicação entre instâncias pendentes.

## Contexto

Regras podem mudar rapidamente e componentes podem ser compartilhados. Uma avaliação não deve misturar versões quando uma publicação ocorrer durante sua execução.

## Alternativas, prós e contras

Consultar a versão mais recente a cada etapa pode refletir mudanças imediatamente, mas mistura revisões e prejudica reprodução. Fixar uma revisão imutável preserva coerência e rastreabilidade, mas exige resolução de referências versionadas e gestão do ciclo de vida das revisões.

## Decisão

Cada avaliação usa uma revisão imutável do Forest, incluindo as versões dos componentes compartilhados. Mudanças publicadas durante a execução afetam somente avaliações seguintes. A avaliação em andamento mantém sua revisão original.

## Consequências e pendências

Componentes reutilizáveis são referenciados por identidade e versão, fixadas na publicação conforme ADR 0018. A estratégia física de resolução ou materialização ainda será definida. Esta garantia é sobre configuração e componentes, não um snapshot transacional automático de todas as fontes externas. Consistência dos dados consultados, propagação entre instâncias, retenção das revisões e demais fronteiras de agregados permanecem pendentes.
