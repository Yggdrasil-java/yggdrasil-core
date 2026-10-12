# Consumidor da API pública

Projeto Maven independente que usa apenas `dev.yggdrasil:yggdrasil-core`.
Os cenários verificam score, aliases, privacidade, investigação, diagnósticos,
datas, snapshots e decisões terminais.

Na raiz do repositório:

```sh
./mvnw install
./mvnw -f examples/api-consumer/pom.xml verify
python3 scripts/verify_consumer.py
```

O comando Maven verifica a compilação como consumidor independente; o script
executa os cenários contra o módulo JPMS empacotado. Para resolver outra versão,
use `-Dyggdrasil.version=1.0.0` com o registro e credenciais Maven configurados.

O consumidor está em `src/main/java/dev/yggdrasil/examples/CoreConsumer.java`.
O domínio usa `dev.yggdrasil.core.domain`; `Evaluator` pertence a
`dev.yggdrasil.core.application`.
