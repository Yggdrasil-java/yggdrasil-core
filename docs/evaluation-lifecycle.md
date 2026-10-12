# Ciclo de avaliação

## Fluxo conceitual proposto

1. Receber operação, identidade global, entidade inicial e evento.
2. Usar somente uma revisão estruturalmente válida: rejeitar referências inexistentes, ciclos, faixas inválidas, Branches vazias e NOT sem exatamente uma condição. Identificar a operação para idempotência e fixar uma revisão imutável do Forest, incluindo as versões dos componentes compartilhados. Publicações posteriores não alteram esta avaliação.
3. Identificar as Features necessárias. Sua resolução respeita a sequência explícita configurada dentro da Tree, em vez de exigir a resolução antecipada de todos os atributos.
4. Percorrer Trees na sequência explícita definida pelo Forest e somar, por padrão, contribuições das condições atendidas; uma Tree pode encerrar toda a avaliação com uma decisão terminal.
5. Aplicar thresholds ou a política de `UNKNOWN`.
6. Produzir decisão, revisão e explicação com identificadores, operadores, resultados e contribuições. Valores dos atributos ficam ocultos por padrão, com inclusão explícita opcional; persistência pertence aos módulos correspondentes.
7. Retornar o resultado à aplicação consumidora.

A ordem de persistência do evento e sua atomicidade com a decisão ainda precisam ser definidas. `includeCurrent` deve incluir o evento atual uma única vez, sem depender de duplicação na ingestão.

## Ordem dentro da Tree

Condições e resolução de atributos seguem a sequência explícita configurada. O motor não reordena os elementos automaticamente. ALL encerra na primeira condição falsa; ANY encerra na primeira verdadeira; NOT inverte uma única condição, preservando UNKNOWN como desconhecido. Os elementos restantes da Branch não são avaliados após seu encerramento. Em ALL, falsa prevalece; sem falsa, qualquer desconhecida resulta em UNKNOWN. Em ANY, verdadeira prevalece; sem verdadeira, qualquer desconhecida resulta em UNKNOWN. Uma condição desconhecida por si só não encerra a Branch: uma condição posterior ainda pode determinar seu resultado. Uma composição encerrada antecipadamente não equivale a uma decisão terminal de toda a avaliação.

## Dados ausentes e prazo

Quando os dados necessários não chegam, a avaliação pode resultar em `UNKNOWN`. Cada conjunto define o tratamento de acordo com o risco de negócio, permitindo bloquear ou não. Ausência de dado não equivale a score zero. Uma Tree indeterminada não contribui para o score. Se houver alguma Tree indeterminada e nenhuma decisão terminal, o resultado do Forest será UNKNOWN, com o score conhecido preservado e a política de risco do conjunto aplicada. O contrato de retorno deve distinguir a indeterminação da disposição operacional configurada (bloquear ou não); sua representação e a distinção entre desconhecido, timeout e erro serão validadas.

## Operação terminal

A primeira decisão terminal encontrada na sequência explícita das Trees encerra a avaliação inteira. Trees posteriores não são avaliadas. Deve impedir início de novo trabalho e solicitar cancelamento de consultas e tarefas pendentes. Cancelamento não garante interrupção imediata em todo driver ou fonte externa. A implementação deve impedir que resultados tardios modifiquem uma decisão encerrada.

A explicação diferencia condições avaliadas, não avaliadas, canceladas e sem dados. Quando uma Tree produzir contribuição e decisão terminal, somar sua contribuição antes de encerrar. Retornar o score acumulado até o encerramento, incluindo essa contribuição quando configurada, identificado como parcial, e indicar a Tree responsável e as Trees não avaliadas. Uma decisão terminal encerra a avaliação mesmo se uma Tree anterior tiver resultado indeterminado; a explicação preserva essa informação.

## Concorrência

Consultas usarão virtual threads, respeitando limites de conexões e capacidade das fontes. Paralelismo, prazos, propagação de cancelamento e orçamento por consulta serão definidos antes de implementação.

Idempotência de operações e versão esperada em alterações são requisitos distintos. Ordenação das avaliações de uma mesma entidade permanece aberta.
