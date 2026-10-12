# ADR 0012: Operadores iniciais das Leaves

## Status

Aceito por famílias e tratamento de ausente/tipo incompatível. Registro: 2026-10-10. Nomes públicos e detalhes semânticos pendentes.

## Contexto

Condições simples precisam avaliar atributos estruturados sem SpEL e preservar incerteza quando a comparação não for possível.

## Alternativas, prós e contras

Operadores estruturados permitem validar tipos e explicar resultados, mas exigem semântica explícita. Expressões arbitrárias oferecem maior liberdade, mas ampliam a superfície do contrato e não atendem à direção acordada. Converter dados incompatíveis silenciosamente pode simplificar entradas, mas esconder erros de integração.

## Decisão

Oferecer igualdade e comparações numéricas; texto contém, começa com e termina com; coleções pertence, contém algum e contém todos; datas antes, depois, entre e SAME_DATE; presença existe, está ausente, é nulo e está vazio.

Comparações com campos ausentes ou tipos incompatíveis retornam UNKNOWN. Operadores de presença tratam estados explicitamente, preservando a distinção entre ausente e nulo.

Comparações de texto distinguem maiúsculas de minúsculas por padrão. A Leaf pode solicitar explicitamente comparação sem essa distinção, como igualdade no estilo equalsIgnoreCase. A configuração e os nomes públicos ainda serão definidos.

Leaves podem comparar um atributo com valor fixo ou dois atributos. SAME_DATE compara datas civis diretamente. Para dois instantes, exige fuso explícito na Leaf e usa esse fuso para determinar o dia de ambos. Não usar o fuso da máquina como padrão. Configuração de SAME_DATE destinada a instantes sem fuso explícito é inválida.

BETWEEN inclui ambos os limites por padrão e permite configurar cada limite como exclusivo. Essa escolha não altera a validação das faixas de thresholds do Forest.

## Consequências e pendências

Definir identificadores públicos, comparações com nulo, igualdade entre tipos, representação decimal e arredondamento, validação dos valores dos limites de intervalos, representação temporal e conversões explícitas com fuso, semântica Unicode e representação da opção de comparação sem distinção de caixa, sentido dos operandos de coleção e semântica de vazio. Não adicionar automaticamente regex ou operadores temporais relativos sem validação.
