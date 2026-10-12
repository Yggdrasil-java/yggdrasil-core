# ADR 0020: Identidade estável e revisão

## Status

Aceito no modelo. Registro: 2026-10-10. Variante de UUID e alocação de revisões pendentes.

## Contexto

Comparação entre revisões e explicações precisam reconhecer elementos mesmo quando forem renomeados ou reordenados. Identidade e revisão representam conceitos diferentes.

## Alternativas, prós e contras

Nome como identidade facilita leitura, mas renomeações quebram referências. Revisão como identidade identifica publicação, mas não reconhece o mesmo elemento entre publicações. UUID estável mais revisão separa identidade de evolução, mas requer regras explícitas para cópia e reutilização. Versionar cada elemento separadamente amplia independência, mas acrescenta gestão desnecessária para elementos internos de um conjunto.

## Decisão

Forest, Tree, Branch e Leaf usam UUID estável. Forest usa revisão numérica crescente; elementos internos são versionados pela revisão do Forest. Componentes reutilizáveis independentes usam UUID e revisão próprios, fixados na publicação do Forest. Cópia independente recebe novo UUID; reutilização por referência preserva o original. Nome é texto editável para leitura.

## Consequências e pendências

Não exigir revisão própria para cada Leaf interna. Definir variante de UUID, geração, unicidade e alocação das revisões. A revisão publicada não deve ser confundida com a versão esperada de um rascunho editável usada no controle de concorrência; a representação desse controle permanece pendente.
