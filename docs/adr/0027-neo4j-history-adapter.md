# ADR 0027 — Adaptador Neo4j para histórico

## Status

Neo4j escolhido pelo responsável. Implementação autorizada; validação no banco
real é obrigatória antes de inclusão na distribuição estável.

## Contexto e alternativas

O histórico já define janelas semiabertas, horizonte de conhecimento,
identidade global de eventos/relações e isolamento por namespace. O adaptador
em memória continua útil para testes, mas não oferece durabilidade.
Neo4j atende à escolha de banco de grafo; seu driver oficial permite transações
gerenciadas. Um banco embarcado aumentaria o acoplamento e o custo do host.

## Decisão

- Criar `adapters/neo4j`, artefato `yggdrasil-history-neo4j`, ativado pelo perfil
  Maven `neo4j`. Não alterar a API de `HistoryRepository`.
- Manter arquitetura hexagonal: os casos de uso recebem ports; implementações
  concretas são conectadas na composição do host. Não instanciar adaptadores
  de infraestrutura dentro da aplicação ou do domínio.
- Receber `Driver` e nome do banco do host; o host administra credenciais,
  TLS, timeout, retries, bookmarks e encerramento do driver.
- Criar esquema explicitamente por `initializeSchema()`, fora do construtor.
  Usar constraints únicas para eventos, relações e identidade composta de
  entidades. Inicializar o esquema antes de aceitar escrita.
- Guardar relações como nós identificados, ligados a entidades por arestas
  direcionadas. As consultas de vizinhos consideram ambas as pontas.
- Executar cada escrita em uma transação; MERGE por UUID e comparar conteúdo
  imutável. Conflitos lançam `IllegalArgumentException` e revertem a transação.
- Guardar instantes como segundos/nanos, sem perda de precisão. Serializar
  atributos em formato binário versionado com tipos explícitos, preservando
  decimais, datas civis, instantes, objetos, listas e nulos. Não usar
  desserialização Java de objetos.
- Aplicar a suíte compartilhada de contratos em um banco exclusivo de testes,
  incluindo nova instância do repositório e escritores concorrentes.

## Consequências e validação

O driver é dependência apenas do adaptador. O perfil padrão e a candidata de
sete módulos continuam independentes de Neo4j. O adaptador ainda não integra
os scripts de publicação; adicionar distribuição e consumidor JPMS deste
artefato somente após compilação e integração aprovadas.
Retenção, migrações além do esquema inicial, restauração de backups e
garantias de cluster são responsabilidades futuras e não são demonstradas
pelos testes locais em memória.

Referências: [instalação do driver](https://neo4j.com/docs/java-manual/current/install/)
e [transações gerenciadas](https://neo4j.com/docs/java-manual/current/transactions/).
