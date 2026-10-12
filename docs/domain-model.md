# Modelo de domínio

## Responsabilidades propostas

Autoria define estruturas válidas. Avaliação percorre regras e produz resultados. Histórico resolve identidades, relações e eventos. Experimentação compara revisões e governa promoção. Reprodução reconstrói avaliações e atributos históricos.

Essas responsabilidades são candidatas a limites de contexto DDD, não bounded contexts ou agregados definitivamente escolhidos. Os demais limites de consistência serão definidos a partir dos casos de uso.

## Agregado Forest

Forest é a raiz do agregado do core. Governa a sequência explícita das Trees, os thresholds e a validade da revisão. Uma revisão executável é imutável e preserva essas invariantes durante a avaliação.

Componentes reutilizáveis são referenciados por identidade e versão, fixadas ao publicar a revisão. Alterar um componente compartilhado não modifica retroativamente um Forest publicado. O agregado não incorpora o histórico de entidades nem define uma transação conjunta com as fontes externas.

Rascunhos são editáveis e podem estar incompletos. Publicação exige validação e produz uma revisão imutável; não equivale a ativação em produção.

Forest, Tree, Branch e Leaf possuem UUID estável. Forest tem revisão numérica crescente; seus elementos internos ficam versionados por essa revisão. Componentes reutilizáveis independentes possuem UUID e revisão próprios. Cópia independente recebe novo UUID; reutilização por referência preserva o original.

A variante de UUID, a alocação das revisões, a representação de rascunhos, a titularidade dos componentes compartilhados e as demais fronteiras de agregados permanecem pendentes.

## Invariantes acordadas

- Somente uma revisão aprovada determina a decisão de produção de um conjunto em cada configuração de ativação.
- Candidatas não executam ações de produção.
- Mudanças concorrentes verificam a versão esperada: uma alteração aceita invalida tentativas baseadas na versão anterior.
- Repetição da mesma operação lógica não duplica efeitos.
- Uma condição de ausência de dados não se transforma silenciosamente em aprovação ou bloqueio: o conjunto configura o tratamento de `UNKNOWN`.
- `includeCurrent` controla a participação do evento atual em uma consulta.
- Componentes compartilhados devem ser vinculados à revisão usada pela avaliação, para permitir reprodução.

- Cada Tree define também a ordem explícita de condições e resolução de atributos, sem reordenação automática.
- O Forest define uma ordem explícita de Trees. A primeira decisão terminal na sequência encerra a avaliação; as Trees seguintes não são avaliadas.
- O padrão de score soma as contribuições das Trees atendidas. Uma Tree pode encerrar a avaliação com uma decisão terminal.

- Branches usam ALL, ANY e NOT, respeitando a ordem configurada. ALL encerra na primeira falsa; ANY, na primeira verdadeira. NOT possui uma única condição e preserva UNKNOWN.

- Decisão terminal retorna o score acumulado até o encerramento, identificado como parcial, com a Tree responsável e as Trees não avaliadas na explicação.

- Uma Tree indeterminada não contribui para o score. Sem decisão terminal, qualquer Tree indeterminada torna o Forest UNKNOWN, preservando o score conhecido e aplicando a política de risco do conjunto.

- Scores admitem contribuições positivas e negativas, sem escala obrigatória ou normalização automática para 0–100.

- Thresholds são faixas de score configuradas por Forest, sem lacunas ou sobreposições. UNKNOWN usa uma política separada.

- O core oferece ALLOW, REVIEW e BLOCK, permite decisões definidas pela aplicação e apenas retorna o resultado. A aplicação consumidora executa a ação correspondente.

- Atributos aceitam objetos aninhados, listas, textos, booleanos, números e datas, acessados por caminhos. Campo ausente é distinto de valor nulo.

- Leaves oferecem igualdade, comparação numérica, operações de texto e coleções, comparação temporal e presença. Comparações com campos ausentes ou tipos incompatíveis retornam UNKNOWN; presença trata os estados explicitamente.

- Scores e comparações numéricas usam precisão decimal; textos não são convertidos automaticamente em números.

- Instante e data civil são tipos temporais distintos. Não há conversão automática entre eles; conversões solicitadas por regras exigem fuso explícito.

- Cada avaliação fixa uma revisão imutável do Forest e dos componentes compartilhados. Publicação durante a execução não altera a avaliação em andamento.

- Revisões executáveis rejeitam referências inexistentes, ciclos, faixas inválidas, Branches vazias e NOT sem exatamente uma condição. Erros identificam o caminho inválido; compartilhamento sem ciclos é permitido.

- Uma Tree pode contribuir para o score e produzir uma decisão terminal simultaneamente. Sua contribuição é somada antes do retorno terminal.

- Comparações de texto distinguem maiúsculas de minúsculas por padrão; a Leaf pode solicitar explicitamente comparação sem essa distinção, como equalsIgnoreCase.

- A explicação registra identificadores, operadores, resultados e contribuições ao score quando aplicáveis. Valores dos atributos ficam ocultos por padrão; sua inclusão exige configuração explícita da aplicação.

## Pontos a validar

Identidade de operações e prazo de retenção das chaves de idempotência; demais fronteiras de agregados; representação decimal, arredondamento e limites dos scores; operadores; contratos de tipos; consistência de avaliações simultâneas; controle de publicação entre instâncias.

Controle de versão na autoria não estabelece ordenação de transações durante avaliação. A resposta sobre idempotência foi documentada sem inferir serialização por usuário, empresa ou outra entidade.
