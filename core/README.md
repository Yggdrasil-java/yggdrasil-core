# Yggdrasil Core

Biblioteca Java 25, publicada como `dev.yggdrasil:yggdrasil-core`.
O POM raiz centraliza as versões e agrega este módulo.

## Camadas

- `dev.yggdrasil.core.domain`: Forest, Trees, condições, operandos, invariantes,
  revisões, atributos imutáveis, decisões e resultados da avaliação.
- `dev.yggdrasil.core.application`: serviço `Evaluator`.
  Depende do domínio; não realiza HTTP, persistência ou ações de negócio.

O módulo JPMS `dev.yggdrasil.core` exporta os dois pacotes. Não há dependências
externas em produção. As políticas compartilhadas de comparação ficam em `domain.internal`,
um pacote não exportado pelo JPMS. Os adaptadores ficam em `examples/antifraud-app`.

Os testes seguem as mesmas camadas em `src/test/java`. Builders compartilhados
de testes ficam em `support/RuleFixtures.java`.

## Comandos

Execute na raiz:

```sh
./mvnw -pl core test
./mvnw verify
python3 scripts/verify_architecture.py
python3 scripts/verify_consumer.py
```

O primeiro comando executa os testes JUnit Jupiter. `verify` empacota o JAR em
`core/target/`. A verificação de arquitetura examina dependências do bytecode
com `jdeps`; a verificação do consumidor compila os exemplos contra o JAR.

Leia a [organização](../docs/repository-organization.md) e o
[contrato Java](../docs/java-core.md) antes de alterar as camadas.
