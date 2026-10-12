# Administração Java

Artefato `dev.yggdrasil:yggdrasil-administration`, módulo JPMS `dev.yggdrasil.administration`.

## Organização

- `domain/`: AdminAction, AccessGrant e AdministrationAuthorization.
- `application/`: AdministrationService.
- `infrastructure/`: StaticAccessPolicy, adaptador de referência.
- `src/test/java`: contratos observáveis e casos de conflito/isolamento.

## Comportamento

Gerir rascunhos, publicação, experimentos, aprovação, promoção e rollback. Toda entrada verifica autorização por ator, ação e Forest. Strings de ator devem vir de autenticação confiável do host; a API não autentica usuários. Permissões ausentes são negadas.

## Verificação

Execute na raiz:

```sh
./mvnw -pl administration -am test
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
