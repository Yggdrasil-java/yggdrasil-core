# Autoria

Artefato `dev.yggdrasil:yggdrasil-authoring`, módulo JPMS `dev.yggdrasil.authoring`.

## Organização

- `domain/`: ForestDefinition, ForestDraft e ForestRepository.
- `application/`: AuthoringService.
- `infrastructure/`: InMemoryForestRepository, adaptador de referência.
- `src/test/java`: contratos observáveis e casos de conflito/isolamento.

## Comportamento

Criar rascunhos incompletos, editar com versão esperada e publicar revisões válidas. A publicação incrementa a versão do rascunho, fixa snapshots de componentes e não ativa produção. UUIDs de operação reconhecem repetições e rejeitam conflitos.

Edição substitui a definição do rascunho. O Forest pode estar incompleto;
Trees/Leaves já fornecidas usam os tipos validados do core. A API atual não
representa uma Leaf parcialmente preenchida de um editor visual.

## Verificação

Execute na raiz:

```sh
./mvnw -pl authoring -am test
./mvnw verify
python3 scripts/verify_architecture.py
python3 scripts/verify_modular_consumer.py
```

O primeiro comando inclui os módulos necessários. Leia o
[consumidor modular](../examples/modular-consumer/README.md) para composição e
[modules.md](../docs/modules.md) para contratos e dependências.

## Limites

Adaptadores locais não oferecem durabilidade ou coordenação entre processos.
Banco de grafo de produção, frontend e autenticação não fazem parte desta
entrega. A API permanece candidata, sujeita à revisão antes da versão estável.
