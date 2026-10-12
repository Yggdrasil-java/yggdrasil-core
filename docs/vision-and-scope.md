# Visão e escopo

Yggdrasil centraliza decisões antifraude e regras complexas em uma biblioteca pública embarcada. Analistas devem conseguir alterar, experimentar e publicar conjuntos sem depender de uma nova implantação da aplicação consumidora.

## Capacidades acordadas

Autoria por frontend; condições estruturadas; consulta a histórico e relações; atributos reutilizáveis; agregações temporais; score, decisão e explicação; versionamento de conjuntos; shadow e aprovação; dry-run, replay, simulação e backfill.

Exemplo de referência: avaliar se uma entidade fez mais de cinco tentativas em um recurso nos últimos dez minutos e compartilha dispositivo com uma conta marcada como fraude. O resultado apresenta decisão, score e explicação.

## Escopo operacional

Uso interno em um mesmo sistema. Entidades e empresas podem ter interseções identificáveis e seus detalhes podem ser consultados internamente. A política concreta de autorização e de visibilidade ainda deve ser definida; uso interno não implica acesso irrestrito.

O produto é modular e a aplicação consumidora escolhe as dependências. É permitido usar somente o core, sem banco ou frontend, ou combinar os módulos. O histórico em produção exige grafo. A implementação atual entrega bibliotecas Java, testes e adaptadores em memória, conforme [modules.md](modules.md). O responsável escolheu Neo4j para a próxima etapa de persistência; o adaptador opcional aguarda validação no banco real. Frontend permanece adiado; administração começa pela API Java.
