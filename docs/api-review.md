# Revisão da API por consumidores

## Estado

Cenários de consumo verificáveis foram criados conforme solicitação do responsável.
Aprovação de consumidores reais permanece pendente, por escolha explícita.
Não foi enviado convite, publicada versão ou solicitado feedback externo.

## Cenários executáveis

O exemplo [CoreConsumer.java](../examples/api-consumer/src/main/java/dev/yggdrasil/examples/CoreConsumer.java) fica fora do pacote
do core e é compilado contra o JAR, em modo JPMS. Executar após o build:

```sh
python3 scripts/verify_consumer.py
```

Ele verifica score e thresholds, aliases, privacidade padrão, captura explícita,
diagnóstico de ausência, datas civis sem fuso, resolução de uma revisão publicada,
rejeição de definição materializada divergente e decisões terminais customizadas.
A CI executa os mesmos cenários em Linux e Windows.

## Critérios para aprovação posterior

O [consumidor modular](../examples/modular-consumer/README.md) exercita os sete
artefatos. Execute `python3 scripts/verify_modular_consumer.py` após o build.
Ele verifica janelas sem dados futuros, provider com versão fixada, isolamento
Live/Shadow, aprovação, promoção, replay, backfill em lotes e rollback.

Revisar também versões esperadas, UUIDs de operação, conflitos de repetição,
horizontes temporais, namespaces de estado e autorização por Forest. Adaptação
ao banco de grafo e frontend não fazem parte da API Java entregue nesta etapa.

O consumidor deve revisar se consegue modelar seus eventos, construir e publicar
regras, interpretar diagnósticos e executar ações usando o resultado sem casts
sobre o domínio. Deve avaliar clareza dos nomes, uso de UUIDs, ergonomia dos
construtores e conveniência de builders antes de estabilizar a API.

Registrar caso de uso, versão avaliada, dificuldades encontradas, mudanças
solicitadas e decisão do responsável. Os exemplos compiláveis comprovam
consumo técnico; não substituem essa aprovação.

## Candidata estável

A [revisão 1.0.0](release-1.0.0-review.md) reúne nomes, assinaturas, limites e
evidências para aprovação. A [aplicação consumidora](consumer-application.md)
compila como projeto Maven independente e verifica o fluxo com histórico
durável. Aprovação humana e execução HTTP hospedada permanecem distintas
das verificações já executadas localmente.
