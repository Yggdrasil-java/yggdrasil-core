# 0024 — Aplicação consumidora e candidata 1.0.0

## Status

Preparado e verificado localmente no escopo autorizado. Publicação no GitHub
Packages escolhida pelo responsável. Aprovação da API e execução hospedada
permanecem pendentes; o destino exato do repositório ainda não foi informado.

## Contexto

Foi solicitado tratar as pendências de consumo real, desempenho da aplicação,
bootstrap do Wrapper, CI e publicação. O responsável escolheu uma candidata
1.0.0 condicionada à revisão da API.

## Decisão

Criar uma aplicação HTTP de referência independente, com histórico local,
idempotência, ações e persistência durável no host. Verificar o fluxo sem HTTP
no sandbox e preparar os testes HTTP e de bootstrap sem cache na CI.
Construir a release em uma cópia temporária do projeto, preservando a versão
SNAPSHOT do workspace até aprovação. Publicar por workflow manual depois da
aprovação e sucesso da CI Linux/Windows.

## Evidências

- 70 testes passaram na candidata, com relatórios preservados.
- Consumidor externo compilou e executou contra o JAR 1.0.0.
- Projeto Maven consumidor independente compilou usando a dependência instalada.
- Histórico, decisões, ações, UNKNOWN, idempotência e recuperação foram verificados.
- 2.000 operações sem HTTP foram medidas com gravação durável.
- Sockets locais foram bloqueados pelo sandbox; bootstrap sem cache falhou por
  indisponibilidade de download externo. Esses resultados não são sucessos.
- Token GitHub está inválido; `.git` está vazio e somente para leitura.

## Consequências

A execução local não comprova HTTP, Windows, download externo ou publicação.
A aplicação exemplifica uma única instância, sem autenticação ou transações
distribuídas. Ela não adiciona dependências ao core.
Ver [revisão da candidata](../release-1.0.0-review.md) e
[contrato do consumidor](../consumer-application.md).
