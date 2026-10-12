# Complementação do core

## Escopo autorizado

Corrigir as lacunas identificadas na revisão: datas civis, investigação,
componentes versionados, diagnósticos, benchmarks e preparação de distribuição.

## Datas e configuração

SAME_DATE aceita atributos `LocalDate` sem fuso. Literais `Instant` exigem fuso
na construção. Quando os tipos vêm do host, uma verificação anterior à avaliação
identifica dois instantes sem fuso e rejeita a configuração com identificação
da Leaf; nenhuma Tree contribui antes dessa verificação. Tipos mistos continuam
indeterminados. O core nunca usa o fuso da máquina como padrão.

## Explicação e investigação

`EvaluationOptions.DEFAULT` oculta valores. `INVESTIGATION` habilita observações
ordenadas dos operandos resolvidos, com posição, estado e valor imutável.
Ausência é distinta de nulo; nós não avaliados nunca capturam valores.
Diagnósticos de Leaves identificam atributo ausente, navegação inválida, nulo,
tipos incompatíveis, limites dinâmicos invertidos e conversão temporal fora
do calendário. Os motivos não contêm valores quando a captura está desabilitada.
A aplicação decide acesso e retenção dos resultados de investigação.

## Componentes publicados

`ComponentRevision` passa a carregar a definição imutável, além de UUID e revisão.
`ComponentCatalog` resolve explicitamente `(UUID, revisão)` e rejeita versões
conflitantes do mesmo componente. O Forest compara cada snapshot declarado
com a definição materializada usada na avaliação. Isso garante consistência
local; autenticidade de uma fonte externa e monotonicidade entre publicações
continuam pertencendo ao host. Não há consultas externas no avaliador.

## Validação e distribuição

Exemplos consumidores são compilados em pacote externo ao core, incluindo
aliases, datas civis, investigação e componentes publicados. Essa verificação
não representa aprovação de consumidores reais; o feedback fica registrado
como pendente até revisão pelo responsável.

Wrapper oficial Apache Maven, CI Java 25 com testes e smoke test modular.
Licença Apache-2.0 escolhida explicitamente pelo responsável. A aprovação de
consumidores reais fica pendente conforme sua escolha; os cenários estão em
[api-review.md](api-review.md).
Benchmarks exploratórios usam JVMs independentes, aquecimento, amostras de
latência e bytes alocados por operação; registram tamanho do Forest e ambiente.
Esses resultados do avaliador puro não comprovam p99 de integrações completas.

## Vocabulário aprovado

O responsável adotou `UNKNOWN` para o estado anteriormente chamado indeterminado. A substituição se aplica
a `Truth`, ao estado da avaliação, aos outcomes da explicação e à política
`unknownDecision` do Forest. `UNKNOWN_POLICY` identifica o motivo de encerramento.
É uma mudança de nomenclatura: score conhecido, short-circuit e política de risco
permanecem com a mesma semântica. O arquivo histórico preserva os nomes antigos.
