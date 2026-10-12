# Repositórios por módulo

Cada módulo é enviado para seu repositório na organização `Yggdrasil-java`.
Os nomes seguem `yggdrasil-<módulo>`; o adaptador usa `yggdrasil-neo4j`.
A visibilidade existente é preservada.

`scripts/export_repositories.py` prepara cópias independentes para commits,
sem mover fontes ou transformar o workspace em submódulos Git.
Cada cópia mantém o caminho original do módulo, o POM agregador limitado
àquele módulo, Wrapper, licença, notices e documentação de referência.
Os outros módulos fornecem apenas seus READMEs para preservar a navegação.

Use JDK 25. Execute `./mvnw install` neste workspace antes dos builds
individuais: as dependências entre módulos usam os artefatos locais
`0.1.0-SNAPSHOT`. Em uma máquina nova, instale os repositórios na ordem
core, authoring, history, extensions, experimentation, replay, administration,
neo4j. O adaptador requer o driver Neo4j e seus testes de integração exigem
um banco descartável. Nenhum artefato estável é publicado por este processo.

```sh
python3 scripts/export_repositories.py target/github-repositories
```

O destino deve estar ausente. As cópias excluem Git, builds e caches Python.
O workspace continua sendo a origem desta exportação inicial; sincronizações
posteriores precisam ser explícitas para preservar alterações remotas.
