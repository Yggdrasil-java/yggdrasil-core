# Adaptador de histórico Neo4j

Artefato candidato `dev.yggdrasil:yggdrasil-history-neo4j`; módulo JPMS
`dev.yggdrasil.history.neo4j`. Driver oficial fixado em 6.1.0. A integração
foi preparada para Neo4j 5.26; outras versões exigem validação própria.

## Arquitetura hexagonal

`HistoryService` recebe o port `HistoryRepository`. O adaptador pertence à
infraestrutura, e sua classe concreta aparece apenas na composição do host
e em testes da infraestrutura. A aplicação não conhece o driver.

```java
// Na composição do host; o host encerra o Driver ao terminar.
var adapter = new Neo4jHistoryRepository(driver, "neo4j");
adapter.initializeSchema(); // provisioning, antes de aceitar escrita
HistoryRepository historyPort = adapter;
var history = new HistoryService(historyPort);
```

O host cria o driver com suas credenciais, TLS e timeouts. O adaptador cria e
encerra uma sessão por operação, usa transações gerenciadas e compartilha o
bookmark manager do driver para leituras causalmente consistentes entre
sessões desse driver. Coordenação entre drivers/processos exige bookmarks do
host. Não inclua senhas no código ou em arquivos versionados.

## Modelo e limites

`YGEvent` tem UUID único e campos de consulta por identidade/tipo/tempo.
`YGEntity` tem identidade composta por namespace, tipo e valor. `YGRelation`
tem UUID único e arestas `YG_FROM`/`YG_TO`; consultar ambas as pontas preserva
os vizinhos do port, inclusive autorrelações sem duplicação.
Labels, constraints e índices com prefixo YG são reservados ao adaptador.

Instantes usam segundos/nanos, e atributos usam payload tipado versionado.
Mapas têm ordenação canônica; escala decimal, nulos e tipos temporais são
preservados. O payload binário admite até 16 MiB e 128 níveis de aninhamento;
valores acima disso são rejeitados antes da escrita. O limite operacional de
mensagem/configuração do servidor também deve comportar o payload Base64.

UUIDs são globais no banco, como no adaptador em memória; uma colisão entre
namespaces também é conflito. Não altere registros fora do port. Inicialize
o esquema com credenciais de provisionamento antes de usar uma conta de
runtime com permissões restritas. Retenção e migrações futuras ainda não
estão implementadas.

## Build e testes

Na raiz, com JDK 25 e Maven Central disponível:

```sh
./mvnw -Pneo4j -pl adapters/neo4j -am verify
```

Esse comando executa testes de serialização, mas não conecta ao banco.
Quando o driver não estiver disponível, a verificação isolada abaixo executa
apenas os fontes originais do codec e seus testes. Requer core instalado no
repositório Maven local; não compila o adaptador nem substitui a integração.

```sh
./mvnw -pl core -am install
python3 scripts/verify_neo4j_codec.py --offline
```

Para integração, use exclusivamente um servidor descartável, configure
`YGGDRASIL_NEO4J_URI`, `YGGDRASIL_NEO4J_USER`,
`YGGDRASIL_NEO4J_PASSWORD`, `YGGDRASIL_NEO4J_DATABASE` e
`YGGDRASIL_NEO4J_TEST_ALLOW_CLEANUP=true`, então execute:

```sh
./mvnw -Pneo4j,neo4j-integration -pl adapters/neo4j -am verify
```

O perfil falha se faltar configuração. Os testes removem dados das labels
YG no banco indicado. A suíte compartilhada testa conflitos concorrentes,
isolamento e janelas; testes adicionais reabrem o driver e exercitam precisão
de nanos. Reabrir o driver não demonstra recuperação após reinício do servidor.
O CI inicia Neo4j Community 5.26 em serviço exclusivo.

## Estado da entrega

O adaptador é opcional e ainda não faz parte da candidata de sete módulos nem
dos scripts de publicação. A compilação com o driver real e os testes no
Neo4j aguardam execução em ambiente com rede e Docker. Testes locais isolados
do codec não validam Cypher, transações ou JPMS do driver.

Veja o [ADR 0027](../../docs/adr/0027-neo4j-history-adapter.md), a
[documentação oficial do driver](https://neo4j.com/docs/java-manual/current/install/)
e as [transações gerenciadas](https://neo4j.com/docs/java-manual/current/transactions/).
