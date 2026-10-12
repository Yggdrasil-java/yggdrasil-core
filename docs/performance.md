# Desempenho

## Meta acordada

- Base de referência: 1 milhão de usuários.
- Taxa de referência: 10 mil chamadas por minuto, aproximadamente 167 por segundo.
- Latência: p99 de até 1 segundo, incluindo consultas e persistência da avaliação.
- Consultas executadas com virtual threads em Java 25.

A meta é requisito a verificar, não garantia já demonstrada. Quantidade de eventos, número de regras, compartilhamento de componentes e distribuição de carga ainda não foram dimensionados.

## Critérios de avaliação propostos

Medir avaliação simples, consultas por janela temporal, relações compartilhadas, entidades com grande histórico, concorrência concentrada, atualização de conjuntos, timeout, cancelamento e execução simultânea de shadow/backfill.

Medir latência completa e por fase; percentis; vazão; erros; saturação das conexões; memória; custo de consultas; tempo de propagação de mudanças. Incluir erros e timeouts no relatório, sem ocultá-los ao calcular a meta.

Virtual threads não substituem controle de concorrência, limites das fontes ou índices adequados. Operação terminal reduz trabalho restante quando sua semântica permite encerrar a decisão, mas não resolve por si só lentidão de dados.

## Pontos pendentes

Picos de carga; volume e retenção de eventos; orçamento por fonte; tamanho de Forest; número de candidatos; metas para publicação; ambiente de benchmark e infraestrutura de referência.

## Avaliador puro

Há um [harness exploratório](../benchmarks/README.md) para latência, alocação
e tamanho de Forest, com resultados brutos e relatório de referência. Essa
medição não substitui os critérios de avaliação completa descritos acima.
