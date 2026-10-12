# ADR 0026 — Capacidades opcionais executáveis

## Status

Implementação autorizada dos módulos apresentados no ADR 0025. Banco de grafo
e frontend dependem de escolha explícita; API estável continua pendente.

Escolhas confirmadas: contratos/adaptador em memória primeiro para histórico,
e API Java de gestão primeiro para administração.

## Contexto e alternativas

Diretórios vazios não entregam recursos. Acoplar todos os módulos ao avaliador
obrigaria consumidores do core a configurar capacidades desnecessárias.
Portas e casos de uso independentes permitem testar recursos antes de escolher
produtos de infraestrutura.

## Decisão

Criar os sete artefatos de [modules.md](../modules.md). Domínios usam objetos
imutáveis e portas; aplicações orquestram operações; adaptadores em memória
implementam atomicidade local. Administração compõe autoria e experimentação.
O core não depende dos demais módulos.

Fixar janelas semiabertas e horizonte de conhecimento, conflitos explícitos
de idempotência, CAS por versão, autorização do host e checkpoints por evento.
Preservar snapshots para reprodução e namespaces separados para shadow e
backfill. Providers são fixados por nome e versão.

## Consequências

Testar concorrência, duplicatas, versões desatualizadas e exclusão de dados
futuros. CI, arquitetura e release precisam incluir todos os módulos.
Memória não substitui banco de grafo de produção ou autenticação real.
