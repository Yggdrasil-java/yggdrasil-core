# Organização do repositório

O TicketHub é a referência para separar responsabilidades, manter testes próximos
do código e centralizar comandos de desenvolvimento. No Yggdrasil, a distribuição
continua sendo uma biblioteca embarcada: o artefato Maven `yggdrasil-core` e o
módulo JPMS `dev.yggdrasil.core` permanecem como unidades de consumo.

## Estrutura

```text
core/                   módulo Maven da biblioteca
  src/main/java/        domínio e serviço de avaliação
  src/test/java/        testes espelhando as responsabilidades
authoring/              rascunhos e publicação
history/                consultas temporais e relações
extensions/             providers e estado isolado
experimentation/        shadow e ativação aprovada
replay/                 reprodução, simulação e backfill
administration/         fachada Java de gestão
docs/                   especificações e revisão da API
  adr/                  decisões arquiteturais numeradas
  archive/              material histórico
examples/               consumidores e adaptadores HTTP/persistência
benchmarks/             medições exploratórias e evidências
scripts/                verificações e preparação/publicação de versões
mise.toml               ferramentas e tarefas de desenvolvimento
```

`examples/api-consumer` contém o cenário público em `src/main/java` e tem
seu próprio POM, sem integrar o reactor da biblioteca. No antifraude, o
verificador do host fica em `src/test/java`, fora do JAR de produção.

O domínio define regras, invariantes, dados imutáveis e resultados. A camada de
aplicação avalia esses objetos e depende do domínio. HTTP, histórico e ações de
negócio ficam na aplicação de exemplo. Não há dependência do domínio sobre o
avaliador ou os adaptadores, nem módulo vazio de infraestrutura na biblioteca.

Os pacotes públicos são `dev.yggdrasil.core.domain` e
`dev.yggdrasil.core.application`. `Evaluator` fica na aplicação; os tipos
recebidos e devolvidos pertencem ao domínio. A CI verifica a direção das
dependências no bytecode com `scripts/verify_architecture.py`.

O POM raiz centraliza versões e agrega `core/`; o diretório não define o nome
do artefato publicado. Cada módulo mantém seus próprios testes. Scripts e CI
usam os caminhos desta estrutura. A documentação histórica permanece preservada.

O reactor agrega também os seis módulos opcionais definidos em
[modules.md](modules.md). Cada um exporta seus pacotes `domain`, `application`
e `infrastructure`. Os adaptadores de memória são implementações de referência.

## Desenvolvimento

Com mise disponível, `mise run test`, `mise run build` e `mise run install`
executam as mesmas fases Maven usadas pelo Wrapper. `mise run consumer` verifica
o consumidor do JAR empacotado. Os comandos diretos continuam disponíveis:
`./mvnw verify` e `./mvnw -pl core test`.

Reorganizar pacotes públicos altera imports e exige regenerar a candidata e a
revisão de assinaturas antes de qualquer aprovação ou publicação da versão 1.0.0.
