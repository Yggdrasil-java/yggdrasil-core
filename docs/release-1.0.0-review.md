# Revisão da candidata 1.0.0

## Estado

Destino escolhido: GitHub Packages, na organização do responsável.
O nome exato da organização/repositório ainda precisa ser informado.
A candidata é preparada localmente, sem aprovação automática nem publicação.
O workspace permanece em `0.1.0-SNAPSHOT` até aprovação da versão estável.

## Resultado técnico

A candidata inclui os sete módulos de [modules.md](modules.md). Gera binário,
fontes, Javadoc e POM por módulo, POM pai, assinaturas e relatórios: 31 arquivos
com hashes. Verifica três consumidores Maven em um repositório isolado,
descartando produtos Yggdrasil preexistentes no cache de bootstrap.

O core passa nos 70 testes de contrato. Os exemplos são compilados contra o
JAR modular e verificam score, aliases, privacidade, investigação, diagnósticos,
datas civis, componentes publicados e decisões terminais. A aplicação consumidora
independente resolve histórico e aplica ações sem acrescentar dependências ao core.

O bundle da candidata contém binário, fontes, Javadoc, POM pai/POM do módulo,
assinaturas públicas, relatórios de testes e manifest com hashes SHA-256.
Preparar novamente:

```sh
python3 scripts/prepare_release.py --version 1.0.0
```

Os artefatos ficam em `target/releases/1.0.0/`. O script constrói uma cópia
isolada do projeto; não altera a versão do workspace, cria tags ou publica.

## Contrato a aprovar

A organização segue o [ADR 0025](adr/0025-repository-layers.md): os tipos
do modelo usam `dev.yggdrasil.core.domain`; `Evaluator` usa
`dev.yggdrasil.core.application`. Imports do pacote antigo devem ser migrados.
As assinaturas da candidata são regeneradas com esses nomes.

O [ADR 0026](adr/0026-optional-capabilities.md) acrescenta autoria, histórico,
extensões, experimentação, replay/backfill e administração. A revisão da linha
1.x deve incluir essas APIs e os contratos de CAS, idempotência, tempo,
namespaces e autorização. Banco e frontend foram adiados explicitamente.

- `Forest` é uma revisão imutável validada, com ordem explícita, thresholds,
  aliases, catálogo de decisões e política `unknownDecision` obrigatória.
- `Truth`: TRUE, FALSE e UNKNOWN. O estado final determinado usa DETERMINATE;
  ausência de certeza usa UNKNOWN. Decisão operacional é separada desse estado.
- `Tree` tem condição raiz e contribuição e/ou decisão terminal. Valores `null`
  nesses campos opcionais indicam capacidade não configurada.
- Caminhos têm segmentos tipados; literais nunca são interpretados como caminhos.
- `ComponentRevision` fixa definição e versão. Consistência local é verificada;
  autenticidade da fonte e alocação de revisões pertencem ao host.
- `Evaluator` é síncrono e sem estado. Configuração temporal inválida de instantes
  sem fuso é rejeitada antes de avaliar Trees; tipos incompatíveis produzem UNKNOWN.
- Explicações ocultam valores por padrão; investigação é explícita por chamada.
- Persistência, integração, autoria e ações de negócio não pertencem ao core.

A aprovação congela esses nomes e assinaturas para a linha 1.x. Alterações
incompatíveis posteriores exigirão outra versão principal ou compatibilidade
por sobrecargas/adaptadores. Builders podem ser adicionados sem substituir os
construtores existentes. A candidata não demonstra garantia de desempenho
em qualquer aplicação ou infraestrutura.

## Validações externas ainda necessárias

A CI foi configurada para Linux/Windows, bootstrap do Wrapper sem cache e
fluxo HTTP com recuperação. Neste sandbox, DNS externo e sockets locais estão
bloqueados. Esses testes ainda não executaram em runners hospedados.
O token GitHub disponível está inválido e `.git` não contém um checkout gravável.
Não foi publicada versão, criado repositório ou executado workflow externo.

## Publicação após aprovação

No repositório confirmado, executar o workflow `Publish approved release`
com `version=1.0.0` e `api_approved=true`. Ele exige sucesso da CI nos dois
sistemas e publica POM pai, JAR, fontes e Javadoc usando o token do workflow.
O script valida hashes antes do deploy; não imprime credenciais.
A etapa seguinte baixa os 29 artefatos do registro e executa consumidores
JPMS em diretório vazio; veja [verificação de consumo](published-consumption.md).
A aprovação humana deve ser registrada nesta revisão antes da primeira execução.

Deploys concluídos são registrados por módulo para retomar publicações parciais.
Vários pacotes não constituem uma transação única: falhas de rede com resultado
ambíguo exigem conferir o registro antes de repetir o deploy. Candidatas
parcialmente publicadas não podem ser regeneradas para a mesma versão.

Referências: [GitHub Packages Maven](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry)
e [Maven deploy-file](https://maven.apache.org/plugins/maven-deploy-plugin/deploy-file-mojo.html).
