# Consumidor modular

Projeto Maven independente que compõe as sete bibliotecas Java. Os adaptadores
são locais, em memória; o exemplo não exige serviços externos.

Na raiz:

```sh
./mvnw install
./mvnw -f examples/modular-consumer/pom.xml verify
python3 scripts/verify_modular_consumer.py
```

O script executa um fluxo completo: cria e publica revisão, edita uma candidata,
consulta eventos/relações, resolve um provider versionado em virtual thread,
compara Live/Shadow e promove após aprovação. Reproduz a revisão original,
simula a candidata, faz backfill idempotente em namespace próprio e retorna à
revisão anterior por rollback autorizado.

Permissões são explícitas por ator, ação e UUID de Forest. O host autentica o
ator e escolhe a revisão Live inicial já autorizada. Captura de dados para
replay é explícita; não persistir esses snapshots sem política de acesso e
retenção. Providers usam namespaces separados e recebem horizonte temporal.

Para compilar contra uma candidata/publicação, configure o repositório Maven
e passe `-Dyggdrasil.version=1.0.0`. A preparação da candidata testa a resolução
dos POMs em um repositório isolado, além do consumo JPMS.
