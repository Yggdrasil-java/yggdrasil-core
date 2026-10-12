# ADR 0016: Validação estrutural da revisão

## Status

Aceito nas invariantes. Registro: 2026-10-10. Contrato público de erros e mecanismo de validação pendentes.

## Contexto

Uma revisão executável deve ter estrutura válida antes de participar de decisões. Erros de configuração não devem ser confundidos com dados indisponíveis na avaliação.

## Alternativas, prós e contras

Verificar somente durante o percurso posterga trabalho de validação, mas pode produzir falhas dependentes do caminho executado. Validar antes de executar identifica problemas estruturais antecipadamente, mas exige percorrer referências e validar as faixas.

## Decisão

Rejeitar revisões com referências inexistentes, ciclos, faixas de score inválidas, Branches vazias ou NOT sem exatamente uma condição. Erros identificam o caminho do elemento inválido. Referências compartilhadas sem ciclos permanecem permitidas.

## Consequências e pendências

A garantia se aplica antes da execução; não exige repetir validação integral a cada evento quando a mesma revisão imutável já foi validada. Definir contrato de erros, identificação dos caminhos, validação de dependências de atributos e estratégia de cache das revisões válidas. Ausência de dado em entrada válida continua sujeita a UNKNOWN, não a erro estrutural de revisão.
