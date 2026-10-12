# Experimentação e publicação

A unidade de comparação é o Forest completo. Regras, configurações de plugins, consultas e agregações fazem parte da revisão comparada. Como referenciar código executável de plugins e sua compatibilidade ainda será definido.

Cada avaliação fixa uma revisão imutável do Forest e de seus componentes compartilhados. Uma publicação não altera avaliações já iniciadas. O mecanismo e a garantia de propagação da publicação entre instâncias ainda serão definidos.

## Autoria e publicação

Rascunhos são editáveis e podem estar incompletos. A publicação valida a configuração e produz uma revisão imutável, com as referências compartilhadas fixadas por identidade e versão. Uma revisão publicada ainda não está ativa em produção: segue para shadow e aprovação antes da ativação como Live Forest.

Autoria/publicação e experimentação têm responsabilidades distintas. A política operacional de promoção pertence à composição com o módulo de experimentação; o core isolado não exige esse módulo para avaliar uma revisão válida fornecida pelo host.

## Live e Shadow

Live Forest decide em produção. Shadow Forest acompanha os mesmos eventos sem executar ações de produção. Quando altera cálculos de atributos, mantém estado próprio para evitar interferência com Live.

A comparação deve registrar decisões, scores, disponibilidade de dados, latência e divergências. Critérios de qualidade antifraude dependem de rótulos e resultados de negócio cuja origem ainda será definida.

## Aprovação

Promoção depende do OK do responsável ou de usuário de confiança autorizado. Roles, autenticação e critérios mínimos de aprovação ainda estão abertos. Registrar quem aprovou e qual revisão foi promovida é necessário para rastreabilidade.

## Autoria concorrente e idempotência

O frontend envia a versão que leu. Alterações com versão desatualizada devem ser rejeitadas, evitando perda de mudanças. Isso não exige um bloqueio permanente para um único cliente.

Operações repetidas devem preservar um único efeito. Contratos de chave de idempotência e resultados de repetição estão pendentes.

## Implementação local

[AuthoringService](../authoring/README.md) implementa rascunho, versão esperada,
publicação validada e repetição idempotente. Publicação incrementa a versão
do rascunho; o próximo editor deve ler esse novo valor.
[ExperimentationService](../experimentation/README.md) implementa comparação,
aprovação identificada, promoção CAS e rollback para revisão anteriormente Live.
Versões desatualizadas e operações conflitantes são rejeitadas.
O host escolhe a revisão Live inicial já autorizada antes do bootstrap.

A comparação usa os mesmos atributos imutáveis nas duas revisões. Resolução
por configurações distintas de plugins continua sendo orquestração explícita
do host, com namespaces Live/Shadow separados. Rótulos antifraude, critérios
mínimos e ativação distribuída continuam pendentes.

## Pendências distribuídas

Ativação entre instâncias; consistência de revisão; política de rollback; isolamento de recursos de shadow; quantidade de candidatas; estado inicial e descarte de experimentos.
