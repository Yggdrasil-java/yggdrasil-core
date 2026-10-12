# ADR 0018: Forest como raiz do agregado

## Status

Aceito no limite do agregado core. Registro: 2026-10-10. Representação e demais agregados pendentes.

## Contexto

A sequência de Trees, os thresholds e a validade da revisão precisam formar uma configuração coerente. Reutilização entre conjuntos deve preservar a revisão fixada por avaliação.

## Alternativas, prós e contras

Governar cada Tree isoladamente facilita alterações locais, mas deixa ordem e thresholds sem uma fronteira única de consistência. Forest como raiz do agregado reúne invariantes do conjunto, mas exige governar referências compartilhadas sem modificar versões publicadas.

## Decisão

Forest é a raiz do agregado, responsável pela ordem das Trees, pelos thresholds e pela validade da revisão. Componentes reutilizáveis são referenciados por identidade e versão, fixadas ao publicar a revisão.

## Consequências e pendências

Alterações em componentes compartilhados não modificam revisões publicadas. Histórico de entidades e fontes externas não integram automaticamente a transação do agregado. O ciclo distingue rascunho editável, que pode estar incompleto, de revisão publicada imutável após validação, conforme ADR 0019. Definir identidades internas, titularidade dos componentes compartilhados, representação de rascunhos e revisões e mecanismo de resolução das referências. Esta decisão não escolhe banco, framework ou modelo de persistência.
