# Implementação Java do core

## Escopo

Implementação autorizada: core Java 25 com Maven e testes. O projeto raiz agrega
`core` e as [capacidades opcionais](modules.md). O core é independente delas.
As coordenadas locais são `dev.yggdrasil:yggdrasil-core:0.1.0-SNAPSHOT` e não
representam reserva de domínio nem publicação de artefatos.

## API inicial

Pacotes `dev.yggdrasil.core.domain` (modelo e invariantes) e
`dev.yggdrasil.core.application` (avaliação), módulo JPMS `dev.yggdrasil.core`.
O módulo Maven fica em `core/`; veja [organização](repository-organization.md).
`Forest` representa uma revisão publicada imutável; `Tree` contém uma condição
raiz, contribuição decimal opcional e decisão terminal opcional.
`Condition` é selada em `Branch` e `Leaf`; `Operand` distingue literal,
caminho tipado e alias. `AttributePath` recebe segmentos `Key` e `Index`.
`EvaluationInput` normaliza e copia profundamente os atributos do host.
Números finitos são normalizados em `BigDecimal`; tempo usa `Instant` e
`LocalDate`, sem conversão automática de textos.

`Evaluator.evaluate(Forest, EvaluationInput)` executa sincronamente e devolve
`EvaluationResult`: identidade/revisão, estado, decisão, score parcial,
motivo e explicação ordenada. Explicações contêm identificadores e estados,
com valores ocultos por padrão e captura explícita por chamada. A explicação da Tree terminal inclui sua decisão. Nós omitidos pelo short-circuit são explicitamente
`NOT_EVALUATED`. Não há consultas, persistência, ações de negócio ou paralelismo.

A construção de `Forest` valida identidade, revisão, aliases, operadores,
intervalos e catálogo de decisões. O catálogo inclui decisões padrão e as
referências utilizadas; identificadores extras podem ser fornecidos explicitamente.
Componentes reutilizáveis são materializados pelo host; snapshots imutáveis
ligam identidade, revisão e definição, com verificação de correspondência no Forest. Autoria, alocação de
revisões e resolução externa não pertencem ao avaliador.

## Build e testes

JDK 25, Maven Wrapper 3.9.16, JUnit Jupiter 5.11.4, Compiler Plugin 3.13.0 e Surefire 3.5.3.
Executar `./mvnw verify` na raiz; `./mvnw -pl core test` testa o módulo.
Testes `*Test` verificam semântica, validação, imutabilidade e explicações.
Usar quatro espaços em Java, classes em PascalCase, métodos em camelCase e
constantes em UPPER_SNAKE_CASE. Não exigir cobertura percentual nesta etapa.

## Limites

A API inicial pode evoluir antes de uma versão estável. Rascunhos, distribuição
de revisões, shadow e cancelamento de integrações ficam fora do módulo.

A complementação da API e suas garantias estão em
[core-completion.md](core-completion.md). SAME_DATE aceita caminhos de datas
civis sem fuso; configurações para instantes precisam de fuso explícito.

Referências de build: [Maven Compiler --release](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html)
e [JUnit 5](https://docs.junit.org/5.11.4/user-guide/).
