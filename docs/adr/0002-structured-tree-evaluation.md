# ADR 0002: Avaliação estruturada de árvores

## Status

Aceito. Registro: 2026-10-10.

## Contexto

Regras complexas devem ser editáveis e explicáveis sem SpEL.

## Alternativas, prós e contras

Árvores estruturadas: permitem validar e explicar condições; exigem operadores, tipos e semântica próprios. Expressões textuais genéricas: oferecem expressividade; exigem parser e controles adicionais. SpEL foi excluído pelo responsável.

## Decisão

Avaliar diretamente estruturas de regras. Adotar Forest, Tree, Root, Node, Branch e Leaf conforme glossário.

## Consequências e pendências

Nomes e detalhes dos operadores, representação de referências reutilizáveis e estratégia de execução ainda serão validados. Compartilhamento sem ciclos é permitido; ciclos e referências inexistentes são rejeitados conforme ADR 0016. Não foi escolhido um algoritmo como Rete.
