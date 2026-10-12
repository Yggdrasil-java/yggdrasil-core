# Core

## Responsabilidade

Avaliar um Forest sobre um evento e atributos fornecidos pela aplicação consumidora. O core pode ser importado isoladamente, sem banco, frontend ou plugins. Integrações adicionais fornecem capacidades por contratos a definir.

## Comportamentos aceitos

- Forest é a raiz do agregado, responsável pela ordem das Trees, pelos thresholds e pela validade da revisão.
- Cada Forest contém Trees e uma configuração de thresholds.
- Componentes reutilizáveis são referenciados por identidade e versão, fixadas ao publicar a revisão.
- Cada avaliação usa uma revisão imutável do Forest, incluindo as versões dos componentes compartilhados. Mudanças publicadas durante a execução afetam apenas avaliações seguintes.
- Thresholds definem faixas de score associadas a decisões. As faixas devem cobrir os scores possíveis sem lacunas nem sobreposições; limites inclusivos e exclusivos precisam ser explícitos.
- UNKNOWN possui uma política separada das faixas de score.
- Decisões são extensíveis: ALLOW, REVIEW e BLOCK são oferecidas, e a aplicação pode definir outras, como REQUIRE_AUTHENTICATION.
- O core devolve a decisão; a aplicação consumidora executa a ação de negócio correspondente.
- Atributos de entrada aceitam objetos aninhados, listas, textos, booleanos, números e datas. Leaves acessam caminhos como transaction.amount e device.trusted.
- Dados temporais distinguem instante (momento com referência global) e data civil (dia sem horário). Não há conversão automática entre eles; conversões solicitadas por regras devem informar o fuso horário.
- Campo ausente e valor nulo são estados distintos; o acesso aos atributos deve preservar essa diferença.
- O comportamento padrão é somar as contribuições de score das Trees cujas condições forem atendidas.
- Scores e comparações numéricas usam precisão decimal. Textos como "100" não são convertidos automaticamente em números.
- Contribuições podem ser positivas ou negativas. Não há escala obrigatória, como 0–100, nem normalização automática para essa faixa.
- Uma Tree também pode produzir uma decisão terminal, como BLOCK, encerrando toda a avaliação.
- O Forest define uma sequência explícita de Trees: a avaliação segue a ordem configurada, indicando o que resolver primeiro e em seguida.
- A ordem também é explícita dentro de cada Tree, incluindo condições e resolução de atributos. O motor respeita a sequência configurada, sem reordenar esses elementos automaticamente.
- A primeira decisão terminal encontrada nessa sequência encerra a avaliação; Trees posteriores não são avaliadas.
- Branches usam ALL, ANY e NOT. ALL exige todas as condições e encerra na primeira falsa; ANY exige pelo menos uma e encerra na primeira verdadeira; NOT inverte uma única condição e mantém UNKNOWN como desconhecido.
- Encerrar uma Branch não equivale a uma decisão terminal: a avaliação das demais Trees continua quando aplicável.
- Em ALL, falsa prevalece; sem falsa, qualquer condição desconhecida resulta em UNKNOWN. Em ANY, verdadeira prevalece; sem verdadeira, qualquer desconhecida resulta em UNKNOWN.
- Uma Tree indeterminada não contribui para o score. Sem decisão terminal, sua presença torna o resultado do Forest UNKNOWN, preservando o score conhecido e aplicando a política de risco do conjunto.
- Uma Tree pode contribuir para o score e produzir uma decisão terminal ao mesmo tempo. Somar sua contribuição antes do retorno terminal.
- Uma decisão terminal devolve o score acumulado até o encerramento, incluindo a contribuição da Tree terminal quando configurada, identificado como parcial. A explicação identifica a Tree terminal e as Trees não avaliadas.
- O resultado apresenta decisão, score, revisão utilizada e explicação.
- Operação terminal impede novo trabalho e solicita cancelamento das pendências quando existirem integrações em execução.
- Dados necessários indisponíveis devem ser representados por UNKNOWN, com tratamento configurável conforme o risco do negócio.

## Operadores iniciais das Leaves

Conjunto aceito por famílias; nomes públicos dos operadores ainda serão validados.

| Família | Operações |
|---|---|
| Igualdade | Igualdade |
| Números | Comparações numéricas |
| Texto | Contém, começa com e termina com |
| Coleções | Pertence, contém algum e contém todos |
| Datas | Antes, depois, entre e SAME_DATE (mesma data civil) |
| Presença | Existe, está ausente, é nulo e está vazio |

Uma Leaf pode comparar um atributo com um valor fixo ou comparar dois atributos, como transaction.amount > account.dailyLimit. A representação pública dos operandos ainda será definida.

SAME_DATE compara se os valores representam a mesma data civil. Para duas datas civis, a comparação é direta. Para dois instantes, SAME_DATE exige um fuso explícito na Leaf para determinar o dia de ambos. Não usar o fuso da máquina como padrão nem converter tipos temporais diferentes automaticamente. Uma Leaf SAME_DATE destinada a instantes sem esse fuso é configuração inválida.

BETWEEN inclui os dois limites por padrão. Cada limite pode ser configurado explicitamente como exclusivo. Essa regra trata o operador BETWEEN; não altera a exigência de faixas de thresholds sem lacunas ou sobreposições.

Comparações de texto distinguem maiúsculas de minúsculas por padrão. A Leaf pode solicitar explicitamente comparação sem essa distinção, como uma igualdade no estilo equalsIgnoreCase. O nome e a representação pública dessa opção ainda serão definidos.

Comparações com campos ausentes ou tipos incompatíveis retornam UNKNOWN. Operadores de presença tratam esses estados explicitamente, conforme seu propósito. Ausente e nulo permanecem distintos.

O comportamento de comparação com nulo, validação dos valores dos limites, representação decimal, regras de arredondamento, representação temporal e conversões explícitas, semântica Unicode das comparações sem distinção de caixa e semântica de vazio ainda precisa de definição. As famílias aceitas não autorizam coerções silenciosas ou execução de expressões arbitrárias.

## Explicação da avaliação

Registrar identificador da condição, operador, resultado e contribuição ao score quando aplicável. A contribuição pertence à Tree; não duplicá-la como contribuição de cada Leaf se a regra tiver múltiplas condições.

Valores dos atributos ficam ocultos por padrão. A aplicação consumidora pode habilitar explicitamente sua inclusão para investigação. A representação dessa opção e seu alcance ainda serão definidos.

A explicação identifica a revisão utilizada, a Tree terminal quando houver, score parcial e elementos não avaliados. Uma comparação sem dados deve permanecer distinguível de uma comparação falsa.

## Identidade e revisão

Forest, Tree, Branch e Leaf possuem UUID estável, preservado ao renomear ou reordenar o mesmo elemento. O Forest possui revisão numérica crescente; elementos internos são versionados pela revisão do conjunto, sem exigir revisão própria para cada folha.

Componentes reutilizáveis independentes possuem UUID e revisão próprios. O Forest fixa essas referências na publicação. Uma cópia independente recebe novo UUID; reutilização por referência preserva o UUID do componente original.

O nome é texto editável para leitura e não substitui identidade. A representação de nomes na API permanece a detalhar. A variante de UUID e o mecanismo de alocação das revisões ainda não foram escolhidos.

## Ciclo de autoria

Distinguir rascunho editável de revisão publicada imutável. O rascunho pode estar incompleto; publicar exige aprovação de todas as validações estruturais e fixa as versões dos componentes reutilizáveis.

Publicar não significa ativar em produção. Na composição com experimentação, a revisão segue para shadow e aprovação antes de ativar como Live Forest. Essa integração não torna shadow, frontend ou persistência dependências obrigatórias do core isolado.

## Validação da revisão

Antes de executar uma revisão, rejeitar configurações com referências inexistentes, ciclos, faixas de score inválidas, Branch vazia ou NOT sem exatamente uma condição. Erros devem identificar o caminho do elemento inválido.

Compartilhamento de componentes continua permitido: referências compartilhadas sem ciclos não são motivo de rejeição. Erro estrutural de configuração é distinto de UNKNOWN causado por dados de entrada insuficientes ou incompatíveis.

## Contrato conceitual proposto

Entrada: revisão do Forest, entidade inicial, evento e atributos disponibilizados pelo host.

Saída: referência à revisão avaliada, decisão, score e explicação. Identificadores da avaliação, representação dos tipos e assinatura da API permanecem pendentes. Este contrato não define formato JSON nem classes Java definitivas.

O core calcula um resultado. A aplicação consumidora executa as ações de negócio correspondentes; o core não realiza essas ações.

## Exemplo ilustrativo

Os nomes das Trees e os números abaixo são exemplos, não valores padrão da biblioteca. ALLOW, REVIEW e BLOCK são decisões oferecidas pelo core.

| Tree | Condição | Contribuição |
|---|---|---|
| high-amount | transaction.amount > 1000 | 30 |
| suspicious-device | device.suspicious = true | 50 |

Se ambas forem atendidas, a soma é 80. Um Forest com configuração ilustrativa de ALLOW abaixo de 30, REVIEW de 30 até abaixo de 80 e BLOCK a partir de 80 produz BLOCK. A revisão usada e as contribuições aparecem na explicação.

Uma Tree com condição device.confirmedFraud = true pode produzir BLOCK terminal, sem precisar atingir um threshold pela soma. A explicação precisa identificar que a decisão veio de uma operação terminal. O score retornado é o acumulado até o encerramento, identificado como parcial. A ordem de avaliação é explícita no Forest.

## Detalhes consolidados sob autorização

O responsável autorizou definir autonomamente os detalhes restantes do core e depois validou os seis grupos de consolidação, acrescentando suporte a aliases e padronização temporal ISO. Estas definições consolidam o contrato funcional; nomes de classes, assinatura Java e empacotamento serão tratados na implementação.

### Operadores e operandos

Nomes: EQUAL, NOT_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, CONTAINS, STARTS_WITH, ENDS_WITH, IN, CONTAINS_ANY, CONTAINS_ALL, BEFORE, AFTER, BETWEEN, SAME_DATE, EXISTS, IS_MISSING, IS_NULL e IS_EMPTY.

NOT_EQUAL é a negação de EQUAL e preserva UNKNOWN. BETWEEN também é disponibilizado para números, com a mesma política de limites aceita para datas. Operandos são explicitamente literais ou referências a atributos; textos nunca são interpretados como caminhos automaticamente. BETWEEN recebe dois limites; SAME_DATE recebe fuso quando aplicado a instantes.

### Presença e nulo

EXISTS é verdadeiro para campo presente, inclusive nulo. IS_MISSING é verdadeiro somente para campo ausente. IS_NULL é verdadeiro somente para campo presente com valor nulo, sendo falso para ausente. IS_EMPTY é verdadeiro para texto, lista ou objeto sem elementos; ausente, nulo e tipos não aplicáveis produzem UNKNOWN. Texto composto de espaços não é vazio.

EQUAL aceita nulo explicitamente: dois nulos são iguais, nulo e valor não nulo são diferentes; campo ausente continua UNKNOWN. Demais comparações com nulo produzem UNKNOWN. Não converter texto em número ou data.

### Caminhos e tipos

Aliases podem dar nomes legíveis a caminhos de atributos, por exemplo transactionAmount para o caminho transaction.amount. O alias aponta explicitamente para um caminho segmentado; não contém expressão executável nem converte valores. Seu mapeamento integra a revisão imutável do Forest. Validar nomes únicos, colisões e referências não resolvidas; na primeira versão, aliases apontam diretamente para caminhos, sem encadeamento entre aliases.


Representar o caminho por segmentos tipados de chave de objeto ou índice de lista. A notação transaction.amount é uma forma de apresentação para chaves simples, não uma linguagem de expressão. Chaves contendo pontos permanecem um único segmento quando informadas explicitamente. Índices começam em zero. Chave inexistente ou índice fora da lista resulta em ausente; navegação por tipo incompatível ou por nulo intermediário resulta em UNKNOWN. Não oferecer curingas, chamadas de métodos ou execução de código na primeira versão.

Números têm semântica decimal e representação de referência BigDecimal, sem arredondamento ou limitação automática do score. Comparação numérica considera 1.0 e 1.00 equivalentes. Rejeitar valores numéricos não finitos na normalização. O contrato inicial de score é aditivo e não exige divisão ou política implícita de arredondamento.

A representação textual de tempo é padronizada em ISO 8601. Instantes usam UTC com sufixo Z, por exemplo 2026-10-10T15:30:00Z; datas civis usam YYYY-MM-DD, por exemplo 2026-10-10. Frações de segundo são preservadas quando presentes, sem truncamento automático. Valores de data/hora sem offset não são aceitos como instantes.

ISO 8601 define a representação, não elimina a distinção entre os tipos. Instantes têm representação de referência Instant; datas civis, LocalDate; fusos, ZoneId. Identificadores de fuso são explícitos, como America/Sao_Paulo; não são substituídos por uma data ISO. BEFORE, AFTER e BETWEEN exigem operandos da mesma categoria temporal. A conversão entre instante e data civil não faz parte dos operadores iniciais: deve ser explícita na obtenção de atributos. Isso evita escolher automaticamente um horário ao converter data civil em instante.

### Igualdade, texto e coleções

Igualdade compara textos, booleanos, números e valores temporais compatíveis. Listas usam comparação estrutural em ordem; objetos usam suas chaves e valores, sem depender da ordem das chaves. Tipos incompatíveis preservam UNKNOWN. Em comparações estruturais, uma diferença determinada prevalece; sem diferença determinada, incompatibilidade interna mantém UNKNOWN.

Texto distingue caixa por padrão. A opção explícita ignoreCase usa comparação independente do locale da máquina, sem remover acentos, espaços ou normalizar conteúdo implicitamente. Para igualdade, adotar a semântica Java de equalsIgnoreCase; para contém, prefixo e sufixo, aplicar comparação de regiões sem distinção de caixa. Unicode case folding completo não é prometido.

IN procura o operando esquerdo na coleção direita. CONTAINS_ANY e CONTAINS_ALL recebem coleções à esquerda e à direita e verificam, respectivamente, algum ou todos os elementos da direita na esquerda. Duplicatas não multiplicam score nem exigem multiplicidade. Coleção direita vazia torna CONTAINS_ANY falso e CONTAINS_ALL verdadeiro. Comparações internas desconhecidas seguem a lógica de ANY/ALL aprovada. CONTAINS inicial é de texto; presença em coleção usa IN.

### Resultado e explicação

Separar estado de avaliação de decisão operacional. Uma avaliação determinada retorna decisão obtida por threshold ou por Tree terminal. Uma avaliação indeterminada retorna estado UNKNOWN e a decisão operacional configurada no Forest. Isso mantém a informação de incerteza mesmo quando a política escolhe bloquear ou permitir.

O resultado contém identidade e revisão do Forest, decisão operacional, estado determinado/indeterminado, score acumulado, indicador de score parcial, motivo de encerramento e explicação ordenada. Score é parcial quando existe Tree indeterminada ou encerramento terminal. A explicação distingue verdadeiro, falso, indeterminado, não avaliado e cancelado quando aplicável, sem atribuir falsidade ao trabalho omitido.

Definir uma política explícita para UNKNOWN em toda revisão executável. ALLOW, REVIEW e BLOCK são decisões oferecidas; decisões adicionais têm identificadores não vazios e únicos no catálogo da revisão. O core não persiste resultados nem executa ações. Relatórios persistidos e captura de dados para replay pertencem aos módulos correspondentes.

Um Forest vazio é inválido na primeira versão. Uma Tree contém uma condição raiz e pelo menos uma contribuição ou decisão terminal. Uma Tree cuja condição é falsa contribui zero e não dispara decisão terminal.

### Validação e fronteiras

Validar operandos obrigatórios, aridade, identificadores, limites invertidos, fuso inválido e referências versionadas antes da execução. Tipos fornecidos dinamicamente pelo host continuam sujeitos a UNKNOWN quando incompatíveis. As faixas de score devem cobrir todo o domínio decimal, com extremos sem limite quando necessário, e não podem formar intervalos vazios.

Os UUIDs são opacos ao domínio; nenhuma variante específica é exigida pelo contrato inicial. Revisões publicadas usam números inteiros positivos crescentes por identidade. O token de concorrência do rascunho é separado da revisão publicada. Geração e persistência desses valores pertencem à autoria e à integração, não ao avaliador puro.

A avaliação core é síncrona e respeita a sequência explícita, sobre dados fornecidos pelo host. Virtual threads se aplicam às pesquisas nos módulos de integração; não impõem paralelismo às condições do core. A API preserva extensão futura sem tornar consultas ou plugins dependências obrigatórias.

## Estado da especificação

O contrato funcional inicial está consolidado. A implementação deverá materializar os tipos e validar os cenários de score, short-circuit, ausência de dados, referências e explicação. Escolhas de banco, frontend, build, publicação de artefatos, distribuição de revisões e contratos dos módulos adicionais continuam fora desta consolidação.
