# Módulos

A biblioteca terá sete módulos Maven. Cada consumidor importa apenas as
capacidades necessárias. Camadas: `infrastructure → application → domain`.

| Módulo / artefato | Capacidade | Dependências internas |
|---|---|---|
| core / yggdrasil-core | Regras, avaliação e explicações | Nenhuma |
| authoring / yggdrasil-authoring | Rascunhos, edição e publicação | core |
| history / yggdrasil-history | Eventos, janelas e relações | core |
| extensions / yggdrasil-extensions | Providers versionados, prazos e estado isolado | core |
| experimentation / yggdrasil-experimentation | Shadow, aprovação, promoção e rollback | core |
| replay / yggdrasil-replay | Dry-run, replay, simulação e backfill | core |
| administration / yggdrasil-administration | Gestão Java autorizada | core, authoring, experimentation |

## Contratos da primeira implementação

- Autoria: versão esperada e UUID por operação. Repetições devolvem o resultado
  anterior; reutilização com outro comando é rejeitada. Publicação valida o
  Forest, fixa componentes e aloca revisões crescentes sem ativar produção.
- Histórico: identidades compostas por namespace, tipo e valor. Eventos têm
  tempos de ocorrência e ingestão. Janelas `[início, fim)` respeitam `knownAt`.
  `includeCurrent` é explícito e não duplica eventos. Relações têm validade e
  tempo de registro. Duplicatas iguais são idempotentes; conflitos são rejeitados.
  O evento atual informado explicitamente pode ocorrer exatamente no fim da
  janela; outros eventos nesse instante continuam excluídos.
- Extensões: resolução explícita por nome/versão exatos, argumentos imutáveis,
  virtual threads, concorrência limitada, prazo e cancelamento cooperativo.
  Resultados distinguem valor, ausência, erro e prazo. Estado é separado por
  namespace e versão do provider; `ScopedState.update` permite agregações
  atômicas. Transformações devem ser puras e curtas. O core não resolve plugins automaticamente.
- Experimentação: Live e Shadow recebem os mesmos dados imutáveis, sem executar
  ações. Aprovação identifica ator/revisão; promoção exige aprovação e versão
  esperada. Rollback autorizado retorna apenas a uma revisão anteriormente Live.
- Replay: snapshots registram revisão, dados, opções, tempos e resultado original.
  Replay fiel compara o resultado completo. Simulação usa uma candidata sobre
  snapshots históricos, sem consultar dados atuais. Backfill tem namespace
  próprio, checkpoint e commit atômico por evento. Projeções devem ser puras.
- Administração: fachada Java verifica autorização fornecida pelo host antes
  de cada operação. Identidade autenticada, HTTP e frontend são integrações
  explícitas; uma string de ator não autentica uma requisição.

## Adaptadores e limites

Adaptadores em memória servem a testes e integração local; não oferecem
durabilidade, retenção ou coordenação entre processos. Produção do histórico
continua exigindo banco de grafo; o responsável escolheu Neo4j. O
[adaptador opcional](../adapters/neo4j/README.md) usa `HistoryRepository`, sem
alterar os casos de uso. Sua compilação e integração aguardam validação em
ambiente com rede e banco disponível; ele não integra a candidata atual.

As assinaturas novas estão em desenvolvimento e não aprovam automaticamente a
versão pública 1.0.0. Migrações, frontend e drivers só serão marcados como
implementados quando existirem e tiverem validação correspondente.
