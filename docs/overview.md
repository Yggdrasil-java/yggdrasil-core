# Yggdrasil

Biblioteca pública e modular de motor de regras, embarcada em aplicações Java 25, voltada a antifraude e decisões complexas. Regras podem mudar sem reiniciar a aplicação consumidora. A avaliação opera diretamente sobre estruturas de árvores, sem SpEL.

## Direção validada

- DDD orienta o modelo, as responsabilidades e as invariantes.
- `Forest` representa um conjunto completo versionado; `Tree`, uma regra.
- Qualquer entidade ou evento pode iniciar uma avaliação. A identidade global e o tipo da entidade inicial são configuráveis.
- A aplicação consumidora escolhe quais módulos importar: somente `core` ou uma composição que pode incluir todos os módulos.
- O `core` avalia regras com dados fornecidos pelo host, sem exigir banco, frontend ou plugins.
- Banco de grafo é obrigatório no módulo de histórico e relações. Produto e distribuição dos dados ainda serão avaliados.
- Consultas de histórico, agregadores e backfill são capacidades modulares.
- O Forest define a ordem explícita de avaliação das Trees; cada Tree define a sequência de condições e resolução de atributos. A primeira decisão terminal encerra a avaliação.
- O core soma contribuições de score por padrão e permite decisões terminais por Tree. Ver [contrato do core](core.md).
- Cada conjunto define score, thresholds e tratamento de `UNKNOWN` conforme o risco do negócio.
- Mudanças são avaliadas em shadow e promovidas mediante aprovação de usuário autorizado.
- Há dry-run, replay fiel e simulação histórica.
- Meta: 1 milhão de usuários, 10 mil chamadas por minuto e p99 de até 1 segundo, incluindo consultas e persistência.
- Consultas usam virtual threads; operações terminais encerram a avaliação e solicitam cancelamento do trabalho pendente.
- Operações são idempotentes. Alterações pelo frontend usam a versão esperada para impedir sobrescritas concorrentes.

## Documentação

O ponto de entrada é [README.md](README.md). As decisões aceitas e propostas estão em [ADR](adr/README.md).

A consolidação anterior de código e documentação foi preservada em [legacy-backend.md](archive/legacy-backend.md). Ela é referência histórica e não define a arquitetura atual.

## Estado do projeto

Implementação inicial do core Java 25 com Maven e testes. Ver
[contrato Java](java-core.md). Módulos adicionais e publicação
continuam pendentes; a biblioteca não exige infraestrutura externa.
