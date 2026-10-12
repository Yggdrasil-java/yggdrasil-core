# Replay e backfill

## Dry-run

Avalia sem executar ações nem modificar o histórico operacional. Pode produzir um relatório separado de auditoria e comparação. Consultas e agregações não devem contaminar estado Live.

## Replay fiel

Reproduz a avaliação usando revisão, valores e contexto disponíveis no momento original. Registrar apenas IDs de entidades não é suficiente se os dados podem mudar. Estratégia de snapshots, valores capturados e reconstrução temporal ainda será decidida.

## Simulação histórica

Aplica um novo Forest a eventos antigos. Cada evento usa somente dados disponíveis até aquele momento, sem acesso a informações futuras. Tempo do evento, tempo de ingestão e relações históricas fazem parte da definição a validar.

## Backfill

Preenche ou recalcula atributos e estado a partir de eventos históricos. Não equivale a replay de uma decisão. Reutilizar a semântica dos cálculos em tempo real e históricos é uma proposta inspirada nas referências.

Idempotência, eventos atrasados, correções, checkpoint, retomada e limites de recursos precisam ser especificados. Backfill e simulação não devem executar ações de produção.

Ainda serão definidos retenção suficiente para reprodução, determinismo dos plugins e comportamento quando dados históricos não estiverem disponíveis.

## Implementação local

[ReplayService](../replay/README.md) usa `EvaluationSnapshot` com Forest, input,
opções e resultado original. Replay não executa plugins novamente: compara o
resultado completo sobre valores capturados. Simulação aplica a candidata aos
snapshots de cada evento, ordenados temporalmente. O host deve fornecer dados
do horizonte correto, sem preencher snapshots com informações atuais.

`BackfillService` processa lotes em namespaces `backfill/...`. O repositório
confirma saída e checkpoint atomicamente por evento; versões desatualizadas
não sobrescrevem progresso. Falha de projeção preserva eventos confirmados.
Retomada não duplica resultados; projeções puras podem executar novamente
quando um commit falha. Memória permite retomada entre chamadas, mas não
sobrevive ao encerramento do processo.

Inputs capturados podem ser sensíveis mesmo com explicações sem valores.
Captura e persistência exigem política explícita do host.
