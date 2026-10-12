# Arquitetura

## Direção aceita

Biblioteca embarcada em Java 25, organizada segundo DDD. Avaliação direta de condições estruturadas, sem SpEL. Banco de grafo obrigatório no módulo de histórico e relações; consultas com virtual threads. Frontend para autoria e administração.

## Separação proposta

O domínio expressa regras, invariantes e resultados. Forest é a raiz do agregado do core e governa ordem das Trees, thresholds e validade da revisão. Componentes reutilizáveis são referenciados por identidade e versão fixadas na publicação. A aplicação coordena casos de uso. Adaptadores conectam bancos, fontes de dados, interfaces e observabilidade. O domínio não deve depender de um frontend ou driver de banco específico.

Na arquitetura hexagonal, casos de uso recebem ports por injeção de construtor.
A infraestrutura implementa esses ports; classes concretas são instanciadas
apenas na composição do host e em testes da infraestrutura. `HistoryService`
recebe `HistoryRepository`, seja o adaptador em memória ou o
[adaptador Neo4j](../adapters/neo4j/README.md).

A aplicação consumidora pode importar somente `core`, que avalia regras sobre dados fornecidos pelo host e não exige persistência. Os demais módulos são selecionados conforme a necessidade.

A obrigatoriedade do grafo no módulo de histórico não exige acesso ao banco para cada folha: carregamento de revisões, consultas, caches e materialização precisam ser avaliados com medições.

Há duas estruturas: regras de avaliação e relações entre entidades e eventos. Compartilhamento de condições pode exigir referências entre árvores; referências compartilhadas são permitidas, mas ciclos e referências inexistentes devem ser rejeitados antes da execução.

## Autoria e execução

Cada avaliação fica vinculada a uma revisão imutável do Forest, incluindo as versões dos componentes compartilhados. Mudanças publicadas durante a execução afetam apenas avaliações seguintes. A forma de distribuir e ativar revisões sem reinício ainda será decidida. Uma avaliação deve registrar qual revisão utilizou.

O frontend necessita de uma interface de administração fornecida pelo host ou por módulo próprio. REST, autenticação e integração Spring não estão escolhidos.

## Decisões pendentes

Neo4j foi escolhido para histórico; integração e operação ainda exigem validação.
Armazenamento de configurações, cache, protocolos e distribuição entre processos
continuam pendentes. Os contratos Java implementados estão em
[modules.md](modules.md). MongoDB para configurações é uma possibilidade, não
uma dependência aprovada.

## Decisões e ações

O core oferece ALLOW, REVIEW e BLOCK e permite decisões adicionais definidas pela aplicação. Retornar uma decisão não executa uma ação de negócio: a aplicação consumidora é responsável por sua execução. Essa separação permite avaliação sem efeitos operacionais em dry-run e shadow.
