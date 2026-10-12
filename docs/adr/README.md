# Architecture Decision Records

## Processo

Registrar problema, alternativas, prós e contras, decisão e consequências. Status `Aceito` representa direção validada pelo responsável; `Proposto` aguarda avaliação e validação. Não selecionar ferramentas pendentes durante a escrita documental.

| ADR | Status | Tema |
|---|---|---|
| [0001](0001-embedded-java-and-ddd.md) | Aceito | Biblioteca embarcada, Java 25 e DDD |
| [0002](0002-structured-tree-evaluation.md) | Aceito | Avaliação estruturada sem SpEL |
| [0003](0003-graph-persistence.md) | Aceito no requisito; ferramenta pendente | Persistência em grafo |
| [0004](0004-versioned-shadow-forests.md) | Aceito | Forests versionados, shadow e aprovação |
| [0005](0005-idempotency-and-version-control.md) | Aceito no princípio; contrato pendente | Idempotência e concorrência na autoria |
| [0006](0006-virtual-threads-and-terminal-operations.md) | Aceito no princípio; mecanismos pendentes | Consultas e cancelamento |
| [0007](0007-configuration-storage.md) | Proposto | Armazenamento de configurações |

| [0008](0008-core-scoring-and-terminal-decisions.md) | Aceito no comportamento; API pendente | Score aditivo e decisões terminais no core |

| [0009](0009-branch-composition.md) | Aceito no comportamento; detalhes pendentes | ALL, ANY e NOT com encerramento antecipado |

| [0010](0010-score-threshold-ranges.md) | Aceito no modelo; API pendente | Faixas de score por Forest |

| [0011](0011-structured-input-attributes.md) | Aceito no modelo; representação pendente | Atributos estruturados e distinção entre ausente e nulo |

| [0012](0012-initial-leaf-operators.md) | Aceito por famílias; detalhes pendentes | Operadores iniciais das Leaves |

| [0013](0013-decimal-numeric-policy.md) | Aceito na política; representação pendente | Precisão decimal e ausência de conversão de texto |

| [0014](0014-temporal-types.md) | Aceito na semântica; representação pendente | Instante e data civil sem conversão automática |

| [0015](0015-immutable-evaluation-revision.md) | Aceito na garantia; mecanismo pendente | Revisão imutável por avaliação |

| [0016](0016-revision-validation.md) | Aceito nas invariantes; contrato pendente | Validação estrutural de revisões |

| [0017](0017-evaluation-explanation.md) | Aceito no conteúdo; representação pendente | Explicação com valores ocultos por padrão |

| [0018](0018-forest-aggregate-root.md) | Aceito no limite; representação pendente | Forest como raiz do agregado |

| [0019](0019-draft-and-publication-lifecycle.md) | Aceito no ciclo; representação pendente | Rascunho, publicação e ativação |

| [0020](0020-element-identity-and-revision.md) | Aceito no modelo; mecanismos pendentes | UUID estável e revisão crescente |

| [0021](0021-core-contract-consolidation.md) | Validado pelo responsável | Consolidação do contrato funcional do core |

O ADR 0021 e a seção de consolidação de core.md resolvem os detalhes funcionais anteriormente pendentes. Status históricos preservam o contexto de cada decisão; mecanismos dos módulos adicionais continuam abertos.

| [0022](0022-java-core-implementation.md) | Implementado no escopo autorizado | Core Java 25, Maven e testes |

| [0023](0023-core-diagnostics-and-distribution.md) | Implementado no escopo autorizado | UNKNOWN, investigação, snapshots e distribuição |

| [0024](0024-consumer-and-release-candidate.md) | Preparado; publicação pendente | Consumidor independente e candidata 1.0.0 |

## Modelo para novos registros

| [0026](0026-optional-capabilities.md) | Implementação autorizada | Capacidades opcionais executáveis |

| [0027](0027-neo4j-history-adapter.md) | Neo4j escolhido; integração pendente | Adaptador opcional de histórico |

| [0025](0025-repository-layers.md) | Aceito pelo responsável | Camadas e organização baseada no TicketHub |

Título, status, contexto, opções com prós e contras, decisão, consequências e perguntas pendentes. ADR de ferramenta deve usar documentação oficial e evidência pertinente ao caso antes de aceitação.
