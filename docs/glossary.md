# Glossário

| Termo | Significado |
|---|---|
| Forest | Raiz do agregado: conjunto completo versionado de regras em sequência explícita, thresholds e configuração das extensões |
| Tree | Uma regra, suas condições e contribuições para a decisão |
| Root | Entidade inicial da avaliação, com identidade global; não é obrigatoriamente um usuário |
| Node | Elemento de uma estrutura de avaliação |
| Branch | Composição de condições ou caminhos de avaliação |
| ALL | Composição que exige todas as condições e encerra na primeira falsa |
| ANY | Composição que exige pelo menos uma condição e encerra na primeira verdadeira |
| NOT | Inversão de uma única condição; preserva UNKNOWN |
| Leaf | Condição simples |
| Feature | Atributo recebido, consultado ou calculado; não é sinônimo de regra |
| Live Forest | Revisão do conjunto responsável pela decisão de produção |
| Shadow Forest | Revisão candidata que recebe eventos para comparação sem executar ações de produção |
| Instant | Momento com referência global, como o horário de uma transação |
| Civil date | Dia sem horário, como uma data de nascimento; distinto de Instant |
| Attribute alias | Nome legível mapeado explicitamente a um caminho de atributo na revisão do Forest |
| ISO 8601 | Padrão de representação textual temporal; instante e data civil mantêm tipos distintos |
| Attribute path | Caminho de acesso a um atributo estruturado, como transaction.amount; gramática completa pendente |
| Missing | Campo ausente na entrada; distinto de valor nulo |
| Null | Campo presente com valor nulo |
| Decision | Resultado extensível, com ALLOW, REVIEW e BLOCK oferecidos pelo core; a aplicação executa a ação correspondente |
| Score | Soma de contribuições positivas ou negativas das Trees atendidas, sem escala obrigatória; precisão e limites pendentes |
| Threshold | Configuração de faixas de score para decisões no Forest, sem lacunas ou sobreposições |
| UNKNOWN | Resultado quando os dados necessários não permitem uma decisão determinada |
| Terminal operation | Operação que encerra a avaliação inteira e solicita cancelamento do trabalho pendente |
| includeCurrent | Opção de consulta para incluir o evento atual no cálculo |
| Dry-run | Avaliação sem ações ou alterações no histórico operacional, com relatório separado permitido |
| Replay | Reprodução de uma decisão com versões e dados disponíveis naquele momento |
| Historical simulation | Avaliação de eventos antigos com um conjunto candidato e dados historicamente disponíveis |
| Backfill | Preenchimento ou recomputação de atributos e estado históricos |
| Idempotency | Repetir a mesma operação lógica sem duplicar seu efeito |
| Element ID | UUID estável de Forest, Tree, Branch ou Leaf, preservado em renomeação e reordenação |
| Revision | Número crescente de uma publicação do Forest ou de um componente reutilizável independente |
| Expected version | Versão informada pelo cliente para impedir alteração sobre uma revisão desatualizada |

Uma árvore de regras e um grafo de entidades são conceitos distintos. Componentes reutilizáveis podem ter referências compartilhadas; ciclos e limites dessas referências ainda precisam de decisão.
