# Verificação dos artefatos publicados

Após o deploy, `scripts/verify_published_consumer.py` baixa POM pai, POMs dos
módulos, binários, fontes e Javadoc diretamente do registro. Confere os hashes
da candidata e compila/executa `examples/api-consumer/src/main/java/dev/yggdrasil/examples/CoreConsumer.java` contra o binário
baixado em um diretório temporário vazio. Não utiliza o JAR do workspace.

```sh
python3 scripts/verify_published_consumer.py --version 1.0.0 --repository ORGANIZACAO/REPOSITORIO
```

Para GitHub Packages, configure `GITHUB_ACTOR` e `GITHUB_TOKEN` no ambiente.
As credenciais não são registradas no relatório. Além do core isolado, executa
a composição dos sete módulos e três builds Maven com os arquivos baixados,
em repositório isolado sem produtos Yggdrasil preexistentes. O workflow de publicação
executa essa verificação automaticamente e preserva a evidência em
`target/releases/1.0.0/published-consumer.json`.

O parâmetro `--registry-url file:///CAMINHO` permite verificar o mesmo fluxo
com um registro Maven local. Esse ensaio verifica os arquivos e a resolução
transitiva Maven, mas não comprova acesso ao GitHub ou permissões de pacotes. A aplicação
independente continua sendo a verificação da integração Maven; sua versão
pode ser selecionada com `-Dyggdrasil.version=1.0.0`.

Offline, passe `--offline --bootstrap-repo /CAMINHO/DO/CACHE` para reutilizar
dependências de terceiros. Produtos Yggdrasil desse cache são descartados
antes de inserir os arquivos baixados.

Falhas posteriores ao deploy não desfazem a publicação. O manifest registra
a publicação; o relatório do consumidor só registra sucesso após a execução.
