# ADR 0019: Rascunho, publicação e ativação

## Status

Aceito no ciclo. Registro: 2026-10-10. Representação pública e transições operacionais pendentes.

## Contexto

Autoria requer mudanças incrementais, enquanto avaliação exige configuração válida e imutável. Publicar uma configuração não deve automaticamente mudar decisões de produção.

## Alternativas, prós e contras

Validar cada edição como configuração completa impede rascunhos incompletos. Rascunho editável separado de revisão publicada permite autoria incremental, mas exige uma fronteira de publicação. Ativar ao publicar reduz etapas, mas conflita com shadow e aprovação exigidos.

## Decisão

Distinguir rascunho editável, que pode estar incompleto, de revisão publicada imutável. Publicar exige validação estrutural e fixa as versões dos componentes reutilizáveis. Publicação não equivale a ativação em produção: na composição com experimentação, a revisão segue para shadow e aprovação antes de se tornar Live Forest.

## Consequências e pendências

O core isolado continua avaliando revisões válidas fornecidas pelo host sem exigir frontend, banco ou módulo de shadow. A garantia de aprovação na plataforma pertence ao módulo de experimentação e à integração consumidora. Definir representação de rascunhos e revisões, identidades, transições, permissões, concorrência e propagação da ativação.
