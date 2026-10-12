# Aplicação consumidora de referência

## Escopo

Criar uma aplicação HTTP Java 25 independente do agregador principal que usa
somente o JAR público do core. O host resolve atributos, consulta seu histórico,
aplica a ação correspondente à decisão e persiste o resultado. Nenhuma dessas
responsabilidades vira dependência do core.

## Contrato

`POST /evaluate` recebe formulário: `eventId` e `accountId` como UUIDs,
`amount` decimal, `suspicious` e `confirmedFraud` booleanos opcionais.
Dados opcionais ausentes continuam ausentes no input do core. A ordem é:
fraude confirmada terminal, valor alto, dispositivo suspeito e frequência
histórica. Score conhecido >=80 bloqueia; >=30 exige revisão; abaixo permite.
A política de UNKNOWN exige revisão. A resposta contém estado, decisão,
score, revisão e a ação do host, sem valores de investigação.

O histórico do host é um arquivo local append-only, com flush durável por evento.
Um UUID de evento repetido devolve a resposta persistida sem novo efeito.
Consultas e gravações são serializadas para manter consistência do histórico;
esse desenho serve à referência de uma instância, não a produção distribuída.
O histórico é recuperado ao iniciar. A consulta de frequência conta eventos
anteriores da mesma conta, inclusive decisões de revisão/bloqueio.

## Validação

Testar a API HTTP, decisões determinadas, UNKNOWN, idempotência e recuperação.
Medir o ciclo completo da aplicação: HTTP, parsing, consulta do histórico,
normalização, avaliação, ação e gravação durável. Usar carga concorrente local
e registrar latência/erros, sem generalizar os resultados para outra implantação.
Uma aprovação humana da ergonomia continua sendo uma etapa distinta.

## Publicação

Destino escolhido: GitHub Packages. Versão candidata: 1.0.0, sujeita à revisão
da API. Nome exato da organização/repositório ainda não foi informado. Não converter a
API inicial em 1.0.0 sem revisão explícita. A preparação local de release
não equivale a publicação externa nem execução hospedada da CI.

## Resultado local

O consumo Maven independente compilou. O fluxo sem HTTP passou nas verificações
de decisões, UNKNOWN, histórico, ações, idempotência e recuperação. Há
[medição com gravação durável](../benchmarks/results/application-in-process.md).
O teste HTTP falhou ao iniciar por bloqueio de sockets do sandbox e foi
preparado na CI, sem marcar execução hospedada como concluída.
