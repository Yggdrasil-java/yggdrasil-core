# Histórico e identidade

A aplicação armazena transações e relações entre usuários, empresas e outras entidades. O ponto inicial da avaliação é configurável e identificado por uma identidade global fornecida na integração/configuração do motor.

Exemplos de consultas: quantas vezes uma entidade usou um recurso nos últimos dez minutos; se realizou uma atividade nos últimos meses; se duas entidades compartilham dispositivo; se um usuário interagiu com uma empresa.

## Relações e interseções

O grafo representa entidades e suas relações. Uso interno permite investigar interseções e seus detalhes, conforme política de acesso a definir. Não se presume uma plataforma de acesso externo entre empresas.

## Semântica temporal implementada

`HistoryEvent` distingue ocorrência e ingestão com `Instant`. Janelas são
semiabertas e `knownAt` limita ingestões visíveis. Relações possuem validade e
tempo de registro; consultas respeitam ambos. Duplicatas iguais não acrescentam
efeitos; conflitos no mesmo UUID são rejeitados, inclusive fora da janela
consultada. Correções versionadas e retenção de produção continuam pendentes.

`includeCurrent` é uma opção explícita por consulta. Eventos repetidos não podem inflar contagens por simples repetição da operação.

O evento atual pode ocorrer exatamente no fim da janela; outros eventos nesse
instante ficam excluídos. Contagens, vizinhos e interseções estão em
[history/README.md](../history/README.md). O adaptador atual é em memória.

## Armazenamento

Banco de grafo é requisito deste módulo, cuja adoção é opcional para a aplicação consumidora. O core isolado não armazena histórico e recebe seus dados do host. A distribuição de eventos, relações e agregados entre o grafo e outros armazenamentos ainda está aberta. Contagens e buscas por janela devem ser medidas; benefício de desempenho não é assumido apenas pelo modelo de banco.
