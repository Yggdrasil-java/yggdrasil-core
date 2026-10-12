# Yggdrasil-backend

Backend de motor de regras, com API REST, avaliação de expressões SpEL, persistência em MongoDB, mensageria Kafka, agendamento e observabilidade.

## Sobre este documento

Consolidação dos arquivos textuais do projeto contido em `Codes.rar`. Inclui código-fonte, testes, configurações e documentação original. O conteúdo foi preservado, normalizando apenas as quebras de linha. Metadados Git, configurações da IDE e artefatos de compilação foram excluídos.

**Arquivos incluídos:** 172.

## Índice

- [.gitignore](#arquivo-001)
- [Dockerfile](#arquivo-002)
- [docker-compose.yml](#arquivo-003)
- [documents/ENV.md](#arquivo-004)
- [documents/GUIDES.md](#arquivo-005)
- [documents/SAD.md](#arquivo-006)
- [documents/TROUBLESHOOTING.md](#arquivo-007)
- [documents/decisions/0001-spel-engine.md](#arquivo-008)
- [documents/decisions/0002-mongodb.md](#arquivo-009)
- [documents/decisions/0003-recursive-chaining.md](#arquivo-010)
- [documents/decisions/0004-aop-utilities.md](#arquivo-011)
- [mise.toml](#arquivo-012)
- [observability/grafana/provisioning/datasources/datasources.yml](#arquivo-013)
- [observability/loki.yml](#arquivo-014)
- [observability/mimir.yml](#arquivo-015)
- [observability/prometheus.yml](#arquivo-016)
- [observability/tempo.yml](#arquivo-017)
- [pom.xml](#arquivo-018)
- [src/main/java/com/matheusjfa/yggdrasil/YggdrasilApplication.java](#arquivo-019)
- [src/main/java/com/matheusjfa/yggdrasil/application/service/RuleForestService.java](#arquivo-020)
- [src/main/java/com/matheusjfa/yggdrasil/application/service/RuleVersionService.java](#arquivo-021)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/EvaluateContextUseCase.java](#arquivo-022)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/ReplayExecutionUseCase.java](#arquivo-023)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/execution/GetExecutionUseCase.java](#arquivo-024)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/execution/ListExecutionsUseCase.java](#arquivo-025)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/metrics/GetMetricsUseCase.java](#arquivo-026)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ConflictStrategy.java](#arquivo-027)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/CreateRuleUseCase.java](#arquivo-028)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/DeleteRuleUseCase.java](#arquivo-029)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRuleUseCase.java](#arquivo-030)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRulesUseCase.java](#arquivo-031)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetForestUseCase.java](#arquivo-032)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleUseCase.java](#arquivo-033)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleVersionUseCase.java](#arquivo-034)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ImportRulesUseCase.java](#arquivo-035)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ListRuleVersionsUseCase.java](#arquivo-036)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ListRulesUseCase.java](#arquivo-037)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/RestoreRuleVersionUseCase.java](#arquivo-038)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/RulePackage.java](#arquivo-039)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/TestRuleUseCase.java](#arquivo-040)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleStatusUseCase.java](#arquivo-041)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleUseCase.java](#arquivo-042)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/CreateScheduleUseCase.java](#arquivo-043)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/DeleteScheduleUseCase.java](#arquivo-044)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/ListSchedulesUseCase.java](#arquivo-045)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/ToggleScheduleStatusUseCase.java](#arquivo-046)
- [src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/UpdateScheduleUseCase.java](#arquivo-047)
- [src/main/java/com/matheusjfa/yggdrasil/domain/evaluation/EvaluationResult.java](#arquivo-048)
- [src/main/java/com/matheusjfa/yggdrasil/domain/evaluation/RuleContext.java](#arquivo-049)
- [src/main/java/com/matheusjfa/yggdrasil/domain/evaluation/RuleEvaluator.java](#arquivo-050)
- [src/main/java/com/matheusjfa/yggdrasil/domain/execution/ChainStep.java](#arquivo-051)
- [src/main/java/com/matheusjfa/yggdrasil/domain/execution/ExecutionId.java](#arquivo-052)
- [src/main/java/com/matheusjfa/yggdrasil/domain/execution/ExecutionOrigin.java](#arquivo-053)
- [src/main/java/com/matheusjfa/yggdrasil/domain/execution/FiredRule.java](#arquivo-054)
- [src/main/java/com/matheusjfa/yggdrasil/domain/execution/RuleExecution.java](#arquivo-055)
- [src/main/java/com/matheusjfa/yggdrasil/domain/execution/RuleExecutionRepository.java](#arquivo-056)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/ChainConfiguration.java](#arquivo-057)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/Rule.java](#arquivo-058)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleAction.java](#arquivo-059)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleEdge.java](#arquivo-060)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleException.java](#arquivo-061)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleId.java](#arquivo-062)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleNode.java](#arquivo-063)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleRepository.java](#arquivo-064)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleStatus.java](#arquivo-065)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ComparisonOperator.java](#arquivo-066)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ConditionGroup.java](#arquivo-067)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ConditionLeaf.java](#arquivo-068)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ConditionNode.java](#arquivo-069)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/LogicalOperator.java](#arquivo-070)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/SpelCondition.java](#arquivo-071)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/version/ChangeType.java](#arquivo-072)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/version/RuleVersion.java](#arquivo-073)
- [src/main/java/com/matheusjfa/yggdrasil/domain/rule/version/RuleVersionRepository.java](#arquivo-074)
- [src/main/java/com/matheusjfa/yggdrasil/domain/schedule/Schedule.java](#arquivo-075)
- [src/main/java/com/matheusjfa/yggdrasil/domain/schedule/ScheduleException.java](#arquivo-076)
- [src/main/java/com/matheusjfa/yggdrasil/domain/schedule/ScheduleId.java](#arquivo-077)
- [src/main/java/com/matheusjfa/yggdrasil/domain/schedule/ScheduleRepository.java](#arquivo-078)
- [src/main/java/com/matheusjfa/yggdrasil/domain/shared/AggregateRoot.java](#arquivo-079)
- [src/main/java/com/matheusjfa/yggdrasil/domain/shared/DomainEvent.java](#arquivo-080)
- [src/main/java/com/matheusjfa/yggdrasil/domain/shared/Entity.java](#arquivo-081)
- [src/main/java/com/matheusjfa/yggdrasil/domain/shared/Identifier.java](#arquivo-082)
- [src/main/java/com/matheusjfa/yggdrasil/domain/shared/ValueObject.java](#arquivo-083)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/annotation/LogExecutionTime.java](#arquivo-084)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/annotation/ValidateContext.java](#arquivo-085)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/aspect/LoggingAspect.java](#arquivo-086)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/aspect/ValidationAspect.java](#arquivo-087)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/constant/AppConstants.java](#arquivo-088)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/filter/UserContextFilter.java](#arquivo-089)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/CacheConfiguration.java](#arquivo-090)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/KafkaConfiguration.java](#arquivo-091)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/PersistenceConfiguration.java](#arquivo-092)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/SchedulingConfiguration.java](#arquivo-093)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/SecurityConfiguration.java](#arquivo-094)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/YggdrasilProperties.java](#arquivo-095)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/ExpressionEvaluator.java](#arquivo-096)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelGenerator.java](#arquivo-097)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelRuleEvaluator.java](#arquivo-098)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/EvaluationRequestConsumer.java](#arquivo-099)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/EvaluationResponseProducer.java](#arquivo-100)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/dto/EvaluateMessage.java](#arquivo-101)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/dto/EvaluationResultMessage.java](#arquivo-102)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/config/IndexInitializer.java](#arquivo-103)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/MongoRuleExecutionRepository.java](#arquivo-104)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionDocument.java](#arquivo-105)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionDocumentMapper.java](#arquivo-106)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionRepositoryAdapter.java](#arquivo-107)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/MongoRuleRepository.java](#arquivo-108)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleDocument.java](#arquivo-109)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleDocumentMapper.java](#arquivo-110)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleRepositoryAdapter.java](#arquivo-111)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/MongoRuleVersionRepository.java](#arquivo-112)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/RuleVersionDocument.java](#arquivo-113)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/RuleVersionDocumentMapper.java](#arquivo-114)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/RuleVersionRepositoryAdapter.java](#arquivo-115)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/MongoScheduleRepository.java](#arquivo-116)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleDocument.java](#arquivo-117)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleDocumentMapper.java](#arquivo-118)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleRepositoryAdapter.java](#arquivo-119)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/EvaluationController.java](#arquivo-120)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/ExecutionController.java](#arquivo-121)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/MetricsController.java](#arquivo-122)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleController.java](#arquivo-123)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleImportExportController.java](#arquivo-124)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/ScheduleController.java](#arquivo-125)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/advice/GlobalExceptionHandler.java](#arquivo-126)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/CreateRuleRequest.java](#arquivo-127)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/CreateScheduleRequest.java](#arquivo-128)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/EvaluateRequest.java](#arquivo-129)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/TestRuleRequest.java](#arquivo-130)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateRuleRequest.java](#arquivo-131)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateScheduleRequest.java](#arquivo-132)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateScheduleStatusRequest.java](#arquivo-133)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateStatusRequest.java](#arquivo-134)
- [src/main/java/com/matheusjfa/yggdrasil/infrastructure/scheduler/ScheduleRunner.java](#arquivo-135)
- [src/main/resources/application.yml](#arquivo-136)
- [src/main/resources/logback-spring.xml](#arquivo-137)
- [src/test/java/com/matheusjfa/yggdrasil/application/service/RuleForestServiceTest.java](#arquivo-138)
- [src/test/java/com/matheusjfa/yggdrasil/application/service/RuleVersionServiceTest.java](#arquivo-139)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/EvaluateContextUseCaseTest.java](#arquivo-140)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/ReplayExecutionUseCaseTest.java](#arquivo-141)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/metrics/GetMetricsUseCaseTest.java](#arquivo-142)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/CreateRuleUseCaseTest.java](#arquivo-143)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRuleUseCaseTest.java](#arquivo-144)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRulesUseCaseTest.java](#arquivo-145)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetForestUseCaseTest.java](#arquivo-146)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleUseCaseTest.java](#arquivo-147)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleVersionUseCaseTest.java](#arquivo-148)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ImportRulesUseCaseTest.java](#arquivo-149)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ListRuleVersionsUseCaseTest.java](#arquivo-150)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/RestoreRuleVersionUseCaseTest.java](#arquivo-151)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/TestRuleUseCaseTest.java](#arquivo-152)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleStatusUseCaseTest.java](#arquivo-153)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleUseCaseTest.java](#arquivo-154)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/schedule/CreateScheduleUseCaseTest.java](#arquivo-155)
- [src/test/java/com/matheusjfa/yggdrasil/application/usecase/schedule/ToggleScheduleStatusUseCaseTest.java](#arquivo-156)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelGeneratorTest.java](#arquivo-157)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionDocumentMapperTest.java](#arquivo-158)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleDocumentMapperTest.java](#arquivo-159)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleDocumentMapperTest.java](#arquivo-160)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/EvaluationControllerTest.java](#arquivo-161)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/ExecutionControllerTest.java](#arquivo-162)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/MetricsControllerTest.java](#arquivo-163)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleControllerForestTest.java](#arquivo-164)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleControllerVersionTest.java](#arquivo-165)
- [src/test/java/com/matheusjfa/yggdrasil/infrastructure/scheduler/ScheduleRunnerTest.java](#arquivo-166)
- [src/test/java/com/matheusjfa/yggdrasil/integration/MetricsCacheIntegrationTest.java](#arquivo-167)
- [src/test/java/com/matheusjfa/yggdrasil/integration/RuleEvaluationIntegrationTest.java](#arquivo-168)
- [src/test/java/com/matheusjfa/yggdrasil/integration/RuleForestCacheIntegrationTest.java](#arquivo-169)
- [src/test/resources/application-test.properties](#arquivo-170)
- [src/test/resources/docker-java.properties](#arquivo-171)
- [src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker](#arquivo-172)

## Conteúdo dos arquivos

<a id="arquivo-001"></a>

### `.gitignore`

```text
/target
```

<a id="arquivo-002"></a>

### `Dockerfile`

```dockerfile
# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -q
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine AS layers
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=layers /app/dependencies/ ./
COPY --from=layers /app/spring-boot-loader/ ./
COPY --from=layers /app/snapshot-dependencies/ ./
COPY --from=layers /app/application/ ./
ENTRYPOINT ["java", \
  "-XX:MaxRAMPercentage=75.0", \
  "org.springframework.boot.loader.launch.JarLauncher"]
```

<a id="arquivo-003"></a>

### `docker-compose.yml`

```yaml
services:
  mongodb:
    image: mongo:7
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db
    healthcheck:
      test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
      interval: 10s
      timeout: 5s
      retries: 5

  zookeeper:
    image: confluentinc/cp-zookeeper:7.6.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:7.6.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "29092:29092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,EXTERNAL://0.0.0.0:29092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092,EXTERNAL://localhost:29092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: "true"
    healthcheck:
      test: ["CMD", "kafka-broker-api-versions", "--bootstrap-server", "localhost:9092"]
      interval: 15s
      timeout: 10s
      retries: 10

  loki:
    image: grafana/loki:3.0.0
    ports:
      - "3100:3100"
    volumes:
      - ./observability/loki.yml:/etc/loki/local-config.yaml
      - loki_data:/loki
    command: -config.file=/etc/loki/local-config.yaml
    healthcheck:
      test: ["CMD-SHELL", "wget -q --spider http://localhost:3100/ready || exit 1"]
      interval: 10s
      timeout: 5s
      retries: 10

  tempo:
    image: grafana/tempo:2.4.0
    ports:
      - "3200:3200"
      - "4317:4317"
      - "4318:4318"
    volumes:
      - ./observability/tempo.yml:/etc/tempo.yaml
      - tempo_data:/var/tempo
    command: -config.file=/etc/tempo.yaml
    healthcheck:
      test: ["CMD-SHELL", "wget -q --spider http://localhost:3200/ready || exit 1"]
      interval: 10s
      timeout: 5s
      retries: 10

  mimir:
    image: grafana/mimir:2.12.0
    ports:
      - "9009:9009"
    volumes:
      - ./observability/mimir.yml:/etc/mimir.yaml
      - mimir_data:/data
    command: -config.file=/etc/mimir.yaml
    healthcheck:
      test: ["CMD-SHELL", "wget -q --spider http://localhost:9009/ready || exit 1"]
      interval: 10s
      timeout: 5s
      retries: 10

  prometheus:
    image: prom/prometheus:v2.51.0
    ports:
      - "9090:9090"
    volumes:
      - ./observability/prometheus.yml:/etc/prometheus/prometheus.yml
    command:
      - "--config.file=/etc/prometheus/prometheus.yml"
      - "--storage.tsdb.path=/prometheus"
    depends_on:
      mimir:
        condition: service_healthy

  grafana:
    image: grafana/grafana:10.4.0
    ports:
      - "3000:3000"
    environment:
      GF_AUTH_ANONYMOUS_ENABLED: "true"
      GF_AUTH_ANONYMOUS_ORG_ROLE: Admin
    volumes:
      - ./observability/grafana/provisioning:/etc/grafana/provisioning
      - grafana_data:/var/lib/grafana
    depends_on:
      loki:
        condition: service_healthy
      tempo:
        condition: service_healthy
      mimir:
        condition: service_healthy

  yggdrasil-backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      MONGODB_URI: mongodb://mongodb:27017/yggdrasil
      KAFKA_BOOTSTRAP_SERVERS: kafka:9092
      TEMPO_ENDPOINT: http://tempo:4317
      LOKI_URL: http://loki:3100
    depends_on:
      mongodb:
        condition: service_healthy
      kafka:
        condition: service_healthy
      loki:
        condition: service_healthy
      tempo:
        condition: service_healthy

volumes:
  mongo_data:
  loki_data:
  tempo_data:
  mimir_data:
  grafana_data:
```

<a id="arquivo-004"></a>

### `documents/ENV.md`

````markdown
# Variáveis de Ambiente e Setup — Yggdrasil Backend

Para visão geral da arquitetura, veja [SAD.md](SAD.md).
Para guias de desenvolvimento, veja [GUIDES.md](GUIDES.md).
Para solução de problemas, veja [TROUBLESHOOTING.md](TROUBLESHOOTING.md).

---

## Variáveis Obrigatórias

Essas variáveis não têm valor padrão. A aplicação falha no startup se não estiverem definidas em produção.

| Variável | Descrição | Exemplo |
|---|---|---|
| `MONGODB_URI` | URI de conexão com o MongoDB | `mongodb://localhost:27017/yggdrasil` |
| `KAFKA_BOOTSTRAP_SERVERS` | Endereço do broker Kafka | `localhost:9092` |

> Em desenvolvimento local, o `docker-compose.yml` na raiz do repositório já sobe MongoDB e Kafka nos valores padrão acima. Não é necessário exportar essas variáveis.

---

## Variáveis Opcionais com Valores Padrão

Definidas em `yggdrasil-backend/src/main/resources/application.yml`.

| Variável / Propriedade | Padrão | Descrição |
|---|---|---|
| `MONGODB_URI` | `mongodb://localhost:27017/yggdrasil` | URI MongoDB (padrão local) |
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Broker Kafka (padrão local) |
| `yggdrasil.cache.rules-ttl-seconds` | `60` | TTL do cache de regras ativas (segundos) |
| `yggdrasil.kafka.topics.evaluate-request` | `yggdrasil.evaluate.request` | Tópico de entrada para avaliação async |
| `yggdrasil.kafka.topics.evaluate-response` | `yggdrasil.evaluate.response` | Tópico de saída do resultado async |
| Porta da aplicação | `8080` | Padrão Spring Boot (sobrepõe com `--server.port=XXXX`) |

---

## Setup Local

### Pré-requisitos

- [mise](https://mise.jdx.dev/) — gerenciador de toolchain (Java 21 + Maven 3.9)
- Docker + Docker Compose

### Passos

**1. Subir infraestrutura (MongoDB + Kafka):**

```bash
# Na raiz do repositório Yggdrasil
docker compose up -d
```

**2. Rodar a aplicação:**

```bash
cd yggdrasil-backend
mise exec -- mvn spring-boot:run
```

**3. Verificar que está rodando:**

```bash
curl http://localhost:8080/actuator/health
# Esperado: {"status":"UP"}
```

A documentação da API está disponível em:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

### Por que `mise exec --`?

O projeto usa [mise](https://mise.jdx.dev/) para fixar as versões de Java e Maven em `mise.toml`. Sem o prefixo `mise exec --`, o shell usa a versão do sistema, que pode ser diferente. Se você tiver o mise configurado globalmente (`eval "$(mise activate zsh)"`), pode omitir o prefixo.

---

## Rodando Testes

### Testes unitários

```bash
cd yggdrasil-backend
mise exec -- mvn test
```

Executa apenas testes sem sufixo de integração. Não requer Docker.

### Testes de integração (Testcontainers)

```bash
cd yggdrasil-backend
mise exec -- mvn verify
```

Inclui testes de integração em `src/test/java/.../integration/`. O Testcontainers sobe um MongoDB real via Docker automaticamente. O Docker daemon deve estar rodando.

### Rodando um teste específico

```bash
cd yggdrasil-backend
mise exec -- mvn test -Dtest="RuleDocumentMapperTest" -pl .
```

---

## Limpando o Build

Se testes falham com erros estranhos de compilação (ex.: `BeanDefinitionOverrideException`, `cannot find symbol` em classes geradas), limpe os artefatos compilados:

```bash
cd yggdrasil-backend
mise exec -- mvn clean
```

Isso é necessário quando arquivos `.class` ficam obsoletos após mudanças de assinatura. Veja mais em [TROUBLESHOOTING.md](TROUBLESHOOTING.md).
````

<a id="arquivo-005"></a>

### `documents/GUIDES.md`

````markdown
# Guias de Desenvolvimento — Yggdrasil Backend

Para setup e variáveis de ambiente, veja [ENV.md](ENV.md).
Para visão geral da arquitetura, veja [SAD.md](SAD.md).
Para solução de problemas, veja [TROUBLESHOOTING.md](TROUBLESHOOTING.md).

---

## 1. Como Criar uma Nova Regra via API

### Payload básico

```bash
curl -X POST http://localhost:8080/rules \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Aprovar adultos",
    "description": "Aprova contextos onde age > 18",
    "priority": 1,
    "condition": {
      "type": "leaf",
      "field": "age",
      "operator": "GREATER_THAN",
      "value": 18
    },
    "actions": [
      { "type": "APPROVE", "parameters": {} }
    ],
    "chains": []
  }'
```

Retorna a regra criada com `id` e `status: "ACTIVE"`.

### Condição composta (AND/OR)

Use `ConditionGroup` para combinar múltiplas condições:

```json
{
  "type": "group",
  "operator": "AND",
  "children": [
    { "type": "leaf", "field": "age", "operator": "GREATER_THAN", "value": 18 },
    { "type": "leaf", "field": "status", "operator": "EQUAL", "value": "ACTIVE" }
  ]
}
```

### Operadores disponíveis (`ComparisonOperator`)

| Operador | Tipo de dado | Exemplo |
|---|---|---|
| `EQUAL` / `NOT_EQUAL` | qualquer | `"field": "status", "operator": "EQUAL", "value": "ACTIVE"` |
| `GREATER_THAN` / `LESS_THAN` / `GREATER_THAN_OR_EQUAL` / `LESS_THAN_OR_EQUAL` | numérico / temporal | `"field": "age", "operator": "GREATER_THAN", "value": 18` |
| `CONTAINS` / `STARTS_WITH` / `ENDS_WITH` / `MATCHES` | string | `"field": "email", "operator": "ENDS_WITH", "value": "@empresa.com"` |
| `CONTAINS_IGNORE_CASE` | string | `"field": "name", "operator": "CONTAINS_IGNORE_CASE", "value": "silva"` |
| `IS_NULL` / `IS_NOT_NULL` | qualquer | `"field": "middleName", "operator": "IS_NULL"` (sem `value`) |
| `IN` / `NOT_IN` | lista | `"field": "role", "operator": "IN", "value": ["ADMIN", "MANAGER"]` |
| `IS_EMPTY` / `IS_NOT_EMPTY` | coleção / string | `"field": "tags", "operator": "IS_EMPTY"` (sem `value`) |
| `MEMBER_OF` | lista | `"field": "userId", "operator": "MEMBER_OF", "value": ["u1", "u2"]` |
| `CONTAINS_ALL` / `CONTAINS_ANY` | coleção | `"field": "permissions", "operator": "CONTAINS_ANY", "value": ["READ", "WRITE"]` |
| `IS_BETWEEN` | numérico | `"field": "score", "operator": "IS_BETWEEN", "value": [60, 100]` |
| `BEFORE` / `AFTER` | temporal (ISO-8601) | `"field": "createdAt", "operator": "BEFORE", "value": "2024-01-01T00:00:00Z"` |
| `LAST_MONTH` | temporal | `"field": "paymentDate", "operator": "LAST_MONTH"` (sem `value`) |
| `LAST_N_DAYS` | temporal | `"field": "loginAt", "operator": "LAST_N_DAYS", "value": 7` |

### Encadeamento de regras (`chains`)

Para disparar outra regra quando esta regra for ativada:

```json
{
  "chains": [
    { "targetRuleId": "<id-da-outra-regra>", "condition": "ALWAYS" }
  ]
}
```

### Testar uma regra individualmente

```bash
curl -X POST http://localhost:8080/rules/{id}/test \
  -H "Content-Type: application/json" \
  -d '{
    "context": { "age": 25, "status": "ACTIVE" }
  }'
```

Retorna `{ "fired": true, "actions": [...] }` sem persistir execução.

---

## 2. Como Adicionar um Novo Operador de Comparação

### Passo 1: Adicionar ao enum `ComparisonOperator`

Arquivo: `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ComparisonOperator.java`

```java
public enum ComparisonOperator {
    // ... operadores existentes ...
    MY_NEW_OPERATOR   // ← adicionar aqui
}
```

### Passo 2: Implementar a geração SpEL em `SpelGenerator`

Arquivo: `src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelGenerator.java`

O método `generateLeaf` usa um `switch` exaustivo em `ComparisonOperator`. Adicionar o novo case:

```java
case MY_NEW_OPERATOR -> field + ".someSpelExpression(" + value + ")";
```

O compilador vai exigir o novo case — o `switch` é exaustivo sobre o enum.

### Passo 3: Sincronizar no frontend

O frontend mantém uma lista de operadores em `yggdrasil-frontend/src/...` (consulte o README do frontend). Adicionar `MY_NEW_OPERATOR` na lista de operadores disponíveis para a interface de criação de regras.

### Passo 4: Escrever teste unitário

Arquivo: `src/test/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelGeneratorTest.java` (criar se não existir)

```java
@Test
void myNewOperator_generates_correct_spel() {
    var generator = new SpelGenerator();
    var leaf = new ConditionLeaf("field", ComparisonOperator.MY_NEW_OPERATOR, "someValue");

    var spel = generator.generate(leaf);

    assertThat(spel).isEqualTo("#field.someSpelExpression('someValue')");
}
```

Rodar: `mise exec -- mvn test -Dtest="SpelGeneratorTest"`

---

## 3. Como Escrever um Teste de Integração

Testes de integração ficam em `src/test/java/com/matheusjfa/yggdrasil/integration/`.

### Estrutura base

```java
package com.matheusjfa.yggdrasil.integration;

import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationRequestConsumer;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationResponseProducer;
import com.matheusjfa.yggdrasil.infrastructure.persistence.rule.MongoRuleRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@AutoConfigureMockMvc
@Testcontainers
class MeuFluxoIntegrationTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired MockMvc mockMvc;
    @Autowired MongoRuleRepository mongoRuleRepository;
    @Autowired CacheManager cacheManager;

    // Kafka não é necessário em testes de integração — mockar os beans
    @MockBean EvaluationRequestConsumer evaluationRequestConsumer;
    @MockBean EvaluationResponseProducer evaluationResponseProducer;

    @AfterEach
    void cleanup() {
        mongoRuleRepository.deleteAll();
        var cache = cacheManager.getCache("active-rules");
        if (cache != null) cache.clear();
    }
}
```

### Por que mockar o Kafka?

Os testes de integração excluem o `KafkaAutoConfiguration` e mocam os beans de consumer/producer para que a aplicação suba sem um broker Kafka real. O Testcontainers cuida apenas do MongoDB.

### Exemplo completo: criar regra e avaliar contexto

```java
@Test
void create_rule_then_evaluate_matching_context_returns_match() throws Exception {
    var objectMapper = new ObjectMapper();

    // 1. Criar a regra
    var createRequest = new CreateRuleRequest(
        "Aprovar adultos", "desc", 1,
        new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
        List.of(new RuleAction("APPROVE", Map.of())),
        null
    );

    mockMvc.perform(post("/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createRequest)))
        .andExpect(status().isCreated());

    // 2. Avaliar um contexto que deve disparar a regra
    mockMvc.perform(post("/evaluate")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                { "context": { "age": 25 } }
            """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firedRules").isNotEmpty());
}
```

---

## 4. Convenções de Código

### Mappers (MapStruct)

- **Interface** — quando o mapper não precisa de dependências Spring:
  ```java
  @Mapper(componentModel = "spring")
  public interface ScheduleDocumentMapper {
      ScheduleDocument toDocument(Schedule schedule);
      default Schedule toDomain(ScheduleDocument doc) { ... }
  }
  ```

- **Abstract class** — quando o mapper precisa de `@Autowired` (ex.: `ObjectMapper` para serialização JSON):
  ```java
  @Mapper(componentModel = "spring")
  public abstract class RuleDocumentMapper {
      @Autowired protected ObjectMapper objectMapper;
      public abstract RuleDocument toDocument(Rule rule);
      public Rule toDomain(RuleDocument doc) { ... }
  }
  ```

### Repositórios

Padrão adapter: o domain define a interface, a infrastructure implementa.

```
domain/rule/RuleRepository.java           ← interface (porta)
infrastructure/persistence/rule/
  MongoRuleRepository.java                ← Spring Data (extends MongoRepository)
  RuleRepositoryAdapter.java              ← implementa RuleRepository, injeta MongoRuleRepository
```

### Use Cases

Um arquivo por caso de uso, com record `Input` e `Output` estáticos:

```java
public class CreateRuleUseCase {
    public record Input(String name, ...) {}
    public record Output(String id, ...) {}

    public Output execute(Input input) { ... }
}
```
````

<a id="arquivo-006"></a>

### `documents/SAD.md`

````markdown
# Software Architecture Document — Yggdrasil Backend

## Visão Geral

O **Yggdrasil Backend** é um motor de regras escalável e extensível que avalia contextos de dados contra regras configuráveis. Cada regra define condições (ex.: `age > 18`, `status == "ACTIVE"`) e ações a executar quando as condições são atendidas. O sistema suporta encadeamento de regras, avaliação síncrona via REST, avaliação assíncrona via Kafka e agendamentos baseados em cron.

**Problema que resolve:** centralizar a lógica de negócio baseada em regras, evitando que condicionais if/else se espalhem por múltiplos serviços.

Para setup e variáveis de ambiente, veja [ENV.md](ENV.md).
Para guias de desenvolvimento, veja [GUIDES.md](GUIDES.md).
Para solução de problemas comuns, veja [TROUBLESHOOTING.md](TROUBLESHOOTING.md).

---

## Diagrama de Componentes

```
┌─────────────────────────────────────────────────────────────────────┐
│                        Infrastructure                               │
│                                                                     │
│  ┌──────────────┐  ┌──────────────────┐  ┌──────────────────────┐  │
│  │     REST     │  │    Messaging     │  │      Scheduler       │  │
│  │ (Controllers)│  │ (Kafka Consumer/ │  │  (ScheduleRunner /   │  │
│  │              │  │   Producer)      │  │   @Scheduled)        │  │
│  └──────┬───────┘  └───────┬──────────┘  └──────────┬───────────┘  │
│         │                  │                         │              │
│  ┌──────▼──────────────────▼─────────────────────────▼───────────┐  │
│  │                    Application (Use Cases)                    │  │
│  │  EvaluateContextUseCase  CreateRuleUseCase  GetRuleUseCase    │  │
│  │  TestRuleUseCase         ListRulesUseCase   DeleteRuleUseCase │  │
│  │  GetExecutionUseCase     ListExecutionsUseCase  GetMetrics    │  │
│  │  CreateScheduleUseCase   TriggerScheduleUseCase  ...          │  │
│  └──────┬───────────────────────────────────────────────────────┘  │
│         │                                                           │
│  ┌──────▼───────────────────────────────────────────────────────┐   │
│  │                          Domain                              │   │
│  │  Rule  RuleExecution  Schedule  ConditionNode  RuleAction    │   │
│  │  RuleRepository  RuleExecutionRepository  ScheduleRepository │   │
│  │  RuleEvaluator (interface)                                   │   │
│  └──────┬───────────────────────────────────────────────────────┘   │
│         │                                                           │
│  ┌──────▼───────────────────────────────────────────────────────┐   │
│  │               Infrastructure (implementações)                │   │
│  │  SpelRuleEvaluator + SpelGenerator  (engine/)                │   │
│  │  RuleRepositoryAdapter + MongoRuleRepository  (persistence/) │   │
│  │  RuleDocumentMapper / ScheduleDocumentMapper (MapStruct)     │   │
│  │  IndexInitializer  PersistenceConfiguration (@Mongo Auditing)│   │
│  │  CacheConfig (Caffeine)  KafkaConfiguration  AOP Aspects     │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
         │                    │                    │
    ┌────▼────┐         ┌─────▼──────┐      ┌─────▼──────┐
    │ MongoDB │         │   Kafka    │      │ HTTP externo│
    │(regras, │         │(avaliação  │      │(contexto de │
    │execuções│         │ assíncrona)│      │ schedules)  │
    │schedules│         └────────────┘      └─────────────┘
    └─────────┘
```

---

## Fluxo de Execução Síncrona

```
Cliente HTTP
     │
     │ POST /evaluate  { context: { age: 25, ... } }
     ▼
EvaluationController
     │
     │ execute(Input)
     ▼
EvaluateContextUseCase
     │  1. Carrega regras ativas (RuleRepository → cache Caffeine "active-rules")
     │  2. Para cada regra:
     │     a. SpelGenerator traduz ConditionNode → expressão SpEL
     │     b. SpelRuleEvaluator avalia via SimpleEvaluationContext (read-only)
     │     c. Se disparou: adiciona à lista firedRules, processa chains recursivamente
     │  3. RuleExecution.record(...) cria a entidade de execução
     │  4. RuleExecutionRepository.save(...) persiste no MongoDB
     ▼
EvaluationResponse { firedRules: [...], executionId: "..." }
```

O `SimpleEvaluationContext` garante que as expressões SpEL não possam chamar métodos Java arbitrários — apenas leitura de variáveis do contexto.

---

## Fluxo de Execução Assíncrona (Kafka)

```
Produtor externo
     │
     │ → tópico: yggdrasil.evaluate.request
     ▼
EvaluationRequestConsumer
     │ deserializa EvaluationRequestMessage
     ▼
EvaluateContextUseCase.execute(...)   ← mesmo fluxo que síncrono
     │
     ▼
EvaluationResponseProducer
     │ → tópico: yggdrasil.evaluate.response
     ▼
Consumidor externo recebe resultado
```

---

## Fluxo de Agendamento

```
@Scheduled (a cada minuto)
     │
     ▼
ScheduleRunner
     │ 1. ScheduleRepository.findEnabledDue(now) → schedules com nextFireAt ≤ agora
     │ 2. Para cada schedule:
     ▼
TriggerScheduleUseCase
     │ 1. GET <contextUrl> → busca contexto de dados via HTTP
     │ 2. EvaluateContextUseCase.execute(contexto, origin=SCHEDULED)
     │ 3. Schedule.updateNextFireAt(cronExpression) → recalcula próxima execução
     │ 4. ScheduleRepository.save(schedule)
     ▼
Execução registrada com origin=SCHEDULED
```

---

## Decisões Arquiteturais

| ID | Decisão | Resumo |
|---|---|---|
| [0001](decisions/0001-spel-engine.md) | Spring SpEL como motor | SpEL fornece avaliação segura via `SimpleEvaluationContext`, sem risco de execução de código arbitrário. Alternativas (Drools, Groovy) foram descartadas por complexidade operacional. |
| [0002](decisions/0002-mongodb.md) | MongoDB para persistência | Schema flexível para `ConditionNode` (árvore de condições polimórfica serializada como JSON). Regras evoluem sem migrações. |
| [0003](decisions/0003-recursive-chaining.md) | Rule Chaining Recursivo | Regras podem disparar outras regras via `ChainConfiguration`. Ciclos são detectados com `Set<String> visited` durante a avaliação recursiva. |
| [0004](decisions/0004-aop-utilities.md) | AOP para Cross-cutting Concerns | `@LogExecutionTime` e `@ValidateContext` implementados via Spring AOP para não poluir a lógica de negócio nos use cases. |

---

## Integrações Externas

| Sistema | Uso | Configuração |
|---|---|---|
| **MongoDB** | Persistência de `Rule`, `RuleExecution`, `Schedule` | `MONGODB_URI` |
| **Kafka** | Avaliação assíncrona (request/response) | `KAFKA_BOOTSTRAP_SERVERS`, tópicos configuráveis |
| **HTTP externo** | Busca de contexto para schedules | URL configurada por schedule (`contextUrl`) |
| **Caffeine** | Cache de regras ativas (`active-rules`) | TTL configurável via `yggdrasil.cache.rules-ttl-seconds` |

---

## Componentes de Infraestrutura

### Persistência

- **`RuleDocumentMapper`** — abstract class MapStruct; injeta `ObjectMapper` para serializar/deserializar `ConditionNode` (árvore polimórfica) como JSON no campo `conditionJson`
- **`ScheduleDocumentMapper`** / **`RuleExecutionDocumentMapper`** — interfaces MapStruct; sem dependências Spring, instanciáveis diretamente em testes unitários
- **`IndexInitializer`** — `ApplicationRunner` que cria índices MongoDB programaticamente no startup (motivo: `auto-index-creation` está desabilitado em produção)
- **`PersistenceConfiguration`** — `@EnableMongoAuditing` ativa `@CreatedDate` e `@LastModifiedDate` nos documents

### Motor de Regras

- **`SpelGenerator`** — traduz `ConditionNode` (árvore de `ConditionLeaf` / `ConditionGroup` / `SpelCondition`) para string SpEL
- **`SpelRuleEvaluator`** — avalia a expressão SpEL contra o contexto usando `SimpleEvaluationContext`

### AOP

- **`@LogExecutionTime`** — loga tempo de execução de métodos anotados
- **`@ValidateContext`** — valida que o mapa de contexto não está vazio antes da execução

---

## Atributos de Qualidade

| Atributo | Meta | Implementação |
|---|---|---|
| Performance | Avaliação < 100ms | Cache Caffeine para regras ativas; SpEL compilado internamente |
| Escalabilidade | Alta concorrência | Kafka para avaliação assíncrona desacoplada |
| Flexibilidade | Novos operadores sem recompilação | `SpelGenerator` com `switch` exaustivo em `ComparisonOperator` |
| Segurança | Sandbox de expressões | `SimpleEvaluationContext` — sem acesso a métodos Java arbitrários |
````

<a id="arquivo-007"></a>

### `documents/TROUBLESHOOTING.md`

````markdown
# Troubleshooting — Yggdrasil Backend

Para setup e variáveis de ambiente, veja [ENV.md](ENV.md).
Para guias de desenvolvimento, veja [GUIDES.md](GUIDES.md).
Para visão geral da arquitetura, veja [SAD.md](SAD.md).

---

## Erros de Build

### `cannot find symbol: class RuleDocumentMapperImpl` (ou `ScheduleDocumentMapperImpl`)

**Causa:** MapStruct não gerou as implementações porque a ordem dos `annotationProcessorPaths` no `pom.xml` está errada.

**Fix:** Lombok **deve** vir antes do MapStruct em `annotationProcessorPaths`:

```xml
<annotationProcessorPaths>
    <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </path>
    <path>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
    </path>
</annotationProcessorPaths>
```

Após corrigir: `mise exec -- mvn clean compile`.

---

### Testes falham com `cannot find symbol` ou `incompatible types` após mudança de assinatura

**Causa:** Arquivos `.class` compilados anteriormente não foram removidos. O Maven incremental não recompila o que parece inalterado.

**Fix:**

```bash
cd yggdrasil-backend
mise exec -- mvn clean
```

Isso apaga `target/` inteiro. Em seguida rode `mise exec -- mvn test` normalmente.

---

### `@Bean method must be in @Configuration class` em testes unitários

**Causa:** Ao usar `@ExtendWith(SpringExtension.class)`, métodos `@Bean` não funcionam diretamente na classe de teste. Precisam estar em uma classe `@TestConfiguration`.

**Fix:** Envolver os beans em uma `@TestConfiguration` estática interna:

```java
@ExtendWith(SpringExtension.class)
@Import({MinhaClasseImpl.class, MinhaTesteClass.Config.class})
class MinhaTesteClass {

    @TestConfiguration
    static class Config {
        @Bean
        ObjectMapper objectMapper() { return new ObjectMapper(); }
    }
}
```

---

## Erros de Startup

### `MongoSocketOpenException: Exception opening socket` ou `Connection refused` (porta 27017)

**Causa:** MongoDB não está rodando.

**Fix:**

```bash
# Na raiz do repositório
docker compose up -d
```

Verificar que o container subiu: `docker ps | grep mongo`

---

### `Connection refused` na porta 9092 (Kafka)

**Causa:** Kafka não está rodando.

**Fix:**

```bash
docker compose up -d
```

Verificar: `docker ps | grep kafka`

Se a aplicação subir mesmo assim (sem erros de regras ou schedules), é provável que os beans de consumer/producer estejam sendo mocked em algum profile. Em produção, o Kafka é obrigatório.

---

### `BeanDefinitionOverrideException: Invalid bean definition with name 'cacheManager'`

**Causa:** Arquivo `.class` stale de um `CacheConfig.java` que foi removido do código-fonte mas ainda existe compilado em `target/classes/`.

**Fix:**

```bash
cd yggdrasil-backend
mise exec -- mvn clean
mise exec -- mvn spring-boot:run
```

---

## Erros de Teste

### `Could not find a valid Docker environment` (Testcontainers)

**Causa:** O Docker daemon não está rodando quando os testes de integração tentam subir o container MongoDB.

**Fix:** Iniciar o Docker Desktop (ou `sudo systemctl start docker` no Linux) e rodar os testes novamente.

---

### `ExecutionOrigin.API` — `cannot find symbol`

**Causa:** O enum `ExecutionOrigin` não tem o valor `API`.

**Fix:** Usar os valores corretos:

| Errado | Correto |
|---|---|
| `ExecutionOrigin.API` | `ExecutionOrigin.SYNC` |
| `ExecutionOrigin.SCHEDULE` | `ExecutionOrigin.SCHEDULED` |

---

### `ComparisonOperator.EQUALS` — `cannot find symbol`

**Causa:** O enum `ComparisonOperator` não tem o valor `EQUALS`.

**Fix:** O valor correto é `ComparisonOperator.EQUAL` (sem o S).

---

## Comportamentos Esperados que Parecem Bugs

### `@Indexed` não cria os índices no MongoDB

**Por que acontece:** A propriedade `spring.data.mongodb.auto-index-creation` não está habilitada (desabilitada por padrão em produção para evitar impacto em coleções grandes).

**Como funciona:** Os índices são criados pelo `IndexInitializer` (`infrastructure/persistence/config/IndexInitializer.java`) toda vez que a aplicação inicia, via `MongoTemplate.indexOps(...).ensureIndex(...)`. O `ensureIndex` é idempotente — não recria o índice se ele já existir.

---

### `@LastModifiedDate` parece sobrescrever o `updatedAt` do domínio

**Por que acontece:** O Spring Data MongoDB sobrescreve o campo `updatedAt` do document ao persistir, mesmo que o domínio já tenha calculado um valor para `updatedAt`. A diferença é sub-milissegundo (o domínio seta `updatedAt = Instant.now()` instantes antes do `save`).

**Impacto:** Nenhum em produção. Os dois valores são praticamente idênticos. Em testes que comparam `updatedAt` com precisão de nanosegundos, use `isCloseTo` em vez de `isEqualTo`.

---

### O cache `active-rules` não reflete uma regra recém-criada

**Por que acontece:** O cache Caffeine tem TTL configurável (`yggdrasil.cache.rules-ttl-seconds`, padrão 60s). Regras criadas ou alteradas só aparecem nas avaliações após a expiração do cache.

**Em produção:** Comportamento esperado e aceitável. O TTL de 60s é um tradeoff conscientemente escolhido entre consistência e performance.

**Em testes:** O `RuleEvaluationIntegrationTest` limpa o cache no `@AfterEach`:

```java
var cache = cacheManager.getCache("active-rules");
if (cache != null) cache.clear();
```

Faça o mesmo nos seus testes de integração se precisar de consistência imediata.
````

<a id="arquivo-008"></a>

### `documents/decisions/0001-spel-engine.md`

```markdown
# ADR 0001: Spring SpEL como Motor de Regras

## Status

- Status: Aceito
- Data: 2026-05-20
- Decisores: Matheus JFA

## Contexto e Problema

O sistema precisa de uma forma flexível e poderosa para avaliar condições lógicas dinâmicas definidas pelo usuário. As condições podem envolver comparações de strings, números, datas e listas, além de acesso a métodos de objetos e variáveis de sistema (como o tempo atual).

## Opções Consideradas

1. **Implementação Manual de Parser/AST**: Criar um interpretador próprio para o JSON de condições.
   - Prós: Controle total, segurança máxima (sandboxing).
   - Contras: Alto custo de desenvolvimento e manutenção, difícil de suportar expressões complexas.
2. **Drools**: Motor de regras padrão de mercado para Java.
   - Prós: Extremamente robusto, otimizado para milhares de regras (algoritmo Rete).
   - Contras: Curva de aprendizado íngreme, pesado para casos de uso simples de avaliação de contexto único.
3. **Spring SpEL (Spring Expression Language)**: Motor de expressões nativo do framework Spring.
   - Prós: Poderoso, suporta chamadas de métodos, manipulação de coleções e variáveis. Integração nativa com o ecossistema existente.
   - Contras: Risco de segurança (necessário uso de `SimpleEvaluationContext` para limitar acesso a beans/reflexão).

## Decisão

Escolhemos o **Spring SpEL** devido ao equilíbrio entre poder de expressão e facilidade de implementação. Através do `SimpleEvaluationContext` configurado para `readOnlyDataBinding`, mitigamos riscos de segurança enquanto permitimos que os usuários definam condições altamente dinâmicas.

## Consequências

### Positivas

- Desenvolvimento rápido da lógica de avaliação.
- Suporte nativo a tipos Java (Dates, Collections).
- Facilidade de extensibilidade através de registro de funções e variáveis no contexto.

### Negativas / Riscos

- Necessidade de um `SpelGenerator` para traduzir o modelo visual (JSON) para a string SpEL.
- Requer cuidado rigoroso com a sanitização de strings para evitar injeção maliciosa, embora o contexto simplificado limite bastante o impacto.
```

<a id="arquivo-009"></a>

### `documents/decisions/0002-mongodb.md`

```markdown
# ADR 0002: MongoDB para Persistência

## Status

- Status: Aceito
- Data: 2026-05-20
- Decisores: Matheus JFA

## Contexto e Problema

O sistema lida com regras que possuem uma estrutura de condições aninhada (árvore) e execuções que recebem mapas de contexto arbitrários (JSON dinâmico). Um banco de dados relacional tradicional exigiria muitas tabelas ou o uso de colunas JSONB, dificultando consultas complexas e evolução do schema.

## Opções Consideradas

1. **PostgreSQL (Relacional + JSONB)**:
   - Prós: Consistência ACID forte, suporte maduro a JSON.
   - Contras: Schema rígido para as entidades principais (Rule, Execution), overhead de mapeamento relacional.
2. **MongoDB (Document-Oriented)**:
   - Prós: Schema-less nativo, mapeamento direto de objetos complexos (Condition Trees), alta performance de escrita (ideal para logs de execução).
   - Contras: Menos garantias transacionais complexas entre coleções (embora suporte transações multi-documento).

## Decisão

Escolhemos o **MongoDB** pela sua natureza orientada a documentos, que se encaixa perfeitamente na estrutura recursiva das regras e no dinamismo dos contextos de execução. A facilidade de persistir o rastro de correntes (Rule Chaining) como sub-documentos também foi um fator decisivo.

## Consequências

### Positivas

- Mapeamento direto entre o domínio Java e o armazenamento.
- Flexibilidade total para adicionar novos tipos de condições e ações sem migrações de banco.
- Desempenho otimizado para gravação de grandes volumes de rastros de execução.

### Negativas / Riscos

- Necessidade de gerenciar a consistência de dados manualmente em alguns cenários de deleção em cascata (ex: apagar regras usadas em chains).
```

<a id="arquivo-010"></a>

### `documents/decisions/0003-recursive-chaining.md`

```markdown
# ADR 0003: Rule Chaining Recursivo

## Status

- Status: Aceito
- Data: 2026-05-20
- Decisores: Matheus JFA

## Contexto e Problema

O motor original avaliava regras de forma isolada. Cenários complexos exigem que o resultado de uma regra dispare a avaliação de outra, potencialmente enriquecendo o contexto original com novos dados calculados.

## Opções Consideradas

1. **Orquestração via Eventos (Kafka)**: Cada regra dispara um evento que pode ser consumido por outra.
   - Prós: Desacoplamento total, escalabilidade horizontal fácil.
   - Contras: Difícil de garantir ordem de execução em correntes síncronas, complexidade de rastreamento de uma única "sessão" de avaliação.
2. **Encadeamento Recursivo em Memória**: O motor percorre as correntes definidas na regra de forma recursiva durante a mesma requisição.
   - Prós: Síncrono (resposta imediata), fácil de implementar enriquecimento de contexto, rastro de execução linear e claro.
   - Contras: Risco de estouro de pilha (stack overflow) e loops infinitos se não houver detecção de ciclos.

## Decisão

Escolhemos o **Encadeamento Recursivo em Memória** para as correntes de regras, visando simplicidade e rastreabilidade. Para mitigar os riscos, implementamos uma detecção de ciclos baseada em um `Set<String> visited` durante a recursão, interrompendo qualquer caminho que tente re-avaliar uma regra já disparada na mesma corrente.

## Consequências

### Positivas

- Permite lógica de negócio altamente modularizada.
- Enriquecimento de contexto dinâmico por salto.
- Rastro de execução (`chainTrace`) detalhado e fácil de auditar.

### Negativas / Riscos

- Limitação natural de profundidade pela pilha de chamadas (embora grafos de regras raramente excedam dezenas de níveis).
- Aumenta o tempo de resposta de uma única requisição de avaliação se a corrente for muito longa.
```

<a id="arquivo-011"></a>

### `documents/decisions/0004-aop-utilities.md`

```markdown
# ADR 0004: AOP para Cross-cutting Concerns

## Status

- Status: Aceito
- Data: 2026-05-20
- Decisores: Matheus JFA

## Contexto e Problema

A aplicação necessita de funcionalidades transversais como logging de tempo de execução e validação de parâmetros de entrada (contexto) em diversos pontos do sistema. Implementar isso manualmente em cada Use Case ou Service geraria duplicação de código e dificultaria a manutenção.

## Opções Consideradas

1. **Implementação Manual (Decorators/Wrappers)**: Envolver cada chamada em um bloco de código de monitoramento.
   - Prós: Explícito, sem "mágica".
   - Contras: Alto boilerplate, propenso a esquecimentos.
2. **Spring AOP (Aspect-Oriented Programming)**: Utilizar aspectos para interceptar chamadas de métodos anotados.
   - Prós: Limpo, reutilizável, desacoplado da lógica de negócio. Segue o padrão do ecossistema Spring.
   - Contras: Adiciona uma camada de abstração que pode dificultar o debugging se não for bem documentada.

## Decisão

Escolhemos o **Spring AOP** com anotações customizadas (`@LogExecutionTime`, `@ValidateContext`). Isso permite que os desenvolvedores declarem necessidades transversais de forma simples, mantendo os Use Cases focados exclusivamente na lógica de negócio (Clean Architecture).

## Consequências

### Positivas

- Redução drástica de código repetitivo.
- Centralização da lógica de monitoramento e validação.
- Facilidade de ativar/desativar comportamentos em toda a aplicação.

### Negativas / Riscos

- Requer que os beans sejam gerenciados pelo Spring para que os proxies funcionem.
- Leve overhead de performance devido à criação de proxies (negligenciável para o volume atual).
```

<a id="arquivo-012"></a>

### `mise.toml`

```toml
[tools]
java = "21"
maven = "3.9"
```

<a id="arquivo-013"></a>

### `observability/grafana/provisioning/datasources/datasources.yml`

```yaml
apiVersion: 1

datasources:
  - name: Loki
    type: loki
    uid: loki
    url: http://loki:3100
    access: proxy
    isDefault: false
    jsonData:
      derivedFields:
        - datasourceUid: tempo
          matcherRegex: "traceId=(\\w+)"
          name: TraceID
          url: "$${__value.raw}"

  - name: Tempo
    type: tempo
    uid: tempo
    url: http://tempo:3200
    access: proxy
    isDefault: false
    jsonData:
      tracesToLogs:
        datasourceUid: loki
        filterByTraceID: true
        filterBySpanID: false
        tags:
          - key: app
            value: yggdrasil

  - name: Mimir
    type: prometheus
    uid: mimir
    url: http://mimir:9009/prometheus
    access: proxy
    isDefault: true

  - name: Prometheus
    type: prometheus
    uid: prometheus
    url: http://prometheus:9090
    access: proxy
    isDefault: false
```

<a id="arquivo-014"></a>

### `observability/loki.yml`

```yaml
auth_enabled: false

server:
  http_listen_port: 3100
  grpc_listen_port: 9096

common:
  instance_addr: 127.0.0.1
  path_prefix: /loki
  storage:
    filesystem:
      chunks_directory: /loki/chunks
      rules_directory: /loki/rules
  replication_factor: 1
  ring:
    kvstore:
      store: inmemory

schema_config:
  configs:
    - from: 2020-10-24
      store: tsdb
      object_store: filesystem
      schema: v13
      index:
        prefix: index_
        period: 24h

ruler:
  alertmanager_url: http://localhost:9093
```

<a id="arquivo-015"></a>

### `observability/mimir.yml`

```yaml
target: all

server:
  http_listen_port: 9009

blocks_storage:
  backend: filesystem
  filesystem:
    dir: /data/mimir/blocks
  bucket_store:
    sync_dir: /data/mimir/tsdb-sync

compactor:
  data_dir: /data/mimir/compactor
  sharding_ring:
    kvstore:
      store: memberlist

distributor:
  ring:
    instance_addr: 127.0.0.1
    kvstore:
      store: memberlist

ingester:
  ring:
    instance_addr: 127.0.0.1
    kvstore:
      store: memberlist
    replication_factor: 1

memberlist:
  join_members: []

ruler_storage:
  backend: filesystem
  filesystem:
    dir: /data/mimir/rules

store_gateway:
  sharding_ring:
    replication_factor: 1
```

<a id="arquivo-016"></a>

### `observability/prometheus.yml`

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

remote_write:
  - url: http://mimir:9009/api/v1/push
    queue_config:
      max_samples_per_send: 1000
      max_shards: 200
      capacity: 2500

scrape_configs:
  - job_name: 'yggdrasil-backend'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['yggdrasil-backend:8080']
        labels:
          application: 'yggdrasil'
```

<a id="arquivo-017"></a>

### `observability/tempo.yml`

```yaml
server:
  http_listen_port: 3200

distributor:
  receivers:
    otlp:
      protocols:
        grpc:
          endpoint: 0.0.0.0:4317
        http:
          endpoint: 0.0.0.0:4318

storage:
  trace:
    backend: local
    local:
      path: /var/tempo/traces
    wal:
      path: /var/tempo/wal

compactor:
  compaction:
    block_retention: 48h
```

<a id="arquivo-018"></a>

### `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.4.5</version>
    <relativePath/>
  </parent>

  <groupId>com.matheusjfa</groupId>
  <artifactId>yggdrasil</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <name>yggdrasil</name>
  <description>Generic Rules Engine</description>

  <properties>
    <java.version>21</java.version>
    <testcontainers.version>1.21.3</testcontainers.version>
    <springdoc.version>2.7.0</springdoc.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>testcontainers-bom</artifactId>
        <version>${testcontainers.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <!-- Web -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- YAML serialization -->
    <dependency>
      <groupId>com.fasterxml.jackson.dataformat</groupId>
      <artifactId>jackson-dataformat-yaml</artifactId>
    </dependency>

    <!-- MongoDB -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>

    <!-- Cache + Caffeine -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
      <groupId>com.github.ben-manes.caffeine</groupId>
      <artifactId>caffeine</artifactId>
    </dependency>

    <!-- Kafka -->
    <dependency>
      <groupId>org.springframework.kafka</groupId>
      <artifactId>spring-kafka</artifactId>
    </dependency>

    <!-- AOP -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

    <!-- Lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>

    <!-- MapStruct -->
    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId>
      <version>1.6.3</version>
    </dependency>

    <!-- OpenAPI -->
    <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>${springdoc.version}</version>
    </dependency>

    <!-- Validation -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Security -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-test</artifactId>
      <scope>test</scope>
    </dependency>

    <!-- Distributed Tracing -->
    <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-tracing-bridge-otel</artifactId>
    </dependency>
    <dependency>
      <groupId>io.opentelemetry</groupId>
      <artifactId>opentelemetry-exporter-otlp</artifactId>
    </dependency>

    <!-- Loki logback appender -->
    <dependency>
      <groupId>com.github.loki4j</groupId>
      <artifactId>loki-logback-appender</artifactId>
      <version>1.5.2</version>
    </dependency>

    <!-- Actuator + Micrometer Prometheus -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>

    <!-- Configuration Processor -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-configuration-processor</artifactId>
      <optional>true</optional>
    </dependency>

    <!-- Test -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.kafka</groupId>
      <artifactId>spring-kafka-test</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.testcontainers</groupId>
      <artifactId>mongodb</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.testcontainers</groupId>
      <artifactId>kafka</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.testcontainers</groupId>
      <artifactId>junit-jupiter</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <annotationProcessorPaths>
            <path>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <version>${lombok.version}</version>
            </path>
            <path>
              <groupId>org.mapstruct</groupId>
              <artifactId>mapstruct-processor</artifactId>
              <version>1.6.3</version>
            </path>
          </annotationProcessorPaths>
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <excludes>
            <exclude>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
            </exclude>
          </excludes>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

<a id="arquivo-019"></a>

### `src/main/java/com/matheusjfa/yggdrasil/YggdrasilApplication.java`

```java
package com.matheusjfa.yggdrasil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class YggdrasilApplication {
    public static void main(String[] args) {
        SpringApplication.run(YggdrasilApplication.class, args);
    }
}
```

<a id="arquivo-020"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/service/RuleForestService.java`

```java
package com.matheusjfa.yggdrasil.application.service;

import com.matheusjfa.yggdrasil.domain.rule.RuleNode;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.infrastructure.common.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Application service responsible for building and caching the in-memory rule forest.
 *
 * <p>Assembles an ordered, linked tree structure of {@link RuleNode}s from the active
 * rules stored in the repository. The result is cached so that every evaluation request
 * can traverse the forest without hitting the database. The cache is invalidated whenever
 * a rule is created, updated, deleted, or has its status changed, ensuring the forest
 * always reflects the current persisted state.
 */
@Service
@RequiredArgsConstructor
public class RuleForestService {

    private final RuleRepository ruleRepository;

    /**
     * Builds (or returns the cached) rule forest from all active rules.
     *
     * <p>Constructs a directed graph where root nodes are rules that are not the target
     * of any chain, and edges represent chain configurations. Roots are sorted by rule
     * priority (ascending) so that higher-priority rules are evaluated first.
     *
     * @return an ordered list of root {@link RuleNode}s representing the forest;
     *         never {@code null}, may be empty if there are no active rules
     */
    @Cacheable(AppConstants.RULE_FOREST_CACHE)
    public List<RuleNode> buildForest() {
        var rules = ruleRepository.findAllActive();

        var index = new LinkedHashMap<String, RuleNode>();
        for (var rule : rules) {
            index.put(rule.getId(), new RuleNode(rule));
        }

        var targetIds = new HashSet<String>();
        for (var rule : rules) {
            var node = index.get(rule.getId());
            for (var chain : rule.getChains()) {
                var child = index.get(chain.targetRuleId());
                if (child != null) {
                    node.addEdge(chain, child);
                    targetIds.add(chain.targetRuleId());
                }
            }
        }

        return rules.stream()
                .filter(r -> !targetIds.contains(r.getId()))
                .map(r -> index.get(r.getId()))
                .sorted(Comparator.comparingInt(n -> n.getRule().getPriority()))
                .toList();
    }

    /**
     * Evicts the cached rule forest, forcing the next call to {@link #buildForest()}
     * to rebuild it from the repository.
     *
     * <p>Should be called after any operation that modifies the set or content of active rules.
     */
    @CacheEvict(value = AppConstants.RULE_FOREST_CACHE, allEntries = true)
    public void evict() {}
}
```

<a id="arquivo-021"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/service/RuleVersionService.java`

```java
package com.matheusjfa.yggdrasil.application.service;

import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service that creates and persists {@link RuleVersion} snapshots.
 *
 * <p>Called by rule-mutating use cases after a rule is saved to ensure an immutable
 * audit trail of every change. Resolves the current authenticated user from the
 * Spring Security context to attribute the snapshot to the correct principal,
 * falling back to {@code "system"} when no authenticated user is present
 * (e.g. during scheduled or internal operations).
 */
@Service
@RequiredArgsConstructor
public class RuleVersionService {

    private final RuleVersionRepository ruleVersionRepository;

    /**
     * Creates and persists a versioned snapshot of the given rule's current state.
     *
     * <p>The version number is determined by the repository (next sequential number),
     * and the author is resolved from the active Spring Security context.
     *
     * @param rule        the rule to snapshot; field values are captured at call time
     * @param changeType  the type of operation that triggered this snapshot
     */
    public void snapshot(Rule rule, ChangeType changeType) {
        String changedBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .orElse("system");
        int version = ruleVersionRepository.nextVersion(rule.getId());
        ruleVersionRepository.save(RuleVersion.of(rule, version, changeType, changedBy));
    }
}
```

<a id="arquivo-022"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/EvaluateContextUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.evaluation;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import com.matheusjfa.yggdrasil.domain.evaluation.RuleContext;
import com.matheusjfa.yggdrasil.domain.evaluation.RuleEvaluator;
import com.matheusjfa.yggdrasil.domain.execution.*;
import com.matheusjfa.yggdrasil.domain.rule.ChainConfiguration;
import com.matheusjfa.yggdrasil.domain.rule.RuleNode;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Application use case that evaluates an input context against the active rule forest.
 *
 * <p>Traverses the rule forest depth-first, evaluating each root rule's condition and
 * recursively following chain edges when a rule fires. Enrichment expressions are evaluated
 * per edge and injected into a branched copy of the context before evaluating child rules,
 * preventing cross-branch contamination. Cycle detection guards against infinite recursion.
 *
 * <p>When not in dry-run mode, the result is persisted as a {@link RuleExecution} record and
 * Micrometer counters/timers are updated. Dry-run mode allows callers to preview the outcome
 * without side-effects.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluateContextUseCase {

    private final RuleForestService ruleForestService;
    private final RuleEvaluator ruleEvaluator;
    private final RuleExecutionRepository executionRepository;
    private final MeterRegistry meterRegistry;
    private final GetMetricsUseCase getMetricsUseCase;

    /**
     * Evaluates the input context against all active rules and returns the result.
     *
     * @param input the evaluation request containing the context attributes, the trigger origin,
     *              and a flag indicating whether the result should be persisted
     * @return an {@link Output} describing which rules fired and, when not a dry run, the
     *         identifier of the persisted execution record
     */
    public Output execute(Input input) {
        var sample = Timer.start(meterRegistry);

        var forest = ruleForestService.buildForest();
        var context = new RuleContext(new HashMap<>(input.context()));
        var firedRules = new ArrayList<FiredRule>();
        var chainTrace = new ArrayList<ChainStep>();
        var visited = new HashSet<String>();

        for (var rootNode : forest) {
            var rule = rootNode.getRule();
            if (ruleEvaluator.evaluate(rule.getCondition(), context)) {
                firedRules.add(new FiredRule(rule.getId(), rule.getName(), rule.getActions()));
                visited.add(rule.getId());
                evaluateChildren(rootNode, context, firedRules, chainTrace, visited);
            }
        }

        String executionId = null;
        if (!input.dryRun()) {
            var execution = RuleExecution.record(input.context(), firedRules, chainTrace, input.origin());
            executionRepository.save(execution);
            executionId = execution.getId();

            Counter.builder("yggdrasil.evaluations.total").register(meterRegistry).increment();
            for (var fired : firedRules) {
                Counter.builder("yggdrasil.rules.fired")
                        .tag("rule_id", fired.ruleId())
                        .tag("rule_name", fired.ruleName())
                        .register(meterRegistry)
                        .increment();
            }
            getMetricsUseCase.evict();
        }

        sample.stop(Timer.builder("yggdrasil.evaluation.duration").register(meterRegistry));

        return new Output(executionId, !firedRules.isEmpty(), firedRules, input.context());
    }

    private void evaluateChildren(RuleNode sourceNode, RuleContext context, List<FiredRule> firedRules,
                                  List<ChainStep> chainTrace, Set<String> visited) {
        for (var edge : sourceNode.getEdges()) {
            var targetNode = edge.child();
            var targetRule = targetNode.getRule();

            if (visited.contains(targetRule.getId())) {
                chainTrace.add(new ChainStep(sourceNode.getRule().getId(), targetRule.getId(),
                        Map.of(), false, "Cycle detected"));
                continue;
            }

            var appliedEnrichments = evaluateEnrichments(edge.chain(), context);
            var branchedContext = context.copy();
            appliedEnrichments.forEach(branchedContext::put);

            if (ruleEvaluator.evaluate(targetRule.getCondition(), branchedContext)) {
                firedRules.add(new FiredRule(targetRule.getId(), targetRule.getName(), targetRule.getActions()));
                chainTrace.add(new ChainStep(sourceNode.getRule().getId(), targetRule.getId(),
                        appliedEnrichments, true, null));
                visited.add(targetRule.getId());
                evaluateChildren(targetNode, branchedContext, firedRules, chainTrace, visited);
            } else {
                chainTrace.add(new ChainStep(sourceNode.getRule().getId(), targetRule.getId(),
                        appliedEnrichments, false, "Condition not met"));
            }
        }
    }

    private Map<String, Object> evaluateEnrichments(ChainConfiguration chain, RuleContext context) {
        return chain.enrichments().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ruleEvaluator.evaluateExpression(e.getValue(), context)
                ));
    }

    /**
     * Input parameters for the evaluate-context use case.
     *
     * @param context  the key-value attributes to evaluate against the rule forest
     * @param origin   how this evaluation was triggered (e.g. API call, schedule, replay)
     * @param dryRun   when {@code true} the result is not persisted and metrics are not updated
     */
    public record Input(Map<String, Object> context, ExecutionOrigin origin, boolean dryRun) {}

    /**
     * Output of the evaluate-context use case.
     *
     * @param executionId  the identifier of the persisted execution record,
     *                     or {@code null} when {@code dryRun} is {@code true}
     * @param hasMatches   {@code true} if at least one rule fired
     * @param firedRules   the ordered list of rules that fired during this evaluation
     * @param inputContext the original context attributes that were supplied as input
     */
    public record Output(String executionId, boolean hasMatches, List<FiredRule> firedRules,
                         Map<String, Object> inputContext) {}
}
```

<a id="arquivo-023"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/ReplayExecutionUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.evaluation;

import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application use case that re-evaluates a past execution using its original input context.
 *
 * <p>Fetches the stored execution by identifier, then delegates to
 * {@link EvaluateContextUseCase} with the same context and the {@link ExecutionOrigin#REPLAY}
 * origin. This allows callers to reproduce a historical evaluation against the current
 * rule set, which is useful for debugging and auditing.
 */
@Service
@RequiredArgsConstructor
public class ReplayExecutionUseCase {

    private final RuleExecutionRepository executionRepository;
    private final EvaluateContextUseCase evaluateContextUseCase;

    /**
     * Replays a past execution identified by the given identifier.
     *
     * @param id     the identifier of the past {@link com.matheusjfa.yggdrasil.domain.execution.RuleExecution}
     *               to replay
     * @param dryRun when {@code true} the replay result is not persisted
     * @return the output of evaluating the original context against the current rule forest
     * @throws IllegalArgumentException if no execution with the given {@code id} is found
     */
    public EvaluateContextUseCase.Output execute(String id, boolean dryRun) {
        var past = executionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found: " + id));
        return evaluateContextUseCase.execute(
                new EvaluateContextUseCase.Input(past.getInputContext(), ExecutionOrigin.REPLAY, dryRun));
    }
}
```

<a id="arquivo-024"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/execution/GetExecutionUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.execution;

import com.matheusjfa.yggdrasil.domain.execution.RuleExecution;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application use case for retrieving a single rule execution record by its identifier.
 *
 * <p>Fetches the full {@link RuleExecution} aggregate from the repository, including
 * the input context, fired rules, and chain trace. Throws an exception if no record
 * with the given identifier exists.
 */
@Service
@RequiredArgsConstructor
public class GetExecutionUseCase {

    private final RuleExecutionRepository executionRepository;

    /**
     * Retrieves the execution record with the given identifier.
     *
     * @param id the string representation of the {@link com.matheusjfa.yggdrasil.domain.execution.ExecutionId}
     *           to look up
     * @return the matching {@link RuleExecution}; never {@code null}
     * @throws IllegalArgumentException if no execution with the given {@code id} exists
     */
    public RuleExecution execute(String id) {
        return executionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Execution not found: " + id));
    }
}
```

<a id="arquivo-025"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/execution/ListExecutionsUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.execution;

import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.execution.FiredRule;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecution;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Application use case for listing rule execution records in reverse chronological order.
 *
 * <p>Returns a paginated view of all executions, mapping each {@link RuleExecution}
 * aggregate to a flat {@link Output} projection for API consumption.
 */
@Service
@RequiredArgsConstructor
public class ListExecutionsUseCase {

    private final RuleExecutionRepository executionRepository;

    /**
     * Returns a paginated page of execution records ordered from most to least recent.
     *
     * @param pageable pagination parameters (page number, page size); sort order is
     *                 fixed to {@code executedAt} descending by the repository
     * @return a page of {@link Output} projections; never {@code null}
     */
    public Page<Output> execute(Pageable pageable) {
        return executionRepository.findAllOrderByExecutedAtDesc(pageable).map(Output::from);
    }

    /**
     * Flat projection of a {@link RuleExecution} used as the API response payload.
     *
     * @param id            the string identifier of the execution record
     * @param inputContext  the key-value attributes that were evaluated
     * @param firedRules    the rules that fired during the execution
     * @param origin        how the execution was triggered
     * @param executedAt    the UTC timestamp when the execution was performed
     */
    public record Output(String id, Map<String, Object> inputContext, List<FiredRule> firedRules,
                         ExecutionOrigin origin, Instant executedAt) {
        static Output from(RuleExecution e) {
            return new Output(e.getId(), e.getInputContext(), e.getFiredRules(), e.getOrigin(), e.getExecutedAt());
        }
    }
}
```

<a id="arquivo-026"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/metrics/GetMetricsUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.metrics;

import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.infrastructure.common.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application use case that computes and caches aggregate metrics for the dashboard.
 *
 * <p>Returns the total number of recorded evaluations and, for every rule in the system,
 * how many times it has fired. The result is cached to avoid expensive repository queries
 * on every dashboard refresh; the cache is invalidated after each evaluation so counters
 * remain fresh.
 */
@Service
@RequiredArgsConstructor
public class GetMetricsUseCase {

    private final RuleRepository ruleRepository;
    private final RuleExecutionRepository executionRepository;

    /**
     * Returns the cached metrics output, rebuilding it from the repository if the cache is empty.
     *
     * @return an {@link Output} containing total executions and per-rule fire counts
     */
    @Cacheable(AppConstants.METRICS_CACHE)
    public Output execute() {
        long totalExecutions = executionRepository.countAll();

        var ruleMetrics = ruleRepository.findAll().stream()
                .map(rule -> new RuleMetric(rule.getId(), rule.getName(),
                        executionRepository.countFiredByRuleId(rule.getId())))
                .toList();

        return new Output(totalExecutions, ruleMetrics);
    }

    /**
     * Evicts the cached metrics, forcing the next call to {@link #execute()} to
     * recompute the values from the repository.
     */
    @CacheEvict(value = AppConstants.METRICS_CACHE, allEntries = true)
    public void evict() {}

    /**
     * Per-rule fire count metric.
     *
     * @param ruleId     the identifier of the rule
     * @param ruleName   the human-readable name of the rule
     * @param firedCount the number of executions in which this rule fired
     */
    public record RuleMetric(String ruleId, String ruleName, long firedCount) {}

    /**
     * Aggregated metrics output returned by this use case.
     *
     * @param totalExecutions the total number of persisted execution records
     * @param ruleMetrics     per-rule fire counts for all rules in the system
     */
    public record Output(long totalExecutions, List<RuleMetric> ruleMetrics) {}
}
```

<a id="arquivo-027"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ConflictStrategy.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

/**
 * Strategy controlling how {@link ImportRulesUseCase} handles rules whose identifiers
 * already exist in the repository.
 */
public enum ConflictStrategy {

    /** Leave the existing rule unchanged and count it as skipped. */
    SKIP,

    /** Replace the existing rule with the imported definition. */
    OVERWRITE,

    /** Abort the entire import immediately and throw an exception. */
    FAIL
}
```

<a id="arquivo-028"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/CreateRuleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for creating a new rule.
 *
 * <p>Delegates to {@link Rule#create} to produce an initial active rule aggregate,
 * persists it, takes a {@link ChangeType#CREATE} version snapshot for audit, and
 * evicts the rule forest cache so the new rule is included in subsequent evaluations.
 */
@Service
@RequiredArgsConstructor
public class CreateRuleUseCase {

    private final RuleRepository ruleRepository;
    private final RuleForestService ruleForestService;
    private final RuleVersionService ruleVersionService;

    /**
     * Creates and persists a new rule from the given input.
     *
     * @param input the creation parameters
     * @return an {@link Output} projection of the saved rule
     */
    public Output execute(Input input) {
        var rule = Rule.create(input.name(), input.description(), input.priority(),
                input.condition(), input.actions(), input.chains());
        var saved = ruleRepository.save(rule);
        ruleVersionService.snapshot(saved, ChangeType.CREATE);
        ruleForestService.evict();
        return Output.from(saved);
    }

    /**
     * Input parameters for the create-rule use case.
     *
     * @param name        the human-readable name for the new rule
     * @param description an optional explanation of the rule's purpose
     * @param priority    the evaluation priority; lower values are evaluated first
     * @param condition   the root of the condition tree that must be satisfied for the rule to fire
     * @param actions     the ordered list of actions to execute when the rule fires
     * @param chains      the list of chaining configurations linking this rule to downstream rules;
     *                    may be {@code null} or empty
     */
    public record Input(String name, String description, int priority,
                        ConditionNode condition, List<RuleAction> actions,
                        List<ChainConfiguration> chains) {}

    /**
     * Output projection of the newly created rule.
     *
     * @param id          the generated identifier of the rule
     * @param name        the rule name
     * @param description the rule description
     * @param priority    the evaluation priority
     * @param condition   the condition tree
     * @param actions     the list of actions
     * @param chains      the chain configurations
     * @param status      the initial status (always {@link RuleStatus#ACTIVE})
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last-updated timestamp (same as {@code createdAt} on creation)
     */
    public record Output(String id, String name, String description, int priority,
                         ConditionNode condition, List<RuleAction> actions,
                         List<ChainConfiguration> chains, RuleStatus status,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Rule r) {
            return new Output(r.getId(), r.getName(), r.getDescription(), r.getPriority(),
                    r.getCondition(), r.getActions(), r.getChains(), r.getStatus(),
                    r.getCreatedAt(), r.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-029"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/DeleteRuleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application use case for permanently deleting a rule by its identifier.
 *
 * <p>Verifies that the rule exists before deletion to produce a clear error when it does not.
 * After deletion, evicts the rule forest cache so the removed rule is no longer evaluated.
 */
@Service
@RequiredArgsConstructor
public class DeleteRuleUseCase {

    private final RuleRepository ruleRepository;
    private final RuleForestService ruleForestService;

    /**
     * Deletes the rule with the given identifier.
     *
     * @param id the identifier of the rule to delete
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code id} exists
     */
    public void execute(String id) {
        if (!ruleRepository.exists(id)) throw RuleException.notFound(id);
        ruleRepository.delete(id);
        ruleForestService.evict();
    }
}
```

<a id="arquivo-030"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRuleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for exporting a single rule as a portable {@link RulePackage}.
 *
 * <p>Produces a versioned package containing exactly one rule entry that can be
 * imported into another environment using {@link ImportRulesUseCase}.
 */
@Service
@RequiredArgsConstructor
public class ExportRuleUseCase {

    private final RuleRepository ruleRepository;

    /**
     * Exports the rule with the given identifier as a {@link RulePackage}.
     *
     * @param ruleId the identifier of the rule to export
     * @return a {@link RulePackage} with schema version {@code "1.0"} containing one rule entry
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code ruleId} exists
     */
    public RulePackage execute(String ruleId) {
        var rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> RuleException.notFound(ruleId));
        return new RulePackage("1.0", Instant.now(), List.of(toEntry(rule)));
    }

    /**
     * Converts a {@link Rule} aggregate to a {@link RulePackage.RuleEntry} for serialisation.
     *
     * @param rule the rule to convert
     * @return the corresponding package entry
     */
    static RulePackage.RuleEntry toEntry(Rule rule) {
        return new RulePackage.RuleEntry(
                rule.getId(),
                rule.getName(),
                rule.getDescription(),
                rule.getPriority(),
                rule.getStatus(),
                rule.getCondition(),
                rule.getActions(),
                rule.getChains()
        );
    }
}
```

<a id="arquivo-031"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRulesUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Application use case for exporting all rules as a single portable {@link RulePackage}.
 *
 * <p>Retrieves every rule from the repository (regardless of status) and packages them
 * into a versioned export document that can be imported into another environment
 * using {@link ImportRulesUseCase}.
 */
@Service
@RequiredArgsConstructor
public class ExportRulesUseCase {

    private final RuleRepository ruleRepository;

    /**
     * Exports all rules as a {@link RulePackage}.
     *
     * @return a {@link RulePackage} with schema version {@code "1.0"} containing an entry
     *         for every rule in the repository; never {@code null}, entries list may be empty
     */
    public RulePackage execute() {
        var entries = ruleRepository.findAll().stream()
                .map(ExportRuleUseCase::toEntry)
                .toList();
        return new RulePackage("1.0", Instant.now(), entries);
    }
}
```

<a id="arquivo-032"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetForestUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.domain.rule.RuleNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Application use case that exposes the rule forest as a graph of nodes and edges
 * suitable for visualisation.
 *
 * <p>Traverses the in-memory forest built by {@link RuleForestService} and flattens
 * it into a list of {@link NodeDto}s (rules) and {@link EdgeDto}s (chain connections)
 * that can be rendered directly by a graph-rendering UI component.
 */
@Service
@RequiredArgsConstructor
public class GetForestUseCase {

    private final RuleForestService ruleForestService;

    /**
     * Builds and returns the graph representation of the current active rule forest.
     *
     * @return an {@link Output} containing all rule nodes and chain edges;
     *         never {@code null}, lists may be empty if there are no active rules
     */
    public Output execute() {
        var forest = ruleForestService.buildForest();

        var nodes = new ArrayList<NodeDto>();
        var edges = new ArrayList<EdgeDto>();
        var visited = new HashSet<String>();

        for (var root : forest) {
            traverse(root, nodes, edges, visited);
        }

        return new Output(nodes, edges);
    }

    private void traverse(RuleNode node, List<NodeDto> nodes, List<EdgeDto> edges, Set<String> visited) {
        var rule = node.getRule();
        if (visited.contains(rule.getId())) return;
        visited.add(rule.getId());

        nodes.add(new NodeDto(
                rule.getId(),
                rule.getName(),
                rule.getPriority(),
                rule.getStatus().name(),
                rule.getActions().size()
        ));

        for (var edge : node.getEdges()) {
            edges.add(new EdgeDto(
                    rule.getId() + "-" + edge.child().getRule().getId(),
                    rule.getId(),
                    edge.child().getRule().getId(),
                    edge.chain().enrichments()
            ));
            traverse(edge.child(), nodes, edges, visited);
        }
    }

    /**
     * Graph node representing a single rule.
     *
     * @param id            the rule identifier
     * @param label         the human-readable rule name used as the node label
     * @param priority      the evaluation priority of the rule
     * @param status        the rule status as a string (e.g. {@code "ACTIVE"})
     * @param actionsCount  the number of actions configured on the rule
     */
    public record NodeDto(String id, String label, int priority, String status, int actionsCount) {}

    /**
     * Directed graph edge representing a chain relationship between two rules.
     *
     * @param id           a synthetic identifier formed as {@code sourceId}-{@code targetId}
     * @param source       the identifier of the source (parent) rule
     * @param target       the identifier of the target (child) rule
     * @param enrichments  the SpEL enrichment expressions associated with this chain edge
     */
    public record EdgeDto(String id, String source, String target, Map<String, String> enrichments) {}

    /**
     * Output of the get-forest use case.
     *
     * @param nodes  all rule nodes in the active forest
     * @param edges  all chain edges connecting the rule nodes
     */
    public record Output(List<NodeDto> nodes, List<EdgeDto> edges) {}
}
```

<a id="arquivo-033"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for retrieving a single rule by its identifier.
 *
 * <p>Looks up the rule in the repository and maps it to a flat {@link Output}
 * projection for API consumption. Throws an exception if the rule does not exist.
 */
@Service
@RequiredArgsConstructor
public class GetRuleUseCase {

    private final RuleRepository ruleRepository;

    /**
     * Retrieves the rule with the given identifier.
     *
     * @param id the string representation of the rule's identifier
     * @return an {@link Output} projection of the found rule
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code id} exists
     */
    public Output execute(String id) {
        var rule = ruleRepository.findById(id).orElseThrow(() -> RuleException.notFound(id));
        return Output.from(rule);
    }

    /**
     * Flat projection of a {@link Rule} used as the API response payload.
     *
     * @param id          the rule identifier
     * @param name        the rule name
     * @param description the rule description
     * @param priority    the evaluation priority
     * @param condition   the condition tree
     * @param actions     the list of actions
     * @param chains      the chain configurations
     * @param status      the current lifecycle status
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last-updated timestamp
     */
    public record Output(String id, String name, String description, int priority,
                         ConditionNode condition, List<RuleAction> actions,
                         List<ChainConfiguration> chains, RuleStatus status,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Rule r) {
            return new Output(r.getId(), r.getName(), r.getDescription(), r.getPriority(),
                    r.getCondition(), r.getActions(), r.getChains(), r.getStatus(),
                    r.getCreatedAt(), r.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-034"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleVersionUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application use case for retrieving a specific version snapshot of a rule.
 *
 * <p>Verifies that the parent rule exists before looking up the version, so the
 * caller receives a meaningful error regardless of whether the rule or the version
 * is missing.
 */
@Service
@RequiredArgsConstructor
public class GetRuleVersionUseCase {

    private final RuleRepository ruleRepository;
    private final RuleVersionRepository ruleVersionRepository;

    /**
     * Retrieves a specific version snapshot for the given rule.
     *
     * @param ruleId  the identifier of the rule whose version history is queried
     * @param version the sequential version number to retrieve (1-based)
     * @return the matching {@link RuleVersion} snapshot
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code ruleId} exists
     * @throws IllegalArgumentException if the rule exists but has no snapshot for the given
     *         version number
     */
    public RuleVersion execute(String ruleId, int version) {
        ruleRepository.findById(ruleId).orElseThrow(() -> RuleException.notFound(ruleId));
        return ruleVersionRepository.findByRuleIdAndVersion(ruleId, version)
                .orElseThrow(() -> new IllegalArgumentException("Version not found: " + version));
    }
}
```

<a id="arquivo-035"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ImportRulesUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Application use case for importing rules from a portable {@link RulePackage}.
 *
 * <p>Processes each entry in the package according to the chosen {@link ConflictStrategy}:
 * when {@link ConflictStrategy#FAIL} is selected, the entire import is validated for
 * conflicts before any changes are persisted. New rules are reconstituted from the package
 * entry, preserving the original identifier. Existing rules can be overwritten or skipped.
 */
@Service
@RequiredArgsConstructor
public class ImportRulesUseCase {

    private final RuleRepository ruleRepository;

    /**
     * Summary of how many rules were imported and how many were skipped.
     *
     * @param imported the number of rules that were created or overwritten
     * @param skipped  the number of rules that were left unchanged due to the
     *                 {@link ConflictStrategy#SKIP} strategy
     */
    public record Result(int imported, int skipped) {}

    /**
     * Imports all rules from the given package using the specified conflict strategy.
     *
     * @param pkg      the package containing the rules to import
     * @param strategy how to handle rules whose identifiers already exist in the repository
     * @return a {@link Result} summarising how many rules were imported and skipped
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if {@code strategy} is
     *         {@link ConflictStrategy#FAIL} and at least one conflicting rule is found
     */
    public Result execute(RulePackage pkg, ConflictStrategy strategy) {
        if (strategy == ConflictStrategy.FAIL) {
            for (var entry : pkg.rules()) {
                if (ruleRepository.findById(entry.id()).isPresent()) {
                    throw RuleException.conflict(entry.id());
                }
            }
        }

        int imported = 0;
        int skipped = 0;

        for (var entry : pkg.rules()) {
            var existing = ruleRepository.findById(entry.id());
            if (existing.isPresent()) {
                if (strategy == ConflictStrategy.SKIP) {
                    skipped++;
                } else {
                    var rule = existing.get();
                    rule.update(entry.name(), entry.description(), entry.priority(),
                            entry.condition(), entry.actions(), entry.chains());
                    ruleRepository.save(rule);
                    imported++;
                }
            } else {
                var now = Instant.now();
                var rule = Rule.reconstitute(entry.id(), entry.name(), entry.description(),
                        entry.priority(), entry.condition(), entry.actions(), entry.chains(),
                        entry.status(), now, now);
                ruleRepository.save(rule);
                imported++;
            }
        }

        return new Result(imported, skipped);
    }
}
```

<a id="arquivo-036"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ListRuleVersionsUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for listing the version history of a specific rule.
 *
 * <p>Verifies that the parent rule exists, then returns a lightweight summary of each
 * version snapshot ordered by the repository's default ordering (typically version ascending).
 */
@Service
@RequiredArgsConstructor
public class ListRuleVersionsUseCase {

    private final RuleRepository ruleRepository;
    private final RuleVersionRepository ruleVersionRepository;

    /**
     * Returns a list of version summaries for the given rule.
     *
     * @param ruleId the identifier of the rule whose version history is requested
     * @return a list of {@link VersionSummary} records; never {@code null}, may be empty
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code ruleId} exists
     */
    public List<VersionSummary> execute(String ruleId) {
        ruleRepository.findById(ruleId).orElseThrow(() -> RuleException.notFound(ruleId));
        return ruleVersionRepository.findByRuleId(ruleId).stream()
                .map(v -> new VersionSummary(v.getId(), v.getVersion(), v.getChangeType(), v.getChangedAt()))
                .toList();
    }

    /**
     * Lightweight summary of a single version snapshot.
     *
     * @param id          the unique identifier of the version record
     * @param version     the sequential version number
     * @param changeType  the type of operation that created this snapshot
     * @param changedAt   the UTC timestamp when the snapshot was taken
     */
    public record VersionSummary(String id, int version, ChangeType changeType, Instant changedAt) {}
}
```

<a id="arquivo-037"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/ListRulesUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for listing rules with optional filtering by name or status.
 *
 * <p>Supports three query modes: filter by name substring, filter by status, or return
 * all rules. Name filtering takes precedence over status filtering when both are provided.
 */
@Service
@RequiredArgsConstructor
public class ListRulesUseCase {

    private final RuleRepository ruleRepository;

    /**
     * Returns a list of rules matching the given optional filters.
     *
     * @param name   a substring to filter rules by name; when non-blank, name filtering
     *               takes precedence over {@code status} filtering
     * @param status a {@link RuleStatus} to filter by; used only when {@code name} is blank or {@code null}
     * @return a list of {@link Output} projections for the matching rules;
     *         never {@code null}, may be empty
     */
    public List<Output> execute(String name, RuleStatus status) {
        List<Rule> rules;
        if (name != null && !name.isBlank()) {
            rules = ruleRepository.findAllByNameContaining(name);
        } else if (status != null) {
            rules = ruleRepository.findAllByStatus(status);
        } else {
            rules = ruleRepository.findAll();
        }
        return rules.stream().map(Output::from).toList();
    }

    /**
     * Flat projection of a {@link Rule} used as the API response payload.
     *
     * @param id          the rule identifier
     * @param name        the rule name
     * @param description the rule description
     * @param priority    the evaluation priority
     * @param condition   the condition tree
     * @param actions     the list of actions
     * @param chains      the chain configurations
     * @param status      the current lifecycle status
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last-updated timestamp
     */
    public record Output(String id, String name, String description, int priority,
                         ConditionNode condition, List<RuleAction> actions,
                         List<ChainConfiguration> chains, RuleStatus status,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Rule r) {
            return new Output(r.getId(), r.getName(), r.getDescription(), r.getPriority(),
                    r.getCondition(), r.getActions(), r.getChains(), r.getStatus(),
                    r.getCreatedAt(), r.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-038"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/RestoreRuleVersionUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application use case that rolls back a rule to a previously recorded version snapshot.
 *
 * <p>Fetches the target version snapshot, applies its field values to the current rule
 * aggregate, saves the result, and records a new {@link ChangeType#RESTORE} version so
 * the rollback is itself audited. Evicts both the rule forest and metrics caches to keep
 * them consistent with the restored state.
 */
@Service
@RequiredArgsConstructor
public class RestoreRuleVersionUseCase {

    private final RuleRepository ruleRepository;
    private final RuleVersionRepository ruleVersionRepository;
    private final RuleVersionService ruleVersionService;
    private final RuleForestService ruleForestService;
    private final GetMetricsUseCase getMetricsUseCase;

    /**
     * Restores the given rule to the state captured in the specified version snapshot.
     *
     * @param ruleId  the identifier of the rule to restore
     * @param version the sequential version number to restore from
     * @return an {@link UpdateRuleUseCase.Output} projection of the rule after restoration
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code ruleId} exists
     * @throws IllegalArgumentException if the rule exists but has no snapshot for the given
     *         version number
     */
    public UpdateRuleUseCase.Output execute(String ruleId, int version) {
        var rule = ruleRepository.findById(ruleId).orElseThrow(() -> RuleException.notFound(ruleId));
        var snapshot = ruleVersionRepository.findByRuleIdAndVersion(ruleId, version)
                .orElseThrow(() -> new IllegalArgumentException("Version not found: " + version));

        rule.update(snapshot.getName(), snapshot.getDescription(), snapshot.getPriority(),
                snapshot.getCondition(), snapshot.getActions(), snapshot.getChains());

        var saved = ruleRepository.save(rule);
        ruleVersionService.snapshot(saved, ChangeType.RESTORE);
        ruleForestService.evict();
        getMetricsUseCase.evict();
        return UpdateRuleUseCase.Output.from(saved);
    }
}
```

<a id="arquivo-039"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/RulePackage.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.ChainConfiguration;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;

import java.time.Instant;
import java.util.List;

/**
 * Portable, serialisable container used for exporting and importing rules across environments.
 *
 * <p>A {@code RulePackage} bundles one or more rule definitions into a single versioned document
 * that can be transferred as JSON. Used by {@link ExportRuleUseCase}, {@link ExportRulesUseCase},
 * and {@link ImportRulesUseCase}.
 *
 * @param version    the schema version of this package format (e.g. {@code "1.0"})
 * @param exportedAt the UTC instant at which the package was generated
 * @param rules      the list of rule entries included in this package
 */
public record RulePackage(String version, Instant exportedAt, List<RuleEntry> rules) {

    /**
     * Self-contained definition of a single rule within a {@link RulePackage}.
     *
     * @param id          the original identifier of the rule (preserved for idempotent re-import)
     * @param name        the rule name at export time
     * @param description the rule description at export time
     * @param priority    the evaluation priority at export time
     * @param status      the lifecycle status at export time
     * @param condition   the condition tree at export time
     * @param actions     the list of actions at export time
     * @param chains      the chain configurations at export time
     */
    public record RuleEntry(
            String id,
            String name,
            String description,
            int priority,
            RuleStatus status,
            ConditionNode condition,
            List<RuleAction> actions,
            List<ChainConfiguration> chains
    ) {}
}
```

<a id="arquivo-040"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/TestRuleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.evaluation.RuleContext;
import com.matheusjfa.yggdrasil.domain.evaluation.RuleEvaluator;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Application use case for testing whether a specific rule fires against an ad-hoc context.
 *
 * <p>Evaluates only the target rule's condition (not the full forest) without persisting
 * any execution record, making it suitable for rapid rule validation during development
 * or debugging.
 */
@Service
@RequiredArgsConstructor
public class TestRuleUseCase {

    private final RuleRepository ruleRepository;
    private final RuleEvaluator ruleEvaluator;

    /**
     * Tests whether the given rule fires against the supplied context.
     *
     * @param ruleId  the identifier of the rule to test
     * @param context the key-value attributes to evaluate against the rule's condition
     * @return an {@link Output} indicating whether the rule fired and the actions that would execute
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code ruleId} exists
     */
    public Output execute(String ruleId, Map<String, Object> context) {
        var rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> RuleException.notFound(ruleId));
        var fired = ruleEvaluator.evaluate(rule.getCondition(), new RuleContext(context));
        return new Output(fired, fired ? rule.getActions() : List.of());
    }

    /**
     * Result of a rule test evaluation.
     *
     * @param fired    {@code true} if the rule's condition was satisfied by the supplied context
     * @param actions  the actions that would execute if the rule fired;
     *                 empty list when {@code fired} is {@code false}
     */
    public record Output(boolean fired, List<RuleAction> actions) {}
}
```

<a id="arquivo-041"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleStatusUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for toggling a rule's lifecycle status between ACTIVE and INACTIVE.
 *
 * <p>Activating or deactivating a rule affects whether it participates in evaluations.
 * After the status change is saved, a {@link ChangeType#STATUS_CHANGE} version snapshot
 * is recorded and the rule forest cache is evicted.
 */
@Service
@RequiredArgsConstructor
public class UpdateRuleStatusUseCase {

    private final RuleRepository ruleRepository;
    private final RuleForestService ruleForestService;
    private final RuleVersionService ruleVersionService;

    /**
     * Updates the lifecycle status of the rule with the given identifier.
     *
     * @param id     the identifier of the rule whose status should be changed
     * @param status the new desired status ({@link RuleStatus#ACTIVE} or
     *               {@link RuleStatus#INACTIVE})
     * @return an {@link Output} projection of the rule after the status change
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code id} exists
     */
    public Output execute(String id, RuleStatus status) {
        var rule = ruleRepository.findById(id).orElseThrow(() -> RuleException.notFound(id));
        if (status == RuleStatus.ACTIVE) rule.activate();
        else rule.deactivate();
        var saved = ruleRepository.save(rule);
        ruleVersionService.snapshot(saved, ChangeType.STATUS_CHANGE);
        ruleForestService.evict();
        return Output.from(saved);
    }

    /**
     * Flat projection of a {@link Rule} returned after a status change.
     *
     * @param id          the rule identifier
     * @param name        the rule name
     * @param description the rule description
     * @param priority    the evaluation priority
     * @param condition   the condition tree
     * @param actions     the list of actions
     * @param status      the updated lifecycle status
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last-updated timestamp (reflects the status change)
     */
    public record Output(String id, String name, String description, int priority,
                         ConditionNode condition, List<RuleAction> actions,
                         RuleStatus status, Instant createdAt, Instant updatedAt) {
        static Output from(Rule r) {
            return new Output(r.getId(), r.getName(), r.getDescription(), r.getPriority(),
                    r.getCondition(), r.getActions(), r.getStatus(), r.getCreatedAt(), r.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-042"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for updating an existing rule's fields.
 *
 * <p>Applies a partial update (only non-null fields are changed) to the target rule aggregate,
 * persists the result, records a {@link ChangeType#UPDATE} version snapshot, and evicts the
 * rule forest cache so the change is reflected in subsequent evaluations.
 */
@Service
@RequiredArgsConstructor
public class UpdateRuleUseCase {

    private final RuleRepository ruleRepository;
    private final RuleForestService ruleForestService;
    private final RuleVersionService ruleVersionService;

    /**
     * Applies the given input to the rule identified by {@code id}.
     *
     * @param id    the identifier of the rule to update
     * @param input the update parameters; any {@code null} field is ignored
     * @return an {@link Output} projection of the rule after the update
     * @throws com.matheusjfa.yggdrasil.domain.rule.RuleException if no rule with the given
     *         {@code id} exists
     */
    public Output execute(String id, Input input) {
        var rule = ruleRepository.findById(id).orElseThrow(() -> RuleException.notFound(id));
        rule.update(input.name(), input.description(), input.priority(), input.condition(),
                input.actions(), input.chains());
        var saved = ruleRepository.save(rule);
        ruleVersionService.snapshot(saved, ChangeType.UPDATE);
        ruleForestService.evict();
        return Output.from(saved);
    }

    /**
     * Partial update parameters for the update-rule use case.
     * Any field that is {@code null} will leave the corresponding rule field unchanged.
     *
     * @param name        the new name, or {@code null} to keep the current name
     * @param description the new description, or {@code null} to keep the current description
     * @param priority    the new priority, or {@code null} to keep the current priority
     * @param condition   the new condition tree, or {@code null} to keep the current condition
     * @param actions     the new list of actions, or {@code null} to keep the current list
     * @param chains      the new chain configurations, or {@code null} to keep the current chains
     */
    public record Input(String name, String description, Integer priority,
                        ConditionNode condition, List<RuleAction> actions,
                        List<ChainConfiguration> chains) {}

    /**
     * Flat projection of a {@link Rule} returned after an update.
     *
     * @param id          the rule identifier
     * @param name        the rule name after the update
     * @param description the rule description after the update
     * @param priority    the evaluation priority after the update
     * @param condition   the condition tree after the update
     * @param actions     the list of actions after the update
     * @param chains      the chain configurations after the update
     * @param status      the current lifecycle status (unchanged by this use case)
     * @param createdAt   the original creation timestamp
     * @param updatedAt   the last-updated timestamp (reflects this update)
     */
    public record Output(String id, String name, String description, int priority,
                         ConditionNode condition, List<RuleAction> actions,
                         List<ChainConfiguration> chains, RuleStatus status,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Rule r) {
            return new Output(r.getId(), r.getName(), r.getDescription(), r.getPriority(),
                    r.getCondition(), r.getActions(), r.getChains(), r.getStatus(),
                    r.getCreatedAt(), r.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-043"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/CreateScheduleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Application use case for creating a new schedule.
 *
 * <p>Validates the provided cron expression, computes the first fire instant,
 * creates the {@link Schedule} aggregate, and persists it. The cron validation
 * uses Spring's {@link CronExpression} parser so that the format is consistent
 * with the scheduler daemon.
 */
@Service
@RequiredArgsConstructor
public class CreateScheduleUseCase {

    private final ScheduleRepository scheduleRepository;

    /**
     * Creates and persists a new schedule from the given input.
     *
     * @param input the creation parameters including the cron expression to validate
     * @return an {@link Output} projection of the saved schedule
     * @throws IllegalArgumentException if the provided cron expression is not valid
     */
    public Output execute(Input input) {
        validateCron(input.cronExpression());
        var nextFireAt = computeNextFireAt(input.cronExpression());
        var schedule = Schedule.create(input.name(), input.cronExpression(),
                input.contextUrl(), input.enabled(), nextFireAt);
        return Output.from(scheduleRepository.save(schedule));
    }

    private void validateCron(String expression) {
        try {
            CronExpression.parse(expression);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid cron expression: " + expression, e);
        }
    }

    /**
     * Computes the next fire instant after the current UTC time for the given cron expression.
     *
     * @param expression a valid Spring cron expression string
     * @return the next scheduled fire time, or {@code now + 60 seconds} as a fallback if the
     *         expression does not produce a future occurrence
     */
    public static Instant computeNextFireAt(String expression) {
        var cron = CronExpression.parse(expression);
        var next = cron.next(LocalDateTime.now(ZoneOffset.UTC));
        return next != null ? next.toInstant(ZoneOffset.UTC) : Instant.now().plusSeconds(60);
    }

    /**
     * Input parameters for the create-schedule use case.
     *
     * @param name            the human-readable label for this schedule
     * @param cronExpression  a Spring-compatible cron expression defining the fire cadence
     * @param contextUrl      the URL from which the evaluation context will be fetched at fire time
     * @param enabled         whether the schedule should start in the enabled state
     */
    public record Input(String name, String cronExpression, String contextUrl, boolean enabled) {}

    /**
     * Flat projection of the newly created {@link Schedule}.
     *
     * @param id             the generated identifier of the schedule
     * @param name           the schedule name
     * @param cronExpression the cron expression
     * @param contextUrl     the context URL
     * @param enabled        the initial enabled state
     * @param lastFiredAt    {@code null} because the schedule has never fired
     * @param nextFireAt     the computed first fire instant
     * @param createdAt      the creation timestamp
     * @param updatedAt      the last-updated timestamp (same as {@code createdAt} on creation)
     */
    public record Output(String id, String name, String cronExpression, String contextUrl,
                         boolean enabled, Instant lastFiredAt, Instant nextFireAt,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Schedule s) {
            return new Output(s.getId(), s.getName(), s.getCronExpression(), s.getContextUrl(),
                    s.isEnabled(), s.getLastFiredAt(), s.getNextFireAt(),
                    s.getCreatedAt(), s.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-044"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/DeleteScheduleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.ScheduleException;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application use case for permanently deleting a schedule by its identifier.
 *
 * <p>Verifies that the schedule exists before deletion to produce a clear domain error
 * when it does not.
 */
@Service
@RequiredArgsConstructor
public class DeleteScheduleUseCase {

    private final ScheduleRepository scheduleRepository;

    /**
     * Deletes the schedule with the given identifier.
     *
     * @param id the identifier of the schedule to delete
     * @throws com.matheusjfa.yggdrasil.domain.schedule.ScheduleException if no schedule with
     *         the given {@code id} exists
     */
    public void execute(String id) {
        if (!scheduleRepository.exists(id)) throw ScheduleException.notFound(id);
        scheduleRepository.delete(id);
    }
}
```

<a id="arquivo-045"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/ListSchedulesUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application use case for listing all configured schedules.
 *
 * <p>Returns every schedule regardless of enabled state, mapped to a flat
 * {@link Output} projection for API consumption.
 */
@Service
@RequiredArgsConstructor
public class ListSchedulesUseCase {

    private final ScheduleRepository scheduleRepository;

    /**
     * Returns all persisted schedules.
     *
     * @return a list of {@link Output} projections; never {@code null}, may be empty
     */
    public List<Output> execute() {
        return scheduleRepository.findAll().stream().map(Output::from).toList();
    }

    /**
     * Flat projection of a {@link Schedule} used as the API response payload.
     *
     * @param id             the schedule identifier
     * @param name           the schedule name
     * @param cronExpression the cron expression defining when it fires
     * @param contextUrl     the URL from which the evaluation context is fetched
     * @param enabled        whether the schedule is currently active
     * @param lastFiredAt    the instant of the last firing, or {@code null} if never fired
     * @param nextFireAt     the computed next fire instant
     * @param createdAt      the creation timestamp
     * @param updatedAt      the last-updated timestamp
     */
    public record Output(String id, String name, String cronExpression, String contextUrl,
                         boolean enabled, Instant lastFiredAt, Instant nextFireAt,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Schedule s) {
            return new Output(s.getId(), s.getName(), s.getCronExpression(), s.getContextUrl(),
                    s.isEnabled(), s.getLastFiredAt(), s.getNextFireAt(),
                    s.getCreatedAt(), s.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-046"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/ToggleScheduleStatusUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleException;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Application use case for enabling or disabling a schedule.
 *
 * <p>Enables or disables the schedule aggregate via its domain methods, persists the change,
 * and returns a lightweight status projection. This is intentionally separate from
 * {@link UpdateScheduleUseCase} to provide a focused, low-cost operation for toggling
 * activity without touching other fields.
 */
@Service
@RequiredArgsConstructor
public class ToggleScheduleStatusUseCase {

    private final ScheduleRepository scheduleRepository;

    /**
     * Sets the enabled state of the schedule with the given identifier.
     *
     * @param id      the identifier of the schedule to toggle
     * @param enabled {@code true} to enable the schedule, {@code false} to disable it
     * @return an {@link Output} projection containing the updated enabled state
     * @throws com.matheusjfa.yggdrasil.domain.schedule.ScheduleException if no schedule with
     *         the given {@code id} exists
     */
    public Output execute(String id, boolean enabled) {
        var schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> ScheduleException.notFound(id));
        if (enabled) schedule.enable(); else schedule.disable();
        return Output.from(scheduleRepository.save(schedule));
    }

    /**
     * Lightweight projection of a {@link Schedule} after a status toggle.
     *
     * @param id        the schedule identifier
     * @param name      the schedule name
     * @param enabled   the new enabled state
     * @param updatedAt the timestamp of the toggle operation
     */
    public record Output(String id, String name, boolean enabled, Instant updatedAt) {
        static Output from(Schedule s) {
            return new Output(s.getId(), s.getName(), s.isEnabled(), s.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-047"></a>

### `src/main/java/com/matheusjfa/yggdrasil/application/usecase/schedule/UpdateScheduleUseCase.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleException;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Application use case for updating an existing schedule's mutable fields.
 *
 * <p>Applies a partial update (only non-null fields are changed). When a new cron expression
 * is provided it is validated and the next fire instant is recomputed. The enabled state is
 * not changed by this use case — use {@link ToggleScheduleStatusUseCase} for that.
 */
@Service
@RequiredArgsConstructor
public class UpdateScheduleUseCase {

    private final ScheduleRepository scheduleRepository;

    /**
     * Applies the given input to the schedule identified by {@code id}.
     *
     * @param id    the identifier of the schedule to update
     * @param input the update parameters; any {@code null} field is ignored
     * @return an {@link Output} projection of the schedule after the update
     * @throws com.matheusjfa.yggdrasil.domain.schedule.ScheduleException if no schedule with
     *         the given {@code id} exists
     * @throws IllegalArgumentException if a new cron expression is provided but is not valid
     */
    public Output execute(String id, Input input) {
        var schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> ScheduleException.notFound(id));

        Instant nextFireAt = null;
        if (input.cronExpression() != null) {
            try {
                CronExpression.parse(input.cronExpression());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid cron expression: " + input.cronExpression(), e);
            }
            nextFireAt = CreateScheduleUseCase.computeNextFireAt(input.cronExpression());
        }

        schedule.update(input.name(), input.cronExpression(), input.contextUrl(), nextFireAt);
        return Output.from(scheduleRepository.save(schedule));
    }

    /**
     * Partial update parameters for the update-schedule use case.
     * Any field that is {@code null} will leave the corresponding schedule field unchanged.
     *
     * @param name            the new name, or {@code null} to keep the current name
     * @param cronExpression  the new cron expression, or {@code null} to keep the current one;
     *                        when provided, the next fire instant is recomputed automatically
     * @param contextUrl      the new context URL, or {@code null} to keep the current one
     */
    public record Input(String name, String cronExpression, String contextUrl) {}

    /**
     * Flat projection of a {@link Schedule} returned after an update.
     *
     * @param id             the schedule identifier
     * @param name           the schedule name after the update
     * @param cronExpression the cron expression after the update
     * @param contextUrl     the context URL after the update
     * @param enabled        the current enabled state (unchanged by this use case)
     * @param lastFiredAt    the last fire instant (unchanged)
     * @param nextFireAt     the recomputed next fire instant if the cron expression changed;
     *                       otherwise the previously stored value
     * @param createdAt      the original creation timestamp
     * @param updatedAt      the last-updated timestamp (reflects this update)
     */
    public record Output(String id, String name, String cronExpression, String contextUrl,
                         boolean enabled, Instant lastFiredAt, Instant nextFireAt,
                         Instant createdAt, Instant updatedAt) {
        static Output from(Schedule s) {
            return new Output(s.getId(), s.getName(), s.getCronExpression(), s.getContextUrl(),
                    s.isEnabled(), s.getLastFiredAt(), s.getNextFireAt(),
                    s.getCreatedAt(), s.getUpdatedAt());
        }
    }
}
```

<a id="arquivo-048"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/evaluation/EvaluationResult.java`

```java
package com.matheusjfa.yggdrasil.domain.evaluation;

import com.matheusjfa.yggdrasil.domain.execution.FiredRule;
import java.util.List;
import java.util.Map;

/**
 * Immutable value object representing the outcome of evaluating a set of rules against a context.
 *
 * <p>Encapsulates the list of rules that fired during evaluation and the original input context
 * so that callers can inspect both what matched and what data drove the evaluation.
 * Exists in the domain layer to provide a typed result contract between the evaluator and
 * its callers, avoiding raw collections.
 *
 * @param firedRules    the list of rules whose conditions were satisfied during evaluation;
 *                      stored as an immutable copy
 * @param inputContext  the key-value attributes that were supplied as input to the evaluation
 */
public record EvaluationResult(List<FiredRule> firedRules, Map<String, Object> inputContext) {
    public EvaluationResult {
        firedRules = List.copyOf(firedRules);
    }

    /**
     * Returns {@code true} if at least one rule fired during evaluation.
     *
     * @return {@code true} when {@code firedRules} is non-empty, {@code false} otherwise
     */
    public boolean hasMatches() { return !firedRules.isEmpty(); }
}
```

<a id="arquivo-049"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/evaluation/RuleContext.java`

```java
package com.matheusjfa.yggdrasil.domain.evaluation;

import java.util.HashMap;
import java.util.Map;

/**
 * Mutable container for the key-value attributes supplied to a rule evaluation.
 *
 * <p>Represents the runtime context that is tested against rule conditions. The context
 * can be enriched during chain evaluation — when a parent rule fires and propagates
 * computed values to child rules — without mutating the caller's original map.
 * Copying the context ({@link #copy()}) before enrichment isolates branch evaluation
 * from sibling branches.
 */
public class RuleContext {
    private final Map<String, Object> attributes;

    /**
     * Constructs a new context with a defensive copy of the supplied attributes.
     *
     * @param attributes the initial key-value pairs to populate this context;
     *                   must not be {@code null}
     */
    public RuleContext(Map<String, Object> attributes) {
        this.attributes = new HashMap<>(attributes);
    }

    /**
     * Returns an immutable snapshot of all attributes currently in this context.
     *
     * @return an unmodifiable map of all key-value pairs
     */
    public Map<String, Object> getAttributes() { return Map.copyOf(attributes); }

    /**
     * Retrieves a single attribute value by its key.
     *
     * @param key the attribute name to look up
     * @return the value associated with the key, or {@code null} if absent
     */
    public Object get(String key) { return attributes.get(key); }

    /**
     * Creates a shallow copy of this context, useful for branch-isolated enrichment
     * during chain evaluation.
     *
     * @return a new {@code RuleContext} with the same attribute entries
     */
    public RuleContext copy() {
        return new RuleContext(new HashMap<>(attributes));
    }

    /**
     * Adds or replaces an attribute in this context.
     *
     * @param key   the attribute name to set
     * @param value the value to associate with the key
     */
    public void put(String key, Object value) {
        this.attributes.put(key, value);
    }
}
```

<a id="arquivo-050"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/evaluation/RuleEvaluator.java`

```java
package com.matheusjfa.yggdrasil.domain.evaluation;

import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;

/**
 * Domain port for evaluating rule conditions and arbitrary expressions against a context.
 *
 * <p>Defined as an interface so that the domain layer declares the contract without
 * depending on any specific evaluation technology (e.g. Spring Expression Language).
 * Implementations live in the infrastructure layer and are injected via dependency
 * inversion, keeping the domain free of framework concerns.
 */
public interface RuleEvaluator {

    /**
     * Evaluates a condition tree against the given context.
     *
     * @param condition the root of the condition tree to evaluate; may be a
     *                  {@code ConditionLeaf}, {@code ConditionGroup}, or {@code SpelCondition}
     * @param context   the runtime attributes against which the condition is tested
     * @return {@code true} if the condition is satisfied by the context, {@code false} otherwise
     */
    boolean evaluate(ConditionNode condition, RuleContext context);

    /**
     * Evaluates an expression string against the given context and returns the computed value.
     *
     * <p>Used primarily during chain enrichment to compute values that are injected into
     * a child rule's context before evaluation.
     *
     * @param expression the expression to evaluate (e.g. a SpEL expression)
     * @param context    the runtime attributes available as variables during expression evaluation
     * @return the result of evaluating the expression; may be any type
     */
    Object evaluateExpression(String expression, RuleContext context);
}
```

<a id="arquivo-051"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/execution/ChainStep.java`

```java
package com.matheusjfa.yggdrasil.domain.execution;

import java.util.Map;

/**
 * Immutable record representing a single step in a rule-chain evaluation trace.
 *
 * <p>Captures whether the transition from a source rule to a target rule was executed,
 * along with any enrichments that were computed and injected into the context before
 * evaluating the target rule's condition. Stored as part of a {@link RuleExecution}
 * to enable full replay and debugging of chained rule evaluation paths.
 *
 * @param sourceRuleId       the identifier of the rule that triggered this chain transition
 * @param targetRuleId       the identifier of the rule that was the target of the chain
 * @param appliedEnrichments the key-value pairs computed from the chain's enrichment expressions
 *                           and injected into the context before evaluating the target rule
 * @param executed           {@code true} if the target rule's condition was satisfied and it fired;
 *                           {@code false} if it was skipped
 * @param skipReason         human-readable explanation for why the step was not executed;
 *                           {@code null} when {@code executed} is {@code true}
 */
public record ChainStep(
        String sourceRuleId,
        String targetRuleId,
        Map<String, Object> appliedEnrichments,
        boolean executed,
        String skipReason
) {}
```

<a id="arquivo-052"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/execution/ExecutionId.java`

```java
package com.matheusjfa.yggdrasil.domain.execution;

import com.matheusjfa.yggdrasil.domain.shared.Identifier;
import java.util.UUID;

/**
 * Strongly-typed identifier for the {@link RuleExecution} aggregate root.
 *
 * <p>Wraps a UUID string to prevent accidental assignment of a {@link RuleExecution}
 * identifier where a rule or schedule identifier is expected. Supports both random
 * generation and reconstitution from a persisted string value.
 */
public final class ExecutionId extends Identifier<String> {
    private final String value;

    private ExecutionId(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("ExecutionId must not be null or blank");
        this.value = value;
    }

    /**
     * Creates a new {@code ExecutionId} with a randomly generated UUID value.
     *
     * @return a new, unique {@code ExecutionId}
     */
    public static ExecutionId generate() { return new ExecutionId(UUID.randomUUID().toString()); }

    /**
     * Reconstitutes an {@code ExecutionId} from a previously persisted string value.
     *
     * @param value the string representation of the identifier; must not be blank
     * @return an {@code ExecutionId} wrapping the provided value
     * @throws IllegalArgumentException if {@code value} is {@code null} or blank
     */
    public static ExecutionId of(String value) { return new ExecutionId(value); }

    /**
     * Returns the underlying UUID string of this identifier.
     *
     * @return the string value
     */
    @Override
    public String getValue() { return value; }

    /**
     * Returns the string representation of this identifier (same as {@link #getValue()}).
     *
     * @return the string value
     */
    @Override
    public String toString() { return value; }
}
```

<a id="arquivo-053"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/execution/ExecutionOrigin.java`

```java
package com.matheusjfa.yggdrasil.domain.execution;

/**
 * Enumeration of the possible origins that can trigger a rule evaluation execution.
 *
 * <p>Stored on each {@link RuleExecution} to allow filtering, auditing, and metrics
 * segmentation by how the evaluation was initiated.
 */
public enum ExecutionOrigin {

    /** The evaluation was requested synchronously by a direct API call. */
    SYNC,

    /** The evaluation was dispatched asynchronously and processed in the background. */
    ASYNC,

    /** The evaluation was triggered automatically by a cron-based schedule. */
    SCHEDULED,

    /** The evaluation re-runs a previously recorded execution using its original input context. */
    REPLAY
}
```

<a id="arquivo-054"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/execution/FiredRule.java`

```java
package com.matheusjfa.yggdrasil.domain.execution;

import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import java.util.List;

/**
 * Immutable record representing a rule that matched and fired during an evaluation.
 *
 * <p>Acts as a snapshot of the rule's identity and its actions at the moment of firing,
 * stored inside a {@link RuleExecution} to preserve the full result of an evaluation
 * even if the rule is later modified or deleted.
 *
 * @param ruleId    the unique identifier of the rule that fired
 * @param ruleName  the human-readable name of the rule at the time of firing
 * @param actions   the list of actions associated with the rule; stored as an immutable copy
 */
public record FiredRule(String ruleId, String ruleName, List<RuleAction> actions) {
    public FiredRule {
        actions = List.copyOf(actions);
    }
}
```

<a id="arquivo-055"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/execution/RuleExecution.java`

```java
package com.matheusjfa.yggdrasil.domain.execution;

import com.matheusjfa.yggdrasil.domain.shared.Entity;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Aggregate root representing a single recorded rule evaluation.
 *
 * <p>Persists an immutable audit trail of every evaluation: the input context that was
 * supplied, which rules fired, how chain steps were traversed, and how the evaluation
 * was triggered. Immutability is intentional — executions are never updated after they
 * are recorded, making them safe for audit and replay scenarios.
 */
public class RuleExecution extends Entity<ExecutionId> {
    private final ExecutionId id;
    private final Map<String, Object> inputContext;
    private final List<FiredRule> firedRules;
    private final List<ChainStep> chainTrace;
    private final ExecutionOrigin origin;
    private final Instant executedAt;

    private RuleExecution(ExecutionId id, Map<String, Object> inputContext,
                          List<FiredRule> firedRules, List<ChainStep> chainTrace,
                          ExecutionOrigin origin, Instant executedAt) {
        this.id = id;
        this.inputContext = Map.copyOf(inputContext);
        this.firedRules = List.copyOf(firedRules);
        this.chainTrace = List.copyOf(chainTrace != null ? chainTrace : List.of());
        this.origin = origin;
        this.executedAt = executedAt;
    }

    /**
     * Creates and time-stamps a new execution record at the current instant.
     *
     * @param context     the key-value attributes that were evaluated against the rule forest
     * @param firedRules  the rules whose conditions were satisfied during this evaluation
     * @param chainTrace  the ordered list of chain steps traversed during evaluation;
     *                    may be {@code null} (treated as empty)
     * @param origin      how this evaluation was triggered
     * @return a new {@code RuleExecution} with a generated identifier and the current timestamp
     */
    public static RuleExecution record(Map<String, Object> context,
                                        List<FiredRule> firedRules,
                                        List<ChainStep> chainTrace,
                                        ExecutionOrigin origin) {
        return new RuleExecution(ExecutionId.generate(), context, firedRules, chainTrace, origin, Instant.now());
    }

    /**
     * Reconstitutes an existing execution from persisted data without generating a new identity.
     *
     * @param id          the previously persisted execution identifier
     * @param context     the input attributes used in the original evaluation
     * @param firedRules  the rules that fired during the original evaluation
     * @param chainTrace  the chain steps recorded during the original evaluation
     * @param origin      how the original evaluation was triggered
     * @param executedAt  the exact instant the original evaluation was performed
     * @return a {@code RuleExecution} that mirrors the persisted state
     */
    public static RuleExecution reconstitute(String id, Map<String, Object> context,
                                              List<FiredRule> firedRules,
                                              List<ChainStep> chainTrace,
                                              ExecutionOrigin origin,
                                              Instant executedAt) {
        return new RuleExecution(ExecutionId.of(id), context, firedRules, chainTrace, origin, executedAt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionId getIdentity() { return id; }

    /**
     * Returns the string value of this execution's identifier.
     *
     * @return the UUID string of the execution
     */
    public String getId() { return id.getValue(); }

    /**
     * Returns the input context that was evaluated.
     *
     * @return an unmodifiable map of the key-value attributes submitted for evaluation
     */
    public Map<String, Object> getInputContext() { return inputContext; }

    /**
     * Returns the rules that fired during this execution.
     *
     * @return an unmodifiable list of {@link FiredRule} instances
     */
    public List<FiredRule> getFiredRules() { return firedRules; }

    /**
     * Returns the ordered chain-step trace recorded during this execution.
     *
     * @return an unmodifiable list of {@link ChainStep} instances; may be empty
     */
    public List<ChainStep> getChainTrace() { return chainTrace; }

    /**
     * Returns how this execution was triggered.
     *
     * @return the origin of this evaluation
     */
    public ExecutionOrigin getOrigin() { return origin; }

    /**
     * Returns the instant at which this execution was recorded.
     *
     * @return the UTC timestamp of the evaluation
     */
    public Instant getExecutedAt() { return executedAt; }
}
```

<a id="arquivo-056"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/execution/RuleExecutionRepository.java`

```java
package com.matheusjfa.yggdrasil.domain.execution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Domain port (output port) for persisting and querying {@link RuleExecution} records.
 *
 * <p>Defined as an interface so the domain layer declares what it needs without
 * depending on any specific persistence technology. The infrastructure layer provides
 * the concrete implementation, following the Dependency Inversion Principle.
 */
public interface RuleExecutionRepository {

    /**
     * Persists a new execution record.
     *
     * @param execution the execution to store; must not be {@code null}
     * @return the saved execution, potentially with infrastructure-enriched state
     */
    RuleExecution save(RuleExecution execution);

    /**
     * Looks up a single execution by its unique identifier.
     *
     * @param id the string representation of the {@link ExecutionId} to search for
     * @return an {@link Optional} containing the execution if found, or empty if not
     */
    Optional<RuleExecution> findById(String id);

    /**
     * Returns a paginated list of all executions ordered from most to least recent.
     *
     * @param pageable pagination and sorting parameters (sorting by {@code executedAt} desc
     *                 is enforced by the implementation)
     * @return a page of executions
     */
    Page<RuleExecution> findAllOrderByExecutedAtDesc(Pageable pageable);

    /**
     * Returns the total number of persisted executions.
     *
     * @return the count of all execution records
     */
    long countAll();

    /**
     * Returns how many times a specific rule has fired across all recorded executions.
     *
     * @param ruleId the identifier of the rule whose fire count is requested
     * @return the number of executions in which the given rule fired
     */
    long countFiredByRuleId(String ruleId);
}
```

<a id="arquivo-057"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/ChainConfiguration.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

import java.util.Map;

/**
 * Immutable value object describing how a rule chains to a downstream rule.
 *
 * <p>When a rule fires, each {@code ChainConfiguration} on it specifies which target rule
 * should be evaluated next and what enrichment expressions should be computed from the
 * current context and injected as extra attributes before the target's condition is tested.
 * Enrichment values are SpEL expression strings that reference the parent context.
 *
 * @param targetRuleId  the identifier of the downstream rule that should be evaluated
 *                      when this rule fires
 * @param enrichments   a map where each key is the attribute name to inject into the child
 *                      context and each value is a SpEL expression evaluated against the
 *                      parent context to produce that attribute's value;
 *                      stored as an immutable copy
 */
public record ChainConfiguration(String targetRuleId, Map<String, String> enrichments) {
    public ChainConfiguration {
        enrichments = Map.copyOf(enrichments);
    }
}
```

<a id="arquivo-058"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/Rule.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import com.matheusjfa.yggdrasil.domain.shared.AggregateRoot;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate root representing a business rule in the domain layer.
 *
 * <p>A {@code Rule} encapsulates a condition tree that is evaluated against a context,
 * a list of actions to execute when the condition is met, optional chaining configuration
 * to trigger other rules in sequence, and lifecycle status. All mutation is done via
 * explicit domain methods to enforce invariants and keep timestamps consistent.
 */
public class Rule extends AggregateRoot<RuleId> {
    private final RuleId id;
    private String name;
    private String description;
    private int priority;
    private ConditionNode condition;
    private List<RuleAction> actions;
    private List<ChainConfiguration> chains;
    private RuleStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Rule(RuleId id, String name, String description, int priority,
                 ConditionNode condition, List<RuleAction> actions, List<ChainConfiguration> chains,
                 RuleStatus status, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.description = description != null ? description : "";
        this.priority = priority;
        this.condition = Objects.requireNonNull(condition);
        this.actions = List.copyOf(Objects.requireNonNull(actions));
        this.chains = List.copyOf(chains != null ? chains : List.of());
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    /**
     * Creates a new {@code Rule} with a generated identifier and an initial status of
     * {@link RuleStatus#ACTIVE}.
     *
     * @param name        the human-readable name of the rule; must not be {@code null}
     * @param description an optional explanation of the rule's purpose; {@code null} is treated as empty
     * @param priority    the evaluation priority — lower values are evaluated first
     * @param condition   the root of the condition tree that must be satisfied for the rule to fire;
     *                    must not be {@code null}
     * @param actions     the ordered list of actions to execute when the condition is met;
     *                    must not be {@code null}
     * @param chains      the list of chaining configurations that define downstream rules;
     *                    may be {@code null} (treated as empty)
     * @return a new active {@code Rule} with {@code createdAt} and {@code updatedAt} set to now
     */
    public static Rule create(String name, String description, int priority,
                               ConditionNode condition, List<RuleAction> actions,
                               List<ChainConfiguration> chains) {
        var now = Instant.now();
        return new Rule(RuleId.generate(), name, description, priority,
                condition, actions, chains, RuleStatus.ACTIVE, now, now);
    }

    /**
     * Reconstitutes a {@code Rule} from previously persisted data without generating
     * a new identifier or resetting timestamps.
     *
     * @param id          the persisted identifier string
     * @param name        the rule's name as stored
     * @param description the rule's description as stored
     * @param priority    the rule's evaluation priority as stored
     * @param condition   the persisted condition tree
     * @param actions     the persisted list of actions
     * @param chains      the persisted chain configurations
     * @param status      the rule's current lifecycle status
     * @param createdAt   the instant the rule was originally created
     * @param updatedAt   the instant of the last modification
     * @return a {@code Rule} that mirrors the persisted state
     */
    public static Rule reconstitute(String id, String name, String description, int priority,
                                     ConditionNode condition, List<RuleAction> actions,
                                     List<ChainConfiguration> chains, RuleStatus status,
                                     Instant createdAt, Instant updatedAt) {
        return new Rule(RuleId.of(id), name, description, priority,
                condition, actions, chains, status, createdAt, updatedAt);
    }

    /**
     * Applies a partial update to this rule, ignoring any parameter that is {@code null}.
     *
     * @param name        the new name, or {@code null} to keep the current name
     * @param description the new description, or {@code null} to keep the current description
     * @param priority    the new priority, or {@code null} to keep the current priority
     * @param condition   the new condition tree, or {@code null} to keep the current condition
     * @param actions     the new list of actions, or {@code null} to keep the current list
     * @param chains      the new chain configurations, or {@code null} to keep the current chains
     */
    public void update(String name, String description, Integer priority,
                       ConditionNode condition, List<RuleAction> actions,
                       List<ChainConfiguration> chains) {
        if (name != null) this.name = name;
        if (description != null) this.description = description;
        if (priority != null) this.priority = priority;
        if (condition != null) this.condition = condition;
        if (actions != null) this.actions = List.copyOf(actions);
        if (chains != null) this.chains = List.copyOf(chains);
        this.updatedAt = Instant.now();
    }

    /**
     * Sets the rule's status to {@link RuleStatus#ACTIVE} and updates the modification timestamp.
     */
    public void activate() {
        this.status = RuleStatus.ACTIVE;
        this.updatedAt = Instant.now();
    }

    /**
     * Sets the rule's status to {@link RuleStatus#INACTIVE} and updates the modification timestamp.
     */
    public void deactivate() {
        this.status = RuleStatus.INACTIVE;
        this.updatedAt = Instant.now();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleId getIdentity() { return id; }

    /**
     * Returns the string value of this rule's identifier.
     *
     * @return the UUID string of the rule
     */
    public String getId() { return id.getValue(); }

    /**
     * Returns the human-readable name of this rule.
     *
     * @return the rule name
     */
    public String getName() { return name; }

    /**
     * Returns the description of this rule.
     *
     * @return the description string; never {@code null}, may be empty
     */
    public String getDescription() { return description; }

    /**
     * Returns the evaluation priority of this rule.
     *
     * @return the priority value; lower means evaluated earlier
     */
    public int getPriority() { return priority; }

    /**
     * Returns the root of the condition tree for this rule.
     *
     * @return the condition node; never {@code null}
     */
    public ConditionNode getCondition() { return condition; }

    /**
     * Returns the actions associated with this rule.
     *
     * @return an unmodifiable list of {@link RuleAction}s
     */
    public List<RuleAction> getActions() { return actions; }

    /**
     * Returns the chain configurations that link this rule to downstream rules.
     *
     * @return an unmodifiable list of {@link ChainConfiguration}s; may be empty
     */
    public List<ChainConfiguration> getChains() { return chains; }

    /**
     * Returns the current lifecycle status of this rule.
     *
     * @return {@link RuleStatus#ACTIVE} or {@link RuleStatus#INACTIVE}
     */
    public RuleStatus getStatus() { return status; }

    /**
     * Returns the instant at which this rule was created.
     *
     * @return the creation timestamp; never {@code null}
     */
    public Instant getCreatedAt() { return createdAt; }

    /**
     * Returns the instant of the most recent modification to this rule.
     *
     * @return the last-updated timestamp; never {@code null}
     */
    public Instant getUpdatedAt() { return updatedAt; }
}
```

<a id="arquivo-059"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleAction.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

import java.util.Map;

/**
 * Immutable value object representing an action to be executed when a rule fires.
 *
 * <p>An action is intentionally generic: the {@code type} field identifies the action
 * kind (e.g. {@code "SEND_EMAIL"}, {@code "SET_VARIABLE"}), while {@code payload}
 * carries the action-specific parameters. This design lets the domain remain decoupled
 * from the mechanics of action execution, which belongs to the application or
 * infrastructure layer.
 *
 * @param type    the discriminator string that identifies the action kind
 * @param payload the key-value parameters that configure this specific action instance
 */
public record RuleAction(String type, Map<String, Object> payload) {}
```

<a id="arquivo-060"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleEdge.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

/**
 * Immutable record representing a directed edge in the rule forest graph.
 *
 * <p>Links a parent {@link RuleNode} to a child {@link RuleNode} via a
 * {@link ChainConfiguration}, carrying both the enrichment definitions and the
 * resolved child node reference. Used during forest traversal to follow chain
 * relationships from a fired parent rule to its potential downstream targets.
 *
 * @param chain  the chain configuration that defines the enrichments and identifies
 *               the target rule
 * @param child  the resolved node in the forest that corresponds to the target rule
 */
public record RuleEdge(ChainConfiguration chain, RuleNode child) {}
```

<a id="arquivo-061"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleException.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

/**
 * Domain exception for rule-related error conditions.
 *
 * <p>Provides named factory methods for the common failure scenarios so that error
 * messages are consistent throughout the application and callers avoid constructing
 * raw exceptions with ad-hoc messages.
 */
public class RuleException extends RuntimeException {
    private RuleException(String message) { super(message); }

    /**
     * Creates an exception indicating that no rule with the given identifier exists.
     *
     * @param id the identifier that was looked up but not found
     * @return a new {@code RuleException} with a descriptive message
     */
    public static RuleException notFound(String id) {
        return new RuleException("Rule not found: " + id);
    }

    /**
     * Creates an exception indicating that a rule with the given identifier already exists,
     * violating a uniqueness constraint.
     *
     * @param id the identifier that conflicts with an existing rule
     * @return a new {@code RuleException} with a descriptive message
     */
    public static RuleException conflict(String id) {
        return new RuleException("Rule already exists: " + id);
    }
}
```

<a id="arquivo-062"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleId.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

import com.matheusjfa.yggdrasil.domain.shared.Identifier;
import java.util.UUID;

/**
 * Strongly-typed identifier for the {@link Rule} aggregate root.
 *
 * <p>Wraps a UUID string to prevent accidental mixing of rule identifiers with identifiers
 * from other aggregates at compile time. Supports both random generation (for new rules)
 * and reconstitution from a persisted value (for existing rules).
 */
public final class RuleId extends Identifier<String> {
    private final String value;

    private RuleId(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("RuleId must not be null or blank");
        this.value = value;
    }

    /**
     * Creates a new {@code RuleId} backed by a randomly generated UUID.
     *
     * @return a new, unique {@code RuleId}
     */
    public static RuleId generate() { return new RuleId(UUID.randomUUID().toString()); }

    /**
     * Reconstitutes a {@code RuleId} from a previously persisted string value.
     *
     * @param value the string representation of the identifier; must not be blank
     * @return a {@code RuleId} wrapping the provided value
     * @throws IllegalArgumentException if {@code value} is {@code null} or blank
     */
    public static RuleId of(String value) { return new RuleId(value); }

    /**
     * Returns the underlying UUID string of this identifier.
     *
     * @return the string value
     */
    @Override
    public String getValue() { return value; }

    /**
     * Returns the string representation of this identifier (same as {@link #getValue()}).
     *
     * @return the string value
     */
    @Override
    public String toString() { return value; }
}
```

<a id="arquivo-063"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleNode.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In-memory graph node that wraps a {@link Rule} and its outgoing chain edges.
 *
 * <p>Used by the rule forest to represent the tree structure of rules at runtime.
 * Each node holds a reference to its domain rule and a mutable list of {@link RuleEdge}s
 * that point to child nodes, enabling depth-first traversal during evaluation.
 * Nodes are constructed transiently (not persisted) when the forest is rebuilt from the
 * repository.
 */
public class RuleNode {

    private final Rule rule;
    private final List<RuleEdge> edges = new ArrayList<>();

    /**
     * Constructs a node wrapping the given rule with no outgoing edges.
     *
     * @param rule the domain rule this node represents; must not be {@code null}
     */
    public RuleNode(Rule rule) {
        this.rule = rule;
    }

    /**
     * Adds a directed edge from this node to a child node via the given chain configuration.
     *
     * @param chain the chain configuration describing enrichments and the target rule identifier
     * @param child the node in the forest that corresponds to the target rule
     */
    public void addEdge(ChainConfiguration chain, RuleNode child) {
        edges.add(new RuleEdge(chain, child));
    }

    /**
     * Returns the domain rule wrapped by this node.
     *
     * @return the rule; never {@code null}
     */
    public Rule getRule() { return rule; }

    /**
     * Returns an unmodifiable view of the outgoing edges from this node.
     *
     * @return the list of edges to child nodes; may be empty
     */
    public List<RuleEdge> getEdges() { return Collections.unmodifiableList(edges); }
}
```

<a id="arquivo-064"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleRepository.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

import java.util.List;
import java.util.Optional;

/**
 * Domain port (output port) for persisting and querying {@link Rule} aggregates.
 *
 * <p>Defined as an interface so the domain layer declares what it needs without
 * depending on any specific persistence technology. The infrastructure layer supplies
 * the concrete implementation, following the Dependency Inversion Principle.
 */
public interface RuleRepository {

    /**
     * Looks up a single rule by its unique identifier.
     *
     * @param id the string representation of the {@link RuleId} to search for
     * @return an {@link Optional} containing the rule if found, or empty if not
     */
    Optional<Rule> findById(String id);

    /**
     * Returns all rules regardless of status.
     *
     * @return a list of all persisted rules; never {@code null}, may be empty
     */
    List<Rule> findAll();

    /**
     * Returns all rules whose names contain the given substring (case behaviour is
     * implementation-defined).
     *
     * @param name the substring to search for within rule names
     * @return a list of matching rules; never {@code null}, may be empty
     */
    List<Rule> findAllByNameContaining(String name);

    /**
     * Returns all rules with a specific lifecycle status.
     *
     * @param status the {@link RuleStatus} to filter by
     * @return a list of rules with the given status; never {@code null}, may be empty
     */
    List<Rule> findAllByStatus(RuleStatus status);

    /**
     * Returns all rules whose status is {@link RuleStatus#ACTIVE}.
     *
     * <p>Convenience shorthand used when building the rule forest for evaluation.
     *
     * @return a list of active rules; never {@code null}, may be empty
     */
    List<Rule> findAllActive();

    /**
     * Persists a new or updated rule.
     *
     * @param rule the rule to store; must not be {@code null}
     * @return the saved rule, potentially with infrastructure-enriched state
     */
    Rule save(Rule rule);

    /**
     * Removes the rule with the given identifier from the persistent store.
     *
     * @param id the string representation of the identifier of the rule to delete
     */
    void delete(String id);

    /**
     * Checks whether a rule with the given identifier exists in the persistent store.
     *
     * @param id the identifier to check
     * @return {@code true} if a rule with this identifier exists, {@code false} otherwise
     */
    boolean exists(String id);
}
```

<a id="arquivo-065"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/RuleStatus.java`

```java
package com.matheusjfa.yggdrasil.domain.rule;

/**
 * Enumeration of the possible lifecycle statuses of a {@link Rule}.
 *
 * <p>Controls whether a rule participates in evaluation: only {@code ACTIVE} rules
 * are included when the rule forest is built.
 */
public enum RuleStatus {

    /** The rule is enabled and will be evaluated against incoming contexts. */
    ACTIVE,

    /** The rule is disabled and will be excluded from all evaluations. */
    INACTIVE
}
```

<a id="arquivo-066"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ComparisonOperator.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.condition;

/**
 * Enumeration of comparison operators available when defining a {@link ConditionLeaf}.
 *
 * <p>Each constant describes how the context attribute value identified by
 * {@code ConditionLeaf#field} should be compared to the expected {@code value}
 * during rule evaluation.
 */
public enum ComparisonOperator {

    /** Tests that the field value equals the expected value. */
    EQUAL,

    /** Tests that the field value does not equal the expected value. */
    NOT_EQUAL,

    /** Tests that the field value is strictly greater than the expected value. */
    GREATER_THAN,

    /** Tests that the field value is greater than or equal to the expected value. */
    GREATER_THAN_OR_EQUAL,

    /** Tests that the field value is strictly less than the expected value. */
    LESS_THAN,

    /** Tests that the field value is less than or equal to the expected value. */
    LESS_THAN_OR_EQUAL,

    /** Tests that the field string or collection contains the expected value. */
    CONTAINS,

    /** Tests that the field string starts with the expected prefix. */
    STARTS_WITH,

    /** Tests that the field value is {@code null}. */
    IS_NULL,

    /** Tests that the field value is not {@code null}. */
    IS_NOT_NULL,

    /** Tests that the field value is present in the expected collection. */
    IN,

    /** Tests that the field value is absent from the expected collection. */
    NOT_IN,

    /** Tests that the field value falls within a range defined by two boundary values. */
    IS_BETWEEN,

    /** Tests that the field date/time value falls within the previous calendar month. */
    LAST_MONTH,

    /** Tests that the field date/time value falls within the last N days where N is the expected value. */
    LAST_N_DAYS,

    /** Tests that the field string ends with the expected suffix. */
    ENDS_WITH,

    /** Tests that the field string matches the expected regular expression pattern. */
    MATCHES,

    /** Tests that the field string contains the expected substring, ignoring character case. */
    CONTAINS_IGNORE_CASE,

    /** Tests that the expected value is a member of the field collection. */
    MEMBER_OF,

    /** Tests that the field collection contains all elements of the expected collection. */
    CONTAINS_ALL,

    /** Tests that the field collection contains at least one element of the expected collection. */
    CONTAINS_ANY,

    /** Tests that the field string or collection is empty. */
    IS_EMPTY,

    /** Tests that the field string or collection is not empty. */
    IS_NOT_EMPTY,

    /** Tests that the field date/time value is before the expected date/time value. */
    BEFORE,

    /** Tests that the field date/time value is after the expected date/time value. */
    AFTER
}
```

<a id="arquivo-067"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ConditionGroup.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.condition;

import java.util.List;
import java.util.Objects;

/**
 * Composite {@link ConditionNode} that combines child nodes using a logical operator.
 *
 * <p>Acts as an interior node in the condition tree: its evaluation result is
 * determined by applying the {@link LogicalOperator} (AND/OR) across all child nodes.
 * An empty children list evaluates according to the evaluator's identity element
 * convention (typically {@code true} for AND, {@code false} for OR).
 *
 * @param operator  the logical operator used to combine the results of all child nodes;
 *                  must not be {@code null}
 * @param children  the child condition nodes to combine; {@code null} is treated as
 *                  an empty list and the result is stored as an immutable copy
 */
public record ConditionGroup(LogicalOperator operator, List<ConditionNode> children)
        implements ConditionNode {
    public ConditionGroup {
        Objects.requireNonNull(operator, "operator must not be null");
        children = (children == null) ? List.of() : List.copyOf(children);
    }
}
```

<a id="arquivo-068"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ConditionLeaf.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.condition;

import java.util.Objects;

/**
 * Terminal {@link ConditionNode} that compares a single context attribute to an expected value.
 *
 * <p>Acts as a leaf node in the condition tree. During evaluation the {@link RuleEvaluator}
 * resolves the {@code field} name against the runtime {@link RuleContext}, then applies
 * the {@code operator} to compare the resolved value with {@code value}.
 *
 * @param field     the dot-notation path or attribute name to extract from the evaluation context;
 *                  must not be {@code null}
 * @param operator  the comparison strategy used to test the field value; must not be {@code null}
 * @param value     the expected value to compare against; may be {@code null} for operators such
 *                  as {@link ComparisonOperator#IS_NULL} and {@link ComparisonOperator#IS_NOT_NULL}
 */
public record ConditionLeaf(String field, ComparisonOperator operator, Object value)
        implements ConditionNode {
    public ConditionLeaf {
        Objects.requireNonNull(field, "field must not be null");
        Objects.requireNonNull(operator, "operator must not be null");
    }
}
```

<a id="arquivo-069"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/ConditionNode.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.condition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed domain interface representing a node in a rule's condition tree.
 *
 * <p>A condition tree is a composite structure where:
 * <ul>
 *   <li>{@link ConditionGroup} is an interior node that combines child nodes with
 *       a {@link LogicalOperator} (AND/OR);</li>
 *   <li>{@link ConditionLeaf} is a terminal node that compares a single context
 *       attribute to an expected value using a {@link ComparisonOperator};</li>
 *   <li>{@link SpelCondition} is a terminal node that evaluates an arbitrary
 *       Spring Expression Language expression.</li>
 * </ul>
 *
 * <p>The sealed permit list and Jackson polymorphic annotations ensure that only the
 * known subtypes are serialised/deserialised, preventing unintended extensions.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ConditionGroup.class, name = "GROUP"),
    @JsonSubTypes.Type(value = ConditionLeaf.class, name = "LEAF"),
    @JsonSubTypes.Type(value = SpelCondition.class, name = "SPEL")
})
public sealed interface ConditionNode permits ConditionGroup, ConditionLeaf, SpelCondition {}
```

<a id="arquivo-070"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/LogicalOperator.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.condition;

/**
 * Enumeration of logical operators used to combine child nodes in a {@link ConditionGroup}.
 */
public enum LogicalOperator {

    /** All child condition nodes must evaluate to {@code true} for the group to pass. */
    AND,

    /** At least one child condition node must evaluate to {@code true} for the group to pass. */
    OR
}
```

<a id="arquivo-071"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/condition/SpelCondition.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.condition;

import java.util.Objects;

/**
 * Terminal {@link ConditionNode} that evaluates an arbitrary Spring Expression Language (SpEL) expression.
 *
 * <p>Provides an escape hatch for conditions that cannot be expressed as a simple
 * {@link ConditionLeaf}. The expression has full access to the evaluation context's
 * attributes as root variables. Because SpEL expressions are evaluated dynamically,
 * they are more powerful but also harder to validate statically — prefer
 * {@link ConditionLeaf} for common comparisons.
 *
 * @param expression the SpEL expression string to evaluate against the runtime context;
 *                   must not be {@code null}
 */
public record SpelCondition(String expression) implements ConditionNode {
    public SpelCondition {
        Objects.requireNonNull(expression, "expression must not be null");
    }
}
```

<a id="arquivo-072"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/version/ChangeType.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.version;

/**
 * Enumeration of the operations that cause a new {@link RuleVersion} snapshot to be recorded.
 *
 * <p>Each constant describes the nature of the change that triggered the version, enabling
 * audit trail queries to filter or display history by operation type.
 */
public enum ChangeType {

    /** A brand new rule was created for the first time. */
    CREATE,

    /** One or more fields of an existing rule were updated. */
    UPDATE,

    /** The rule's {@link com.matheusjfa.yggdrasil.domain.rule.RuleStatus} was toggled. */
    STATUS_CHANGE,

    /** The rule was rolled back to a previous version snapshot. */
    RESTORE
}
```

<a id="arquivo-073"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/version/RuleVersion.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.version;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Immutable snapshot of a {@link Rule} at a specific point in time, used for versioning and audit.
 *
 * <p>Every time a rule is created, updated, has its status changed, or is restored,
 * a new {@code RuleVersion} is recorded. Because rules are mutable aggregates, versioning
 * is implemented as a separate, append-only domain object rather than by annotating the
 * aggregate itself. This keeps the audit history stable even as the live rule evolves.
 */
public class RuleVersion {
    private final String id;
    private final String ruleId;
    private final int version;
    private final String name;
    private final String description;
    private final int priority;
    private final ConditionNode condition;
    private final List<RuleAction> actions;
    private final List<ChainConfiguration> chains;
    private final RuleStatus status;
    private final Instant changedAt;
    private final ChangeType changeType;
    private final String changedBy;

    private RuleVersion(String id, String ruleId, int version, String name, String description,
                        int priority, ConditionNode condition, List<RuleAction> actions,
                        List<ChainConfiguration> chains, RuleStatus status,
                        Instant changedAt, ChangeType changeType, String changedBy) {
        this.id = id;
        this.ruleId = ruleId;
        this.version = version;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.condition = condition;
        this.actions = List.copyOf(actions);
        this.chains = List.copyOf(chains);
        this.status = status;
        this.changedAt = changedAt;
        this.changeType = changeType;
        this.changedBy = changedBy;
    }

    /**
     * Creates a new version snapshot from a {@link Rule} aggregate's current state.
     *
     * @param rule        the rule to snapshot; field values are copied at call time
     * @param version     the sequential version number assigned to this snapshot
     * @param changeType  the type of operation that caused this snapshot to be taken
     * @param changedBy   the identifier of the user or system that performed the change
     * @return a new {@code RuleVersion} with a generated identifier and the current timestamp
     */
    public static RuleVersion of(Rule rule, int version, ChangeType changeType, String changedBy) {
        return new RuleVersion(
                UUID.randomUUID().toString(),
                rule.getId(),
                version,
                rule.getName(),
                rule.getDescription(),
                rule.getPriority(),
                rule.getCondition(),
                rule.getActions(),
                rule.getChains(),
                rule.getStatus(),
                Instant.now(),
                changeType,
                changedBy
        );
    }

    /**
     * Reconstitutes a {@code RuleVersion} from previously persisted data.
     *
     * @param id          the persisted identifier of the version record
     * @param ruleId      the identifier of the rule this version belongs to
     * @param version     the sequential version number
     * @param name        the rule name at the time of the snapshot
     * @param description the rule description at the time of the snapshot
     * @param priority    the rule priority at the time of the snapshot
     * @param condition   the condition tree at the time of the snapshot
     * @param actions     the actions at the time of the snapshot
     * @param chains      the chain configurations at the time of the snapshot
     * @param status      the rule status at the time of the snapshot
     * @param changedAt   the instant the snapshot was taken
     * @param changeType  the operation that triggered the snapshot
     * @param changedBy   the identifier of the author of the change
     * @return a {@code RuleVersion} mirroring the persisted state
     */
    public static RuleVersion reconstitute(String id, String ruleId, int version, String name,
                                           String description, int priority, ConditionNode condition,
                                           List<RuleAction> actions, List<ChainConfiguration> chains,
                                           RuleStatus status, Instant changedAt, ChangeType changeType,
                                           String changedBy) {
        return new RuleVersion(id, ruleId, version, name, description, priority,
                condition, actions, chains, status, changedAt, changeType, changedBy);
    }

    /**
     * Returns the unique identifier of this version record.
     *
     * @return the UUID string
     */
    public String getId() { return id; }

    /**
     * Returns the identifier of the rule this version belongs to.
     *
     * @return the rule's UUID string
     */
    public String getRuleId() { return ruleId; }

    /**
     * Returns the sequential version number of this snapshot.
     *
     * @return a positive integer starting at 1 for the first snapshot of a rule
     */
    public int getVersion() { return version; }

    /**
     * Returns the rule name captured in this snapshot.
     *
     * @return the name string
     */
    public String getName() { return name; }

    /**
     * Returns the rule description captured in this snapshot.
     *
     * @return the description string; may be empty
     */
    public String getDescription() { return description; }

    /**
     * Returns the rule priority captured in this snapshot.
     *
     * @return the priority value
     */
    public int getPriority() { return priority; }

    /**
     * Returns the condition tree captured in this snapshot.
     *
     * @return the root condition node
     */
    public ConditionNode getCondition() { return condition; }

    /**
     * Returns the actions captured in this snapshot.
     *
     * @return an unmodifiable list of actions
     */
    public List<RuleAction> getActions() { return actions; }

    /**
     * Returns the chain configurations captured in this snapshot.
     *
     * @return an unmodifiable list of chain configurations
     */
    public List<ChainConfiguration> getChains() { return chains; }

    /**
     * Returns the rule status captured in this snapshot.
     *
     * @return the status value
     */
    public RuleStatus getStatus() { return status; }

    /**
     * Returns the instant at which this snapshot was taken.
     *
     * @return the UTC timestamp of the change
     */
    public Instant getChangedAt() { return changedAt; }

    /**
     * Returns the type of operation that triggered this snapshot.
     *
     * @return the change type
     */
    public ChangeType getChangeType() { return changeType; }

    /**
     * Returns the identifier of the user or system that made the change.
     *
     * @return the principal name; {@code "system"} when no authenticated user is present
     */
    public String getChangedBy() { return changedBy; }
}
```

<a id="arquivo-074"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/rule/version/RuleVersionRepository.java`

```java
package com.matheusjfa.yggdrasil.domain.rule.version;

import java.util.List;
import java.util.Optional;

/**
 * Domain port (output port) for persisting and querying {@link RuleVersion} snapshots.
 *
 * <p>Defined as an interface so the domain layer declares what it needs without
 * depending on any specific persistence technology. The infrastructure layer provides
 * the concrete implementation following the Dependency Inversion Principle.
 */
public interface RuleVersionRepository {

    /**
     * Persists a new version snapshot.
     *
     * @param version the snapshot to store; must not be {@code null}
     */
    void save(RuleVersion version);

    /**
     * Returns all version snapshots for a given rule, ordered by version number ascending.
     *
     * @param ruleId the identifier of the rule whose history is requested
     * @return a list of {@link RuleVersion}s; never {@code null}, may be empty
     */
    List<RuleVersion> findByRuleId(String ruleId);

    /**
     * Looks up a specific version of a rule by its sequential version number.
     *
     * @param ruleId  the identifier of the rule
     * @param version the sequential version number to retrieve
     * @return an {@link Optional} containing the version if found, or empty if not
     */
    Optional<RuleVersion> findByRuleIdAndVersion(String ruleId, int version);

    /**
     * Returns the next sequential version number to assign for a given rule.
     *
     * <p>Implementations typically return {@code (max existing version) + 1}, or {@code 1}
     * if no versions exist yet.
     *
     * @param ruleId the identifier of the rule for which to compute the next version number
     * @return the next version number to use
     */
    int nextVersion(String ruleId);
}
```

<a id="arquivo-075"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/schedule/Schedule.java`

```java
package com.matheusjfa.yggdrasil.domain.schedule;

import com.matheusjfa.yggdrasil.domain.shared.AggregateRoot;
import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate root representing a time-based trigger that periodically initiates a rule evaluation.
 *
 * <p>A {@code Schedule} stores a cron expression that defines when evaluations should run,
 * a URL from which the evaluation context is fetched at fire time, and housekeeping timestamps
 * that track the last and next fire instants. Enabling or disabling a schedule controls whether
 * the scheduler daemon will fire it without having to delete and re-create it.
 */
public class Schedule extends AggregateRoot<ScheduleId> {
    private final ScheduleId id;
    private String name;
    private String cronExpression;
    private String contextUrl;
    private boolean enabled;
    private Instant lastFiredAt;
    private Instant nextFireAt;
    private final Instant createdAt;
    private Instant updatedAt;

    private Schedule(ScheduleId id, String name, String cronExpression, String contextUrl,
                     boolean enabled, Instant lastFiredAt, Instant nextFireAt,
                     Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.cronExpression = Objects.requireNonNull(cronExpression);
        this.contextUrl = Objects.requireNonNull(contextUrl);
        this.enabled = enabled;
        this.lastFiredAt = lastFiredAt;
        this.nextFireAt = Objects.requireNonNull(nextFireAt);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    /**
     * Creates a new {@code Schedule} with a generated identifier and no previous fire time.
     *
     * @param name            the human-readable label for this schedule; must not be {@code null}
     * @param cronExpression  the Spring-compatible cron expression defining the fire cadence;
     *                        must not be {@code null}
     * @param contextUrl      the URL that will be fetched to obtain the evaluation context payload;
     *                        must not be {@code null}
     * @param enabled         whether the schedule should be active immediately upon creation
     * @param nextFireAt      the pre-computed instant of the first expected firing; must not be
     *                        {@code null}
     * @return a new {@code Schedule} with {@code createdAt} and {@code updatedAt} set to now
     *         and {@code lastFiredAt} set to {@code null}
     */
    public static Schedule create(String name, String cronExpression, String contextUrl,
                                   boolean enabled, Instant nextFireAt) {
        var now = Instant.now();
        return new Schedule(ScheduleId.generate(), name, cronExpression, contextUrl,
                enabled, null, nextFireAt, now, now);
    }

    /**
     * Reconstitutes a {@code Schedule} from previously persisted data.
     *
     * @param id             the persisted identifier string
     * @param name           the schedule's name as stored
     * @param cronExpression the cron expression as stored
     * @param contextUrl     the context URL as stored
     * @param enabled        the enabled flag as stored
     * @param lastFiredAt    the instant of the last firing, or {@code null} if never fired
     * @param nextFireAt     the pre-computed instant of the next expected firing
     * @param createdAt      the instant the schedule was originally created
     * @param updatedAt      the instant of the last modification
     * @return a {@code Schedule} that mirrors the persisted state
     */
    public static Schedule reconstitute(String id, String name, String cronExpression,
                                         String contextUrl, boolean enabled,
                                         Instant lastFiredAt, Instant nextFireAt,
                                         Instant createdAt, Instant updatedAt) {
        return new Schedule(ScheduleId.of(id), name, cronExpression, contextUrl,
                enabled, lastFiredAt, nextFireAt, createdAt, updatedAt);
    }

    /**
     * Applies a partial update to this schedule, ignoring any parameter that is {@code null}.
     *
     * @param name            the new name, or {@code null} to keep the current name
     * @param cronExpression  the new cron expression, or {@code null} to keep the current one
     * @param contextUrl      the new context URL, or {@code null} to keep the current one
     * @param nextFireAt      the newly computed next fire instant, or {@code null} to keep the
     *                        current value
     */
    public void update(String name, String cronExpression, String contextUrl, Instant nextFireAt) {
        if (name != null) this.name = name;
        if (cronExpression != null) this.cronExpression = cronExpression;
        if (contextUrl != null) this.contextUrl = contextUrl;
        if (nextFireAt != null) this.nextFireAt = nextFireAt;
        this.updatedAt = Instant.now();
    }

    /**
     * Marks this schedule as enabled so that the scheduler will fire it at the next due instant.
     */
    public void enable() { this.enabled = true; this.updatedAt = Instant.now(); }

    /**
     * Marks this schedule as disabled so that the scheduler will skip it.
     */
    public void disable() { this.enabled = false; this.updatedAt = Instant.now(); }

    /**
     * Records a successful firing of this schedule and advances the next fire time.
     *
     * @param nextFireAt the pre-computed instant of the next expected firing after this one;
     *                   must not be {@code null}
     */
    public void markFired(Instant nextFireAt) {
        this.lastFiredAt = Instant.now();
        this.nextFireAt = nextFireAt;
        this.updatedAt = Instant.now();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScheduleId getIdentity() { return id; }

    /**
     * Returns the string value of this schedule's identifier.
     *
     * @return the UUID string
     */
    public String getId() { return id.getValue(); }

    /**
     * Returns the human-readable name of this schedule.
     *
     * @return the name string
     */
    public String getName() { return name; }

    /**
     * Returns the cron expression that defines when this schedule fires.
     *
     * @return the cron expression string
     */
    public String getCronExpression() { return cronExpression; }

    /**
     * Returns the URL that will be fetched to obtain the evaluation context payload.
     *
     * @return the context URL string
     */
    public String getContextUrl() { return contextUrl; }

    /**
     * Returns whether this schedule is currently enabled.
     *
     * @return {@code true} if the schedule will fire when due, {@code false} if skipped
     */
    public boolean isEnabled() { return enabled; }

    /**
     * Returns the instant this schedule last fired, or {@code null} if it has never fired.
     *
     * @return the last fire timestamp, or {@code null}
     */
    public Instant getLastFiredAt() { return lastFiredAt; }

    /**
     * Returns the pre-computed instant at which this schedule is expected to fire next.
     *
     * @return the next fire timestamp; never {@code null}
     */
    public Instant getNextFireAt() { return nextFireAt; }

    /**
     * Returns the instant at which this schedule was created.
     *
     * @return the creation timestamp; never {@code null}
     */
    public Instant getCreatedAt() { return createdAt; }

    /**
     * Returns the instant of the most recent modification to this schedule.
     *
     * @return the last-updated timestamp; never {@code null}
     */
    public Instant getUpdatedAt() { return updatedAt; }
}
```

<a id="arquivo-076"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/schedule/ScheduleException.java`

```java
package com.matheusjfa.yggdrasil.domain.schedule;

/**
 * Domain exception for schedule-related error conditions.
 *
 * <p>Provides named factory methods for common failure scenarios to ensure consistent
 * error messages across the application.
 */
public class ScheduleException extends RuntimeException {
    private ScheduleException(String message) { super(message); }

    /**
     * Creates an exception indicating that no schedule with the given identifier exists.
     *
     * @param id the identifier that was looked up but not found
     * @return a new {@code ScheduleException} with a descriptive message
     */
    public static ScheduleException notFound(String id) {
        return new ScheduleException("Schedule not found: " + id);
    }
}
```

<a id="arquivo-077"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/schedule/ScheduleId.java`

```java
package com.matheusjfa.yggdrasil.domain.schedule;

import com.matheusjfa.yggdrasil.domain.shared.Identifier;
import java.util.UUID;

/**
 * Strongly-typed identifier for the {@link Schedule} aggregate root.
 *
 * <p>Wraps a UUID string to prevent accidental mixing of schedule identifiers with
 * identifiers from other aggregates at compile time. Supports both random generation
 * and reconstitution from a persisted string value.
 */
public final class ScheduleId extends Identifier<String> {
    private final String value;

    private ScheduleId(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("ScheduleId must not be null or blank");
        this.value = value;
    }

    /**
     * Creates a new {@code ScheduleId} backed by a randomly generated UUID.
     *
     * @return a new, unique {@code ScheduleId}
     */
    public static ScheduleId generate() { return new ScheduleId(UUID.randomUUID().toString()); }

    /**
     * Reconstitutes a {@code ScheduleId} from a previously persisted string value.
     *
     * @param value the string representation of the identifier; must not be blank
     * @return a {@code ScheduleId} wrapping the provided value
     * @throws IllegalArgumentException if {@code value} is {@code null} or blank
     */
    public static ScheduleId of(String value) { return new ScheduleId(value); }

    /**
     * Returns the underlying UUID string of this identifier.
     *
     * @return the string value
     */
    @Override
    public String getValue() { return value; }

    /**
     * Returns the string representation of this identifier (same as {@link #getValue()}).
     *
     * @return the string value
     */
    @Override
    public String toString() { return value; }
}
```

<a id="arquivo-078"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/schedule/ScheduleRepository.java`

```java
package com.matheusjfa.yggdrasil.domain.schedule;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Domain port (output port) for persisting and querying {@link Schedule} aggregates.
 *
 * <p>Defined as an interface so the domain layer declares what it needs without
 * depending on any specific persistence technology. The infrastructure layer supplies
 * the concrete implementation following the Dependency Inversion Principle.
 */
public interface ScheduleRepository {

    /**
     * Persists a new or updated schedule.
     *
     * @param schedule the schedule to store; must not be {@code null}
     * @return the saved schedule, potentially with infrastructure-enriched state
     */
    Schedule save(Schedule schedule);

    /**
     * Looks up a single schedule by its unique identifier.
     *
     * @param id the string representation of the {@link ScheduleId} to search for
     * @return an {@link Optional} containing the schedule if found, or empty if not
     */
    Optional<Schedule> findById(String id);

    /**
     * Returns all persisted schedules regardless of enabled state.
     *
     * @return a list of all schedules; never {@code null}, may be empty
     */
    List<Schedule> findAll();

    /**
     * Returns all enabled schedules whose {@code nextFireAt} is on or before the given instant.
     *
     * <p>Used by the scheduler daemon to determine which schedules are due for execution.
     *
     * @param now the reference instant to compare against each schedule's next fire time
     * @return a list of due, enabled schedules; never {@code null}, may be empty
     */
    List<Schedule> findEnabledDue(Instant now);

    /**
     * Removes the schedule with the given identifier from the persistent store.
     *
     * @param id the string representation of the identifier of the schedule to delete
     */
    void delete(String id);

    /**
     * Checks whether a schedule with the given identifier exists in the persistent store.
     *
     * @param id the identifier to check
     * @return {@code true} if a schedule with this identifier exists, {@code false} otherwise
     */
    boolean exists(String id);
}
```

<a id="arquivo-079"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/shared/AggregateRoot.java`

```java
package com.matheusjfa.yggdrasil.domain.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all aggregate roots in the domain layer.
 *
 * <p>An aggregate root is the entry point to an aggregate — a cluster of domain objects
 * treated as a single consistency boundary. This class extends {@link Entity} and adds
 * support for collecting and dispatching {@link DomainEvent}s, enabling event-driven
 * coordination between aggregates without direct coupling.
 *
 * @param <ID> the type of the aggregate's identifier, which must extend {@link Identifier}
 */
public abstract class AggregateRoot<ID extends Identifier<?>> extends Entity<ID> {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Returns an immutable snapshot of all domain events registered on this aggregate
     * since the last call to {@link #clearDomainEvents()}.
     *
     * @return an unmodifiable list of pending domain events
     */
    public List<DomainEvent> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    /**
     * Removes all pending domain events from this aggregate.
     *
     * <p>Should be called after events have been dispatched to external handlers
     * to prevent duplicate processing.
     */
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    /**
     * Registers a domain event to be dispatched when the aggregate is persisted.
     *
     * @param event the event to register; {@code null} values are silently ignored
     */
    protected void registerEvent(DomainEvent event) {
        if (event != null) this.domainEvents.add(event);
    }
}
```

<a id="arquivo-080"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/shared/DomainEvent.java`

```java
package com.matheusjfa.yggdrasil.domain.shared;

import java.time.Instant;

/**
 * Marker interface for all domain events in the domain layer.
 *
 * <p>A domain event represents something meaningful that happened in the domain.
 * Implementing classes describe facts that occurred within an aggregate and can be
 * used to trigger side-effects or synchronise other parts of the system without
 * creating direct coupling between aggregates.
 */
public interface DomainEvent {

    /**
     * Returns the unique identifier of this event instance.
     *
     * @return a non-null string that uniquely identifies this occurrence of the event
     */
    String eventId();

    /**
     * Returns the instant at which this event occurred.
     *
     * @return the UTC timestamp when the event was raised
     */
    Instant occurredOn();
}
```

<a id="arquivo-081"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/shared/Entity.java`

```java
package com.matheusjfa.yggdrasil.domain.shared;

import java.util.Objects;

/**
 * Base class for all domain entities in the domain layer.
 *
 * <p>An entity is a domain object with a unique identity that persists over time.
 * Two entities are considered equal when they share the same identity, regardless
 * of their attribute values — this is the fundamental distinction between entities
 * and value objects in DDD.
 *
 * @param <ID> the type of the entity's identifier, which must extend {@link Identifier}
 */
public abstract class Entity<ID extends Identifier<?>> {

    /**
     * Returns the unique identifier of this entity.
     *
     * @return the identity object of this entity; must not be {@code null} for equality
     *         to work correctly
     */
    public abstract ID getIdentity();

    /**
     * Compares this entity with another for equality based solely on identity.
     *
     * @param o the object to compare with
     * @return {@code true} if both objects are of the same concrete type and have equal identities
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return getIdentity() != null && Objects.equals(getIdentity(), entity.getIdentity());
    }

    /**
     * Returns a hash code based on the entity's identity.
     *
     * @return the hash code of the identity, or the default object hash code if identity is {@code null}
     */
    @Override
    public int hashCode() {
        return getIdentity() != null ? getIdentity().hashCode() : super.hashCode();
    }
}
```

<a id="arquivo-082"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/shared/Identifier.java`

```java
package com.matheusjfa.yggdrasil.domain.shared;

/**
 * Base class for strongly-typed entity identifiers in the domain layer.
 *
 * <p>Using a dedicated identifier type (rather than a raw {@code String} or {@code UUID})
 * prevents accidental mixing of identifiers from different aggregates at compile time and
 * makes the domain model more expressive. Equality is based entirely on the wrapped value.
 *
 * @param <T> the primitive or wrapper type of the underlying identifier value (e.g. {@code String})
 */
public abstract class Identifier<T> extends ValueObject {

    /**
     * Returns the underlying identifier value.
     *
     * @return the raw value wrapped by this identifier; should not be {@code null}
     */
    public abstract T getValue();

    /**
     * Compares this identifier to another for equality based on the wrapped value.
     *
     * @param obj the object to compare with
     * @return {@code true} if both identifiers are of the same concrete type and wrap equal values
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Identifier<?> that = (Identifier<?>) obj;
        return getValue() != null && getValue().equals(that.getValue());
    }

    /**
     * Returns a hash code based on the wrapped identifier value.
     *
     * @return the hash code of the underlying value, or {@code 0} if the value is {@code null}
     */
    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
```

<a id="arquivo-083"></a>

### `src/main/java/com/matheusjfa/yggdrasil/domain/shared/ValueObject.java`

```java
package com.matheusjfa.yggdrasil.domain.shared;

import java.io.Serializable;

/**
 * Base class for all value objects in the domain layer.
 *
 * <p>A value object has no identity of its own — two value objects are equal if and only if
 * all of their attributes are equal. Subclasses must implement {@code equals} and
 * {@code hashCode} based on their attribute values. Implementing {@link Serializable}
 * allows value objects to be safely stored in caches or transmitted across process boundaries.
 */
public abstract class ValueObject implements Serializable {}
```

<a id="arquivo-084"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/annotation/LogExecutionTime.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method whose wall-clock execution time should be measured and logged.
 *
 * <p>When placed on any method, the {@link com.matheusjfa.yggdrasil.infrastructure.common.aspect.LoggingAspect}
 * intercepts the call via an {@code @Around} advice, records the elapsed time in milliseconds,
 * and writes it to the application log at INFO level after the method returns (or throws).</p>
 *
 * <p>This annotation is retained at runtime so that the AOP proxy can discover it through
 * reflection during request processing.</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
```

<a id="arquivo-085"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/annotation/ValidateContext.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method parameter that must not be {@code null} or, when the parameter is a
 * {@link java.util.Map}, must not be empty.
 *
 * <p>The {@link com.matheusjfa.yggdrasil.infrastructure.common.aspect.ValidationAspect}
 * intercepts every method call whose signature includes a parameter annotated with
 * {@code @ValidateContext} and throws {@link IllegalArgumentException} before the method
 * body executes if the constraint is violated.</p>
 *
 * <p>Intended for use on context/fact-map parameters that feed the SpEL rule evaluation
 * engine, where an absent or empty context would produce meaningless results.</p>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateContext {
}
```

<a id="arquivo-086"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/aspect/LoggingAspect.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP aspect that measures and logs the wall-clock execution time of methods annotated
 * with {@link com.matheusjfa.yggdrasil.infrastructure.common.annotation.LogExecutionTime}.
 *
 * <p>This adapter-layer cross-cutting concern wraps target methods with an {@code @Around}
 * advice, captures the elapsed time in nanoseconds (converted to milliseconds), and emits
 * an INFO-level log entry after the method completes — whether normally or via exception.</p>
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Around advice that times the execution of any method annotated with
     * {@code @LogExecutionTime} and logs the result.
     *
     * @param joinPoint the proceeding join point representing the intercepted method call,
     *                  used to continue execution and to retrieve the method signature for logging
     * @return the value returned by the intercepted method, passed through transparently
     * @throws Throwable any exception thrown by the intercepted method, re-thrown unchanged
     *                   so callers observe normal error propagation
     */
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        try {
            return joinPoint.proceed();
        } finally {
            long executionTimeMs = java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            log.info("{} executed in {}ms", joinPoint.getSignature(), executionTimeMs);
        }
    }
}
```

<a id="arquivo-087"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/aspect/ValidationAspect.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.common.aspect;

import com.matheusjfa.yggdrasil.infrastructure.common.annotation.ValidateContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

import java.util.Map;

/**
 * AOP aspect that enforces the {@link ValidateContext} parameter-level annotation contract
 * before any method with such an annotated parameter is executed.
 *
 * <p>Acting as a guard in the infrastructure layer, this aspect inspects each argument
 * marked with {@code @ValidateContext} and throws {@link IllegalArgumentException} when
 * the argument is {@code null} or, for {@link Map} parameters, is empty. This prevents
 * the SpEL rule-evaluation engine from receiving incomplete input data.</p>
 */
@Aspect
@Component
public class ValidationAspect {

    /**
     * Before advice that validates every method argument annotated with
     * {@link ValidateContext} prior to method execution.
     *
     * <p>Iterates over the method's parameter annotations to locate arguments carrying
     * {@code @ValidateContext}, then delegates to the private {@code validate} helper.
     * The advice uses a pointcut expression that matches any method containing at least one
     * {@code @ValidateContext}-annotated parameter.</p>
     *
     * @param joinPoint the join point representing the intercepted method call, providing
     *                  access to the runtime argument values and the method signature
     * @throws IllegalArgumentException if any {@code @ValidateContext}-annotated argument
     *                                  is {@code null} or is an empty {@link Map}
     */
    @Before("execution(* *(.., @ValidateContext (*), ..))")
    public void validateContext(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var annotations = signature.getMethod().getParameterAnnotations();

        for (int i = 0; i < args.length; i++) {
            for (var annotation : annotations[i]) {
                if (annotation instanceof ValidateContext) {
                    validate(args[i]);
                }
            }
        }
    }

    private void validate(Object arg) {
        if (isNull(arg)) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        if (arg instanceof Map<?, ?> map && map.isEmpty()) {
            throw new IllegalArgumentException("Context cannot be empty");
        }
    }
}
```

<a id="arquivo-088"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/constant/AppConstants.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Centralized holder for application-wide constant values used across the infrastructure layer.
 *
 * <p>This utility class is non-instantiable (private constructor generated by Lombok's
 * {@code @NoArgsConstructor(access = AccessLevel.PRIVATE)}) and groups cache-name strings
 * together with numeric defaults so that they can be referenced from a single location
 * without magic literals scattered throughout configuration and service classes.</p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstants {

    /** Cache name used to store the set of currently active (enabled) rules. */
    public static final String ACTIVE_RULES_CACHE = "active-rules";

    /** Cache name used to store the compiled rule-forest structure for evaluation. */
    public static final String RULE_FOREST_CACHE = "rule-forest";

    /** Cache name used to store aggregated rule-execution metrics. */
    public static final String METRICS_CACHE = "metrics";

    /** Default maximum number of entries allowed in a single Caffeine cache region. */
    public static final long DEFAULT_CACHE_MAX_SIZE = 1000;
}
```

<a id="arquivo-089"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/common/filter/UserContextFilter.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter that propagates the authenticated user's identity into the SLF4J
 * Mapped Diagnostic Context (MDC) for the duration of each HTTP request.
 *
 * <p>This infrastructure adapter extends {@link OncePerRequestFilter} to guarantee
 * single execution per request. When a valid, authenticated {@link Authentication} is
 * present in the Spring Security context, the user's name is placed under the
 * {@code "userId"} MDC key so that downstream log statements automatically include it
 * without requiring explicit passing. The key is always removed in the {@code finally}
 * block to prevent MDC leakage across requests in a thread-pool environment.</p>
 */
@Component
public class UserContextFilter extends OncePerRequestFilter {

    /**
     * Extracts the authenticated user's name from the Spring Security context, stores it
     * in the SLF4J MDC under the {@code "userId"} key, and then continues the filter chain.
     *
     * @param request     the current HTTP request being processed
     * @param response    the current HTTP response being built
     * @param filterChain the remaining filter chain to delegate to after populating the MDC
     * @throws ServletException if the filter chain signals a servlet-layer error
     * @throws IOException      if an I/O error occurs while processing the request or response
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                MDC.put("userId", auth.getName());
            }
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove("userId");
        }
    }
}
```

<a id="arquivo-090"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/CacheConfiguration.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.matheusjfa.yggdrasil.infrastructure.common.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Spring configuration class that sets up the application-wide in-process cache backed by
 * Caffeine.
 *
 * <p>Three named caches are registered — {@code active-rules}, {@code rule-forest}, and
 * {@code metrics} — all sharing the same TTL and maximum-size policy that is read from
 * {@link YggdrasilProperties}. Using a single {@link CacheManager} bean allows Spring's
 * {@code @Cacheable}/{@code @CacheEvict} annotations throughout the application to resolve
 * their caches by name without additional configuration.</p>
 */
@Configuration
@RequiredArgsConstructor
public class CacheConfiguration {

    private final YggdrasilProperties properties;

    /**
     * Creates and configures the {@link CacheManager} bean that manages all named Caffeine
     * caches used by the application.
     *
     * <p>The three registered cache regions are:
     * <ul>
     *   <li>{@code active-rules} — holds the list of currently active (enabled) rule definitions</li>
     *   <li>{@code rule-forest} — holds the compiled rule-forest structure used during evaluation</li>
     *   <li>{@code metrics} — holds aggregated rule-execution metric snapshots</li>
     * </ul>
     * Each region shares the same write-expiry TTL ({@code yggdrasil.cache.rulesTtlSeconds}) and
     * maximum entry count ({@code yggdrasil.cache.maxSize}) sourced from {@link YggdrasilProperties},
     * enabling runtime tuning without code changes.</p>
     *
     * @return a fully configured {@link CaffeineCacheManager} ready to be injected as the
     *         primary {@link CacheManager} in the application context
     */
    @Bean
    public CacheManager cacheManager() {
        var manager = new CaffeineCacheManager(
                AppConstants.ACTIVE_RULES_CACHE,
                AppConstants.RULE_FOREST_CACHE,
                AppConstants.METRICS_CACHE
        );
        var cacheConfig = properties.getCache();

        manager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(cacheConfig.getRulesTtlSeconds(), TimeUnit.SECONDS)
            .maximumSize(cacheConfig.getMaxSize()));
        return manager;
    }
}
```

<a id="arquivo-091"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/KafkaConfiguration.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Spring configuration class that declares the Kafka topics required by the messaging layer.
 *
 * <p>Topic names are externalised through {@link YggdrasilProperties} so they can be
 * overridden per environment without recompilation. Both topics are created with a single
 * partition and replication factor of one, which is appropriate for development and
 * single-broker deployments; production deployments should override these via Kafka admin
 * tooling or Infrastructure-as-Code.</p>
 */
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final YggdrasilProperties properties;

    /**
     * Declares the Kafka topic that carries inbound rule-evaluation requests sent by
     * external producers.
     *
     * <p>The topic name is resolved from {@code yggdrasil.kafka.topics.evaluate-request}.
     * Spring Kafka's admin infrastructure creates the topic automatically at startup if it
     * does not already exist in the broker.</p>
     *
     * @return a {@link NewTopic} descriptor for the evaluation-request topic with one
     *         partition and a replication factor of one
     */
    @Bean
    public NewTopic evaluateRequestTopic() {
        return TopicBuilder.name(properties.getKafka().getTopics().getEvaluateRequest())
                .partitions(1)
                .replicas(1)
                .build();
    }

    /**
     * Declares the Kafka topic to which the application publishes rule-evaluation results
     * for downstream consumers.
     *
     * <p>The topic name is resolved from {@code yggdrasil.kafka.topics.evaluate-response}.
     * Spring Kafka's admin infrastructure creates the topic automatically at startup if it
     * does not already exist in the broker.</p>
     *
     * @return a {@link NewTopic} descriptor for the evaluation-response topic with one
     *         partition and a replication factor of one
     */
    @Bean
    public NewTopic evaluateResponseTopic() {
        return TopicBuilder.name(properties.getKafka().getTopics().getEvaluateResponse())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
```

<a id="arquivo-092"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/PersistenceConfiguration.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Spring configuration class that activates MongoDB auditing for the persistence layer.
 *
 * <p>The {@link EnableMongoAuditing} annotation instructs Spring Data MongoDB to
 * automatically populate fields annotated with {@code @CreatedDate}, {@code @LastModifiedDate},
 * {@code @CreatedBy}, and {@code @LastModifiedBy} on document entities whenever they are
 * persisted or updated. This removes the need for manual timestamp management in repository
 * adapters.</p>
 */
@Configuration
@EnableMongoAuditing
public class PersistenceConfiguration {}
```

<a id="arquivo-093"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/SchedulingConfiguration.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * Spring configuration class that enables scheduled task execution and registers shared
 * HTTP client infrastructure.
 *
 * <p>The {@link EnableScheduling} annotation activates Spring's task-scheduling support,
 * allowing any bean method annotated with {@code @Scheduled} — such as those in
 * {@link com.matheusjfa.yggdrasil.infrastructure.scheduler.ScheduleRunner} — to be picked
 * up by the framework's task scheduler without additional XML or programmatic setup.</p>
 */
@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    /**
     * Provides a shared {@link RestTemplate} bean for performing synchronous HTTP calls
     * from scheduled tasks and other infrastructure components.
     *
     * <p>A single shared instance is exposed as a Spring bean so that all consumers
     * benefit from a consistent, connection-pool-backed HTTP client rather than
     * creating short-lived instances per call.</p>
     *
     * @return a default {@link RestTemplate} instance with standard Spring HTTP message
     *         converters and error handling
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

<a id="arquivo-094"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/SecurityConfiguration.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class that defines the HTTP security filter chain for the
 * application.
 *
 * <p>This configuration:
 * <ul>
 *   <li>Disables CSRF protection because all API clients authenticate via stateless JWT
 *       bearer tokens and do not rely on session cookies.</li>
 *   <li>Permits unauthenticated access to health, metrics, and OpenAPI documentation
 *       endpoints so that monitoring infrastructure and API explorers do not require
 *       credentials.</li>
 *   <li>Requires authentication for all other requests.</li>
 *   <li>Configures an OAuth 2.0 resource-server with JWT validation, delegating token
 *       parsing and verification to Spring Security's built-in JWT decoder.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Builds and returns the {@link SecurityFilterChain} bean that governs HTTP security
     * for all incoming requests.
     *
     * <p>Permitted paths (no authentication required):
     * <ul>
     *   <li>{@code /actuator/health} — liveness and readiness probes</li>
     *   <li>{@code /actuator/prometheus} — Prometheus metrics scrape endpoint</li>
     *   <li>{@code /swagger-ui/**} and {@code /swagger-ui.html} — OpenAPI UI assets</li>
     *   <li>{@code /api-docs/**} — OpenAPI JSON/YAML specification</li>
     * </ul>
     * All other paths require a valid JWT bearer token. CSRF is disabled because the API
     * is stateless and consumed by machine clients rather than browser sessions.</p>
     *
     * @param http the {@link HttpSecurity} builder provided by the Spring Security framework
     *             for configuring the filter chain
     * @return the fully configured {@link SecurityFilterChain} registered in the application
     *         context
     * @throws Exception if Spring Security encounters an error while building the filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/actuator/health",
                    "/actuator/prometheus",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
        return http.build();
    }
}
```

<a id="arquivo-095"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/configuration/YggdrasilProperties.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "yggdrasil")
public class YggdrasilProperties {

    private CacheProperties cache = new CacheProperties();
    private KafkaProperties kafka = new KafkaProperties();

    @Data
    public static class CacheProperties {
        /**
         * Time to live for rules in the cache.
         */
        private long rulesTtlSeconds = 60;
        
        /**
         * Maximum number of entries in the cache.
         */
        private long maxSize = 1000;
    }

    @Data
    public static class KafkaProperties {
        private TopicProperties topics = new TopicProperties();

        @Data
        public static class TopicProperties {
            private String evaluateRequest;
            private String evaluateResponse;
        }
    }
}
```

<a id="arquivo-096"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/ExpressionEvaluator.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.engine;

import com.matheusjfa.yggdrasil.domain.evaluation.RuleContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Core engine component responsible for executing SpEL expressions.
 *
 * <p>Uses a {@link SimpleEvaluationContext} configured for read-only data binding,
 * which provides a secure sandbox for expression evaluation. It automatically
 * populates the evaluation context with variables from the {@link RuleContext}
 * and adds a special {@code #__now__} variable for time-based comparisons.
 */
@Component
public class ExpressionEvaluator {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * Evaluates an expression that is expected to return a boolean result.
     *
     * @param spelExpression the SpEL expression to evaluate
     * @param context        the context providing variables for the expression
     * @return {@code true} if the expression evaluates to true, {@code false} otherwise
     */
    public boolean evaluate(String spelExpression, RuleContext context) {
        return Boolean.TRUE.equals(evaluateRaw(spelExpression, context, Boolean.class));
    }

    /**
     * Evaluates an expression and returns the result cast to the specified type.
     *
     * @param spelExpression the SpEL expression to evaluate
     * @param context        the context providing variables for the expression
     * @param targetType     the expected class of the result
     * @param <T>            the type of the result
     * @return the evaluation result, or {@code null} if the expression evaluates to null
     */
    public <T> T evaluateRaw(String spelExpression, RuleContext context, Class<T> targetType) {
        var evalContext = SimpleEvaluationContext
                .forReadOnlyDataBinding()
                .withInstanceMethods()
                .build();
        evalContext.setVariable("__now__", LocalDateTime.now());
        context.getAttributes().forEach(evalContext::setVariable);
        return parser.parseExpression(spelExpression).getValue(evalContext, targetType);
    }
}
```

<a id="arquivo-097"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelGenerator.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.engine;

import com.matheusjfa.yggdrasil.domain.rule.condition.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Translator that converts a domain condition tree into a SpEL expression string.
 *
 * <p>Recursively traverses {@link ConditionNode} structures (Groups, Leaves, and raw SpEL)
 * to produce a single valid Spring Expression Language string. It handles various
 * operators (comparison, logical, collection-based, and time-based) and ensures
 * proper escaping of string literal values.
 */
@Component
public class SpelGenerator {

    /**
     * Entry point for translating any {@link ConditionNode} into its SpEL equivalent.
     *
     * @param node the root of the condition tree to translate
     * @return a SpEL expression string ready for evaluation
     */
    public String generate(ConditionNode node) {
        return switch (node) {
            case ConditionGroup group -> generateGroup(group);
            case ConditionLeaf leaf -> generateLeaf(leaf);
            case SpelCondition spel -> spel.expression();
        };
    }

    /**
     * Translates a {@link ConditionGroup} by joining its children with the appropriate logical operator.
     * Each child is wrapped in parentheses to ensure correct precedence.
     */
    private String generateGroup(ConditionGroup group) {
        String sep = group.operator() == LogicalOperator.AND ? " && " : " || ";
        return group.children().stream()
                .map(child -> "(" + generate(child) + ")")
                .collect(Collectors.joining(sep));
    }

    /**
     * Translates a {@link ConditionLeaf} into a SpEL comparison.
     * Handles field access (prefixed with #), value formatting, and special operators
     * like LAST_MONTH or LAST_N_DAYS.
     */
    private String generateLeaf(ConditionLeaf leaf) {
        String field = "#" + leaf.field();
        String value = formatValue(leaf.value());
        return switch (leaf.operator()) {
            case EQUAL -> field + " == " + value;
            case NOT_EQUAL -> field + " != " + value;
            case GREATER_THAN -> field + " > " + value;
            case GREATER_THAN_OR_EQUAL -> field + " >= " + value;
            case LESS_THAN -> field + " < " + value;
            case LESS_THAN_OR_EQUAL -> field + " <= " + value;
            case CONTAINS -> field + ".contains(" + value + ")";
            case STARTS_WITH -> field + ".startsWith(" + value + ")";
            case IS_NULL -> field + " == null";
            case IS_NOT_NULL -> field + " != null";
            case IN -> generateInChain(field, leaf.value(), " == ", " || ");
            case NOT_IN -> generateInChain(field, leaf.value(), " != ", " && ");
            case IS_BETWEEN -> generateBetween(field, leaf.value());
            case LAST_MONTH -> "(" + field + " != null && " + field + ".year == #__now__.minusMonths(1).year && " + field + ".monthValue == #__now__.minusMonths(1).monthValue)";
            case LAST_N_DAYS -> "(" + field + " != null && !" + field + ".isBefore(#__now__.minusDays(" + leaf.value() + ")) && !" + field + ".isAfter(#__now__))";
            case ENDS_WITH -> field + ".endsWith(" + value + ")";
            case MATCHES -> field + " matches " + value;
            case CONTAINS_IGNORE_CASE -> field + " != null && " + field + ".toLowerCase().contains(" + value + ".toLowerCase())";
            case MEMBER_OF -> value + ".contains(" + field + ")";
            case CONTAINS_ALL -> generateCollectionOp(field, leaf.value(), "containsAll");
            case CONTAINS_ANY -> generateCollectionOp(field, leaf.value(), "containsAny");
            case IS_EMPTY -> field + ".isEmpty()";
            case IS_NOT_EMPTY -> "!" + field + ".isEmpty()";
            case BEFORE -> field + ".isBefore(" + value + ")";
            case AFTER -> field + ".isAfter(" + value + ")";
        };
    }

    private String generateCollectionOp(String field, Object value, String method) {
        // For CONTAINS_ALL/ANY, we might need custom helper registration or complex SpEL
        // Using basic collection method as placeholder
        return field + "." + method + "(" + formatValue(value) + ")";
    }

    @SuppressWarnings("unchecked")
    private String generateInChain(String field, Object value, String op, String sep) {
        List<Object> items = (List<Object>) value;
        return "(" + items.stream()
                .map(item -> field + op + formatValue(item))
                .collect(Collectors.joining(sep)) + ")";
    }

    @SuppressWarnings("unchecked")
    private String generateBetween(String field, Object value) {
        List<Object> range = (List<Object>) value;
        return "(" + field + " >= " + formatValue(range.get(0)) + " && " + field + " <= " + formatValue(range.get(1)) + ")";
    }

    private String formatValue(Object value) {
        if (value instanceof String s) return "'" + s.replace("'", "\\'") + "'";
        return String.valueOf(value);
    }
}
```

<a id="arquivo-098"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelRuleEvaluator.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.engine;

import com.matheusjfa.yggdrasil.domain.evaluation.RuleContext;
import com.matheusjfa.yggdrasil.domain.evaluation.RuleEvaluator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link RuleEvaluator} that uses the Spring Expression Language (SpEL).
 *
 * <p>This evaluator acts as the bridge between the domain condition tree and the
 * technical SpEL execution engine. It first translates a {@link ConditionNode} into
 * a SpEL string using {@link SpelGenerator}, and then executes it against a
 * {@link RuleContext} using {@link ExpressionEvaluator}.
 */
@Component
@RequiredArgsConstructor
public class SpelRuleEvaluator implements RuleEvaluator {

    private final SpelGenerator spelGenerator;
    private final ExpressionEvaluator expressionEvaluator;

    /**
     * Evaluates a condition tree against a given context.
     *
     * @param condition the root of the condition tree to evaluate
     * @param context   the context containing attributes for evaluation
     * @return {@code true} if the condition tree evaluates to true, {@code false} otherwise
     */
    @Override
    public boolean evaluate(ConditionNode condition, RuleContext context) {
        return expressionEvaluator.evaluate(spelGenerator.generate(condition), context);
    }

    /**
     * Evaluates a raw SpEL expression string against a given context.
     *
     * <p>Useful for dynamic expressions or actions that are defined as plain strings.
     *
     * @param expression the SpEL expression to evaluate
     * @param context    the context containing attributes for evaluation
     * @return the result of the evaluation, cast to {@link Object}
     */
    @Override
    public Object evaluateExpression(String expression, RuleContext context) {
        return expressionEvaluator.evaluateRaw(expression, context, Object.class);
    }
}
```

<a id="arquivo-099"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/EvaluationRequestConsumer.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.messaging;

import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.infrastructure.messaging.dto.EvaluateMessage;
import com.matheusjfa.yggdrasil.infrastructure.messaging.dto.EvaluationResultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EvaluationRequestConsumer {

    private final EvaluateContextUseCase evaluateContextUseCase;
    private final EvaluationResponseProducer responseProducer;

    @KafkaListener(
        topics = "${yggdrasil.kafka.topics.evaluate-request}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(EvaluateMessage message) {
        var output = evaluateContextUseCase.execute(
                new EvaluateContextUseCase.Input(message.context(), ExecutionOrigin.ASYNC, false));

        responseProducer.send(new EvaluationResultMessage(
                message.correlationId(),
                output.hasMatches(),
                output.firedRules(),
                output.inputContext()
        ));
    }
}
```

<a id="arquivo-100"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/EvaluationResponseProducer.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.messaging;

import com.matheusjfa.yggdrasil.infrastructure.messaging.dto.EvaluationResultMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EvaluationResponseProducer {

    private final KafkaTemplate<String, EvaluationResultMessage> kafkaTemplate;

    @Value("${yggdrasil.kafka.topics.evaluate-response}")
    private String responseTopic;

    public void send(EvaluationResultMessage message) {
        kafkaTemplate.send(responseTopic, message.correlationId(), message);
    }
}
```

<a id="arquivo-101"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/dto/EvaluateMessage.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.messaging.dto;

import java.util.Map;

public record EvaluateMessage(String correlationId, Map<String, Object> context) {}
```

<a id="arquivo-102"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/messaging/dto/EvaluationResultMessage.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.messaging.dto;

import com.matheusjfa.yggdrasil.domain.execution.FiredRule;
import java.util.List;
import java.util.Map;

public record EvaluationResultMessage(
    String correlationId,
    boolean hasMatches,
    List<FiredRule> firedRules,
    Map<String, Object> inputContext
) {}
```

<a id="arquivo-103"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/config/IndexInitializer.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.config;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndexInitializer implements ApplicationRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) {
        var rulesOps = mongoTemplate.indexOps("rules");
        rulesOps.ensureIndex(new Index().on("name", Sort.Direction.ASC));
        rulesOps.ensureIndex(new Index().on("status", Sort.Direction.ASC));

        var schedulesOps = mongoTemplate.indexOps("schedules");
        schedulesOps.ensureIndex(new CompoundIndexDefinition(
                new Document("enabled", 1).append("nextFireAt", 1)));

        var executionsOps = mongoTemplate.indexOps("rule_executions");
        executionsOps.ensureIndex(new Index().on("executedAt", Sort.Direction.DESC));
    }
}
```

<a id="arquivo-104"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/MongoRuleExecutionRepository.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.execution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRuleExecutionRepository extends MongoRepository<RuleExecutionDocument, String> {
    Page<RuleExecutionDocument> findAllByOrderByExecutedAtDesc(Pageable pageable);
    long countByFiredRulesRuleId(String ruleId);
}
```

<a id="arquivo-105"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionDocument.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.execution;

import com.matheusjfa.yggdrasil.domain.execution.ChainStep;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.execution.FiredRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Document(collection = "rule_executions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleExecutionDocument {
    @Id
    private String id;
    private Map<String, Object> inputContext;
    private List<FiredRule> firedRules;
    private List<ChainStep> chainTrace;
    private ExecutionOrigin origin;

    @Indexed
    private Instant executedAt;
}
```

<a id="arquivo-106"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionDocumentMapper.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.execution;

import com.matheusjfa.yggdrasil.domain.execution.RuleExecution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RuleExecutionDocumentMapper {

    @Mapping(source = "id", target = "id")
    RuleExecutionDocument toDocument(RuleExecution execution);

    default RuleExecution toDomain(RuleExecutionDocument doc) {
        return RuleExecution.reconstitute(
                doc.getId(), doc.getInputContext(), doc.getFiredRules(),
                doc.getChainTrace(), doc.getOrigin(), doc.getExecutedAt());
    }
}
```

<a id="arquivo-107"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionRepositoryAdapter.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.execution;

import com.matheusjfa.yggdrasil.domain.execution.RuleExecution;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RuleExecutionRepositoryAdapter implements RuleExecutionRepository {

    private final MongoRuleExecutionRepository mongo;
    private final RuleExecutionDocumentMapper mapper;

    @Override
    public RuleExecution save(RuleExecution execution) {
        return mapper.toDomain(mongo.save(mapper.toDocument(execution)));
    }

    @Override
    public Optional<RuleExecution> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<RuleExecution> findAllOrderByExecutedAtDesc(Pageable pageable) {
        return mongo.findAllByOrderByExecutedAtDesc(pageable).map(mapper::toDomain);
    }

    @Override
    public long countAll() {
        return mongo.count();
    }

    @Override
    public long countFiredByRuleId(String ruleId) {
        return mongo.countByFiredRulesRuleId(ruleId);
    }
}
```

<a id="arquivo-108"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/MongoRuleRepository.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule;

import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoRuleRepository extends MongoRepository<RuleDocument, String> {
    List<RuleDocument> findByStatus(RuleStatus status);
    List<RuleDocument> findByNameContainingIgnoreCase(String name);
}
```

<a id="arquivo-109"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleDocument.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule;

import com.matheusjfa.yggdrasil.domain.rule.ChainConfiguration;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDocument {
    @Id
    private String id;

    @Indexed
    private String name;

    private String description;
    private int priority;
    private String conditionJson;
    private List<RuleAction> actions;
    private List<ChainConfiguration> chains;

    @Indexed
    private RuleStatus status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
```

<a id="arquivo-110"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleDocumentMapper.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MapStruct mapper for converting between {@link Rule} domain entities and {@link RuleDocument} MongoDB documents.
 *
 * <p>Handles the complex mapping of the condition tree by serializing/deserializing it
 * to/from a JSON string, ensuring that the nested structure is preserved in the database.
 */
@Mapper(componentModel = "spring")
public abstract class RuleDocumentMapper {

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Converts a domain {@link Rule} to a {@link RuleDocument}.
     *
     * @param rule the domain rule to convert
     * @return the corresponding persistence document
     */
    @Mapping(source = "condition", target = "conditionJson", qualifiedByName = "serializeCondition")
    public abstract RuleDocument toDocument(Rule rule);

    /**
     * Serializes a {@link ConditionNode} tree to its JSON string representation.
     *
     * @param condition the condition tree root
     * @return a JSON string
     * @throws IllegalArgumentException if serialization fails
     */
    @Named("serializeCondition")
    protected String serializeCondition(ConditionNode condition) {
        try {
            return objectMapper.writeValueAsString(condition);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid condition: " + e.getMessage(), e);
        }
    }

    /**
     * Reconstitutes a domain {@link Rule} from a {@link RuleDocument}.
     *
     * @param document the persistence document to convert
     * @return the reconstructed domain rule
     * @throws IllegalStateException if deserialization of the condition fails
     */
    public Rule toDomain(RuleDocument document) {
        try {
            ConditionNode condition = objectMapper.readValue(document.getConditionJson(), ConditionNode.class);
            return Rule.reconstitute(
                    document.getId(),
                    document.getName(),
                    document.getDescription(),
                    document.getPriority(),
                    condition,
                    document.getActions(),
                    document.getChains(),
                    document.getStatus(),
                    document.getCreatedAt(), 
                    document.getUpdatedAt()
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize condition: " + e.getMessage(), e);
        }
    }
}
```

<a id="arquivo-111"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleRepositoryAdapter.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule;

import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence adapter that implements {@link RuleRepository} using Spring Data MongoDB.
 *
 * <p>This class acts as a bridge between the domain layer and the persistence layer.
 * It uses {@link MongoRuleRepository} for database operations and {@link RuleDocumentMapper}
 * to convert between domain entities and MongoDB documents.
 *
 * <p>It also manages caching for active rules to optimize performance during evaluation.
 */
@Repository
@RequiredArgsConstructor
public class RuleRepositoryAdapter implements RuleRepository {

    private final MongoRuleRepository mongo;
    private final RuleDocumentMapper mapper;

    @Override
    public Optional<Rule> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Rule> findAll() {
        return mongo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Rule> findAllByNameContaining(String name) {
        return mongo.findByNameContainingIgnoreCase(name).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Rule> findAllByStatus(RuleStatus status) {
        return mongo.findByStatus(status).stream().map(mapper::toDomain).toList();
    }

    /**
     * Retrieves all active rules, utilizing a cache to avoid redundant database hits.
     *
     * @return a list of active rules
     */
    @Override
    @Cacheable("active-rules")
    public List<Rule> findAllActive() {
        return mongo.findByStatus(RuleStatus.ACTIVE).stream().map(mapper::toDomain).toList();
    }

    /**
     * Saves a rule and invalidates the active rules cache to ensure consistency.
     *
     * @param rule the rule to save
     * @return the saved rule
     */
    @Override
    @CacheEvict(value = "active-rules", allEntries = true)
    public Rule save(Rule rule) {
        var saved = mongo.save(mapper.toDocument(rule));
        return mapper.toDomain(saved);
    }

    /**
     * Deletes a rule by identifier and invalidates the active rules cache.
     *
     * @param id the identifier of the rule to delete
     */
    @Override
    @CacheEvict(value = "active-rules", allEntries = true)
    public void delete(String id) {
        mongo.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return mongo.existsById(id);
    }
}
```

<a id="arquivo-112"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/MongoRuleVersionRepository.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule.version;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoRuleVersionRepository extends MongoRepository<RuleVersionDocument, String> {
    List<RuleVersionDocument> findByRuleIdOrderByVersionAsc(String ruleId);
    Optional<RuleVersionDocument> findByRuleIdAndVersion(String ruleId, int version);
    int countByRuleId(String ruleId);
}
```

<a id="arquivo-113"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/RuleVersionDocument.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule.version;

import com.matheusjfa.yggdrasil.domain.rule.ChainConfiguration;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "rule_versions")
@CompoundIndex(name = "ruleId_version", def = "{'ruleId': 1, 'version': 1}", unique = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleVersionDocument {
    @Id
    private String id;

    @Indexed
    private String ruleId;

    private int version;
    private String name;
    private String description;
    private int priority;
    private String conditionJson;
    private List<RuleAction> actions;
    private List<ChainConfiguration> chains;
    private RuleStatus status;
    private Instant changedAt;
    private ChangeType changeType;
    private String changedBy;
}
```

<a id="arquivo-114"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/RuleVersionDocumentMapper.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule.version;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class RuleVersionDocumentMapper {

    @Autowired
    protected ObjectMapper objectMapper;

    @Mapping(source = "condition", target = "conditionJson", qualifiedByName = "serializeCondition")
    public abstract RuleVersionDocument toDocument(RuleVersion version);

    @Named("serializeCondition")
    protected String serializeCondition(ConditionNode condition) {
        try {
            return objectMapper.writeValueAsString(condition);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid condition: " + e.getMessage(), e);
        }
    }

    public RuleVersion toDomain(RuleVersionDocument doc) {
        try {
            ConditionNode condition = objectMapper.readValue(doc.getConditionJson(), ConditionNode.class);
            return RuleVersion.reconstitute(
                    doc.getId(), doc.getRuleId(), doc.getVersion(),
                    doc.getName(), doc.getDescription(), doc.getPriority(),
                    condition, doc.getActions(), doc.getChains(),
                    doc.getStatus(), doc.getChangedAt(), doc.getChangeType(),
                    Optional.ofNullable(doc.getChangedBy()).orElse("system")
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize condition: " + e.getMessage(), e);
        }
    }
}
```

<a id="arquivo-115"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/version/RuleVersionRepositoryAdapter.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule.version;

import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RuleVersionRepositoryAdapter implements RuleVersionRepository {

    private final MongoRuleVersionRepository mongoRepository;
    private final RuleVersionDocumentMapper mapper;

    @Override
    public void save(RuleVersion version) {
        mongoRepository.save(mapper.toDocument(version));
    }

    @Override
    public List<RuleVersion> findByRuleId(String ruleId) {
        return mongoRepository.findByRuleIdOrderByVersionAsc(ruleId)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<RuleVersion> findByRuleIdAndVersion(String ruleId, int version) {
        return mongoRepository.findByRuleIdAndVersion(ruleId, version).map(mapper::toDomain);
    }

    @Override
    public int nextVersion(String ruleId) {
        return mongoRepository.countByRuleId(ruleId) + 1;
    }
}
```

<a id="arquivo-116"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/MongoScheduleRepository.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.schedule;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface MongoScheduleRepository extends MongoRepository<ScheduleDocument, String> {

    @Query("{ 'enabled': true, 'nextFireAt': { $lte: ?0 } }")
    List<ScheduleDocument> findEnabledDue(Instant now);
}
```

<a id="arquivo-117"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleDocument.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "schedules")
@CompoundIndex(name = "enabled_nextFireAt", def = "{'enabled': 1, 'nextFireAt': 1}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDocument {
    @Id
    private String id;
    private String name;
    private String cronExpression;
    private String contextUrl;
    private boolean enabled;
    private Instant lastFiredAt;
    private Instant nextFireAt;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
```

<a id="arquivo-118"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleDocumentMapper.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleDocumentMapper {

    @Mapping(source = "id", target = "id")
    ScheduleDocument toDocument(Schedule schedule);

    default Schedule toDomain(ScheduleDocument doc) {
        return Schedule.reconstitute(
                doc.getId(), doc.getName(), doc.getCronExpression(),
                doc.getContextUrl(), doc.isEnabled(), doc.getLastFiredAt(),
                doc.getNextFireAt(), doc.getCreatedAt(), doc.getUpdatedAt());
    }
}
```

<a id="arquivo-119"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleRepositoryAdapter.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryAdapter implements ScheduleRepository {

    private final MongoScheduleRepository mongo;
    private final ScheduleDocumentMapper mapper;

    @Override
    public Schedule save(Schedule schedule) {
        return mapper.toDomain(mongo.save(mapper.toDocument(schedule)));
    }

    @Override
    public Optional<Schedule> findById(String id) {
        return mongo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Schedule> findAll() {
        return mongo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Schedule> findEnabledDue(Instant now) {
        return mongo.findEnabledDue(now).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(String id) {
        mongo.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return mongo.existsById(id);
    }
}
```

<a id="arquivo-120"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/EvaluationController.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.infrastructure.rest.dto.EvaluateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for synchronous rule evaluation.
 *
 * <p>Provides an entry point for external systems to evaluate a set of attributes
 * against the rule forest.
 */
@RestController
@RequestMapping("/rules/evaluate")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluateContextUseCase evaluateContextUseCase;

    /**
     * Evaluates the provided context against all active rules.
     *
     * @param req    the evaluation request containing attributes
     * @param dryRun if {@code true}, the evaluation is performed but no execution record is persisted
     * @return the evaluation result, including fired rules and actions
     */
    @PostMapping
    public EvaluateContextUseCase.Output evaluate(
            @Valid @RequestBody EvaluateRequest req,
            @RequestParam(defaultValue = "false") boolean dryRun) {
        return evaluateContextUseCase.execute(
                new EvaluateContextUseCase.Input(req.context(), ExecutionOrigin.SYNC, dryRun));
    }
}
```

<a id="arquivo-121"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/ExecutionController.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.application.usecase.evaluation.ReplayExecutionUseCase;
import com.matheusjfa.yggdrasil.application.usecase.execution.GetExecutionUseCase;
import com.matheusjfa.yggdrasil.application.usecase.execution.ListExecutionsUseCase;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for querying and replaying rule execution records.
 *
 * <p>Allows auditing the history of rule evaluations and re-triggering past
 * executions for debugging or reconciliation purposes.
 */
@RestController
@RequestMapping("/rules/executions")
@RequiredArgsConstructor
public class ExecutionController {

    private final ListExecutionsUseCase listExecutionsUseCase;
    private final GetExecutionUseCase getExecutionUseCase;
    private final ReplayExecutionUseCase replayExecutionUseCase;

    /**
     * Retrieves a paginated list of all rule executions.
     *
     * @param page the page number (0-indexed)
     * @param size the number of records per page
     * @return a page of execution summaries
     */
    @GetMapping
    public Page<ListExecutionsUseCase.Output> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return listExecutionsUseCase.execute(PageRequest.of(page, size));
    }

    /**
     * Retrieves the full details of a specific execution record.
     *
     * @param id the execution identifier
     * @return the complete execution record
     */
    @GetMapping("/{id}")
    public RuleExecution findById(@PathVariable String id) {
        return getExecutionUseCase.execute(id);
    }

    /**
     * Replays a previous execution using the exact same context attributes.
     *
     * @param id     the identifier of the execution to replay
     * @param dryRun if {@code true}, the replay is performed but no new execution record is persisted
     * @return the result of the re-evaluation
     */
    @PostMapping("/{id}/replay")
    public EvaluateContextUseCase.Output replay(
            @PathVariable String id,
            @RequestParam(defaultValue = "false") boolean dryRun) {
        return replayExecutionUseCase.execute(id, dryRun);
    }
}
```

<a id="arquivo-122"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/MetricsController.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final GetMetricsUseCase getMetricsUseCase;

    @GetMapping
    public GetMetricsUseCase.Output metrics() {
        return getMetricsUseCase.execute();
    }
}
```

<a id="arquivo-123"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleController.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.infrastructure.rest.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing rules and their versions.
 *
 * <p>Provides endpoints for CRUD operations, rule status management, testing,
 * forest visualization, and historical version management.
 */
@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleController {

    private final CreateRuleUseCase createRuleUseCase;
    private final GetRuleUseCase getRuleUseCase;
    private final ListRulesUseCase listRulesUseCase;
    private final UpdateRuleUseCase updateRuleUseCase;
    private final UpdateRuleStatusUseCase updateRuleStatusUseCase;
    private final DeleteRuleUseCase deleteRuleUseCase;
    private final TestRuleUseCase testRuleUseCase;
    private final GetForestUseCase getForestUseCase;
    private final ListRuleVersionsUseCase listRuleVersionsUseCase;
    private final GetRuleVersionUseCase getRuleVersionUseCase;
    private final RestoreRuleVersionUseCase restoreRuleVersionUseCase;

    /**
     * Creates a new rule.
     *
     * @param req the rule creation request containing name, priority, and conditions
     * @return the created rule details
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRuleUseCase.Output create(@Valid @RequestBody CreateRuleRequest req) {
        return createRuleUseCase.execute(new CreateRuleUseCase.Input(
                req.name(), req.description(), req.priority(), req.condition(),
                req.actions(), req.chains()));
    }

    /**
     * Lists rules with optional filtering by name and status.
     *
     * @param name   optional partial name filter
     * @param status optional status filter
     * @return a list of matching rules
     */
    @GetMapping
    public List<ListRulesUseCase.Output> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) RuleStatus status) {
        return listRulesUseCase.execute(name, status);
    }

    /**
     * Retrieves a single rule by its identifier.
     *
     * @param id the rule identifier
     * @return the rule details
     */
    @GetMapping("/{id}")
    public GetRuleUseCase.Output findById(@PathVariable String id) {
        return getRuleUseCase.execute(id);
    }

    /**
     * Updates an existing rule.
     *
     * @param id  the rule identifier
     * @param req the update request
     * @return the updated rule details
     */
    @PutMapping("/{id}")
    public UpdateRuleUseCase.Output update(@PathVariable String id,
                                            @Valid @RequestBody UpdateRuleRequest req) {
        return updateRuleUseCase.execute(id, new UpdateRuleUseCase.Input(
                req.name(), req.description(), req.priority(), req.condition(),
                req.actions(), req.chains()));
    }

    /**
     * Updates the status (ACTIVE/INACTIVE) of a rule.
     *
     * @param id  the rule identifier
     * @param req the status update request
     * @return the updated status details
     */
    @PatchMapping("/{id}/status")
    public UpdateRuleStatusUseCase.Output updateStatus(@PathVariable String id,
                                                        @Valid @RequestBody UpdateStatusRequest req) {
        return updateRuleStatusUseCase.execute(id, req.status());
    }

    /**
     * Permanently deletes a rule.
     *
     * @param id the rule identifier
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        deleteRuleUseCase.execute(id);
    }

    /**
     * Tests a rule against a provided context without persisting any execution data.
     *
     * @param id  the rule identifier
     * @param req the test request containing the context
     * @return the evaluation result
     */
    @PostMapping("/{id}/test")
    public TestRuleUseCase.Output test(@PathVariable String id,
                                        @Valid @RequestBody TestRuleRequest req) {
        return testRuleUseCase.execute(id, req.context());
    }

    /**
     * Retrieves the visual representation of the rule forest (chained rules).
     *
     * @return the rule forest structure
     */
    @GetMapping("/forest")
    public GetForestUseCase.Output getForest() {
        return getForestUseCase.execute();
    }

    /**
     * Lists the version history of a specific rule.
     *
     * @param id the rule identifier
     * @return a list of version summaries
     */
    @GetMapping("/{id}/history")
    public List<ListRuleVersionsUseCase.VersionSummary> getHistory(@PathVariable String id) {
        return listRuleVersionsUseCase.execute(id);
    }

    /**
     * Retrieves a specific historical version of a rule.
     *
     * @param id      the rule identifier
     * @param version the version number
     * @return the rule version details
     */
    @GetMapping("/{id}/history/{version}")
    public RuleVersion getVersion(@PathVariable String id, @PathVariable int version) {
        return getRuleVersionUseCase.execute(id, version);
    }

    /**
     * Restores a rule to a specific historical version.
     *
     * @param id      the rule identifier
     * @param version the version number to restore
     * @return the updated rule details (now matching the restored version)
     */
    @PostMapping("/{id}/restore/{version}")
    public UpdateRuleUseCase.Output restore(@PathVariable String id, @PathVariable int version) {
        return restoreRuleVersionUseCase.execute(id, version);
    }
}
```

<a id="arquivo-124"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleImportExportController.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.matheusjfa.yggdrasil.application.usecase.rule.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RuleImportExportController {

    private static final YAMLMapper YAML_MAPPER =
            YAMLMapper.builder().findAndAddModules().build();

    private final ExportRuleUseCase exportRuleUseCase;
    private final ExportRulesUseCase exportRulesUseCase;
    private final ImportRulesUseCase importRulesUseCase;

    @GetMapping("/rules/{id}/export")
    public ResponseEntity<Object> exportRule(
            @PathVariable String id,
            @RequestParam(defaultValue = "json") String format) throws Exception {
        var pkg = exportRuleUseCase.execute(id);
        return toResponse(pkg, format);
    }

    @GetMapping("/rules/export")
    public ResponseEntity<Object> exportRules(
            @RequestParam(defaultValue = "json") String format) throws Exception {
        var pkg = exportRulesUseCase.execute();
        return toResponse(pkg, format);
    }

    @PostMapping("/rules/import")
    public ImportRulesUseCase.Result importRules(
            @RequestBody RulePackage pkg,
            @RequestParam(defaultValue = "SKIP") ConflictStrategy strategy) {
        return importRulesUseCase.execute(pkg, strategy);
    }

    private ResponseEntity<Object> toResponse(RulePackage pkg, String format) throws Exception {
        if ("yaml".equalsIgnoreCase(format)) {
            var yaml = YAML_MAPPER.writeValueAsString(pkg);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/yaml"))
                    .body(yaml);
        }
        return ResponseEntity.ok(pkg);
    }
}
```

<a id="arquivo-125"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/ScheduleController.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.schedule.*;
import com.matheusjfa.yggdrasil.infrastructure.rest.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final CreateScheduleUseCase createScheduleUseCase;
    private final UpdateScheduleUseCase updateScheduleUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;
    private final ListSchedulesUseCase listSchedulesUseCase;
    private final ToggleScheduleStatusUseCase toggleScheduleStatusUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateScheduleUseCase.Output create(@Valid @RequestBody CreateScheduleRequest req) {
        return createScheduleUseCase.execute(new CreateScheduleUseCase.Input(
                req.name(), req.cronExpression(), req.contextUrl(), req.enabled()));
    }

    @GetMapping
    public List<ListSchedulesUseCase.Output> findAll() {
        return listSchedulesUseCase.execute();
    }

    @PutMapping("/{id}")
    public UpdateScheduleUseCase.Output update(@PathVariable String id,
                                                @Valid @RequestBody UpdateScheduleRequest req) {
        return updateScheduleUseCase.execute(id, new UpdateScheduleUseCase.Input(
                req.name(), req.cronExpression(), req.contextUrl()));
    }

    @PatchMapping("/{id}/status")
    public ToggleScheduleStatusUseCase.Output updateStatus(@PathVariable String id,
                                                            @Valid @RequestBody UpdateScheduleStatusRequest req) {
        return toggleScheduleStatusUseCase.execute(id, req.enabled());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        deleteScheduleUseCase.execute(id);
    }
}
```

<a id="arquivo-126"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/advice/GlobalExceptionHandler.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.advice;

import com.matheusjfa.yggdrasil.domain.rule.RuleException;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(RuleException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return Map.of("error", errors);
    }

    @ExceptionHandler(ScheduleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleScheduleNotFound(ScheduleException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
```

<a id="arquivo-127"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/CreateRuleRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

import com.matheusjfa.yggdrasil.domain.rule.ChainConfiguration;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateRuleRequest(
    @NotBlank String name,
    String description,
    @Min(1) int priority,
    @NotNull ConditionNode condition,
    @NotNull List<RuleAction> actions,
    List<ChainConfiguration> chains
) {}
```

<a id="arquivo-128"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/CreateScheduleRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateScheduleRequest(
        @NotBlank String name,
        @NotBlank String cronExpression,
        @NotBlank String contextUrl,
        boolean enabled
) {}
```

<a id="arquivo-129"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/EvaluateRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record EvaluateRequest(@NotNull Map<String, Object> context) {}
```

<a id="arquivo-130"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/TestRuleRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record TestRuleRequest(@NotNull Map<String, Object> context) {}
```

<a id="arquivo-131"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateRuleRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

import com.matheusjfa.yggdrasil.domain.rule.ChainConfiguration;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionNode;
import jakarta.validation.constraints.Min;
import java.util.List;

public record UpdateRuleRequest(
    String name,
    String description,
    @Min(1) Integer priority,
    ConditionNode condition,
    List<RuleAction> actions,
    List<ChainConfiguration> chains
) {}
```

<a id="arquivo-132"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateScheduleRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

public record UpdateScheduleRequest(String name, String cronExpression, String contextUrl) {}
```

<a id="arquivo-133"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateScheduleStatusRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

public record UpdateScheduleStatusRequest(boolean enabled) {}
```

<a id="arquivo-134"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/rest/dto/UpdateStatusRequest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest.dto;

import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull RuleStatus status) {}
```

<a id="arquivo-135"></a>

### `src/main/java/com/matheusjfa/yggdrasil/infrastructure/scheduler/ScheduleRunner.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.scheduler;

import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.application.usecase.schedule.CreateScheduleUseCase;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleRunner {

    private final ScheduleRepository scheduleRepository;
    private final EvaluateContextUseCase evaluateContextUseCase;
    private final RestTemplate restTemplate;

    @Scheduled(fixedDelay = 30_000)
    public void run() {
        var due = scheduleRepository.findEnabledDue(Instant.now());
        for (var schedule : due) {
            try {
                fireSchedule(schedule);
            } catch (Exception e) {
                log.error("Failed to fire schedule {}: {}", schedule.getId(), e.getMessage());
            }
        }
    }

    private void fireSchedule(Schedule schedule) {
        var context = fetchContext(schedule.getContextUrl());
        evaluateContextUseCase.execute(
                new EvaluateContextUseCase.Input(context, ExecutionOrigin.SCHEDULED, false));
        var nextFireAt = CreateScheduleUseCase.computeNextFireAt(schedule.getCronExpression());
        schedule.markFired(nextFireAt);
        scheduleRepository.save(schedule);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchContext(String url) {
        var response = restTemplate.getForObject(url, Map.class);
        return response != null ? response : Map.of();
    }
}
```

<a id="arquivo-136"></a>

### `src/main/resources/application.yml`

```yaml
spring:
  application:
    name: yggdrasil
  threads:
    virtual:
      enabled: true
  data:
    mongodb:
      uri: ${MONGODB_URI:mongodb://localhost:27017/yggdrasil}
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: yggdrasil-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "com.matheusjfa.yggdrasil.messaging.dto"
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${AUTH0_ISSUER_URI:https://dev-placeholder.auth0.com/}

yggdrasil:
  cache:
    rules-ttl-seconds: 60
  kafka:
    topics:
      evaluate-request: yggdrasil.evaluate.request
      evaluate-response: yggdrasil.evaluate.response

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html

management:
  endpoints:
    web:
      exposure:
        include: prometheus,health
  endpoint:
    prometheus:
      enabled: true
  tracing:
    sampling:
      probability: 1.0
  otlp:
    tracing:
      endpoint: ${TEMPO_ENDPOINT:http://localhost:4317}

logging:
  pattern:
    level: "%5p [${spring.application.name},%X{traceId:-},%X{spanId:-},%X{userId:-}]"
```

<a id="arquivo-137"></a>

### `src/main/resources/logback-spring.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <springProperty scope="context" name="appName" source="spring.application.name" defaultValue="yggdrasil"/>
    <springProperty scope="context" name="lokiUrl" source="loki.url" defaultValue="http://localhost:3100"/>

    <!-- Console appender for local dev -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %5p [${appName},%X{traceId:-},%X{spanId:-},%X{userId:-}] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Loki appender -->
    <appender name="LOKI" class="com.github.loki4j.logback.Loki4jAppender">
        <http>
            <url>${lokiUrl}/loki/api/v1/push</url>
        </http>
        <format>
            <label>
                <pattern>app=${appName},level=%level,host=${HOSTNAME:-local}</pattern>
            </label>
            <message>
                <pattern>traceId=%X{traceId:-none} spanId=%X{spanId:-none} userId=%X{userId:-anonymous} level=%level logger=%logger{36} | %msg%ex</pattern>
            </message>
            <sortByTime>true</sortByTime>
        </format>
        <batchMaxItems>1000</batchMaxItems>
        <batchTimeoutMs>1000</batchTimeoutMs>
    </appender>

    <!-- Use CONSOLE always; LOKI only outside test profile -->
    <springProfile name="!test">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="LOKI"/>
        </root>
    </springProfile>

    <springProfile name="test">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>
</configuration>
```

<a id="arquivo-138"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/service/RuleForestServiceTest.java`

```java
package com.matheusjfa.yggdrasil.application.service;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleForestServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleForestService ruleForestService;

    @Test
    void buildForest_single_rule_with_no_chains_is_root() {
        var rule = Rule.create("Root", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());

        when(ruleRepository.findAllActive()).thenReturn(List.of(rule));

        var forest = ruleForestService.buildForest();

        assertThat(forest).hasSize(1);
        assertThat(forest.get(0).getRule().getName()).isEqualTo("Root");
        assertThat(forest.get(0).getEdges()).isEmpty();
    }

    @Test
    void buildForest_resolves_child_via_chain() {
        var leaf = Rule.create("Leaf", "", 2,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        var root = Rule.create("Root", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of(new ChainConfiguration(leaf.getId(), Map.of())));

        when(ruleRepository.findAllActive()).thenReturn(List.of(leaf, root));

        var forest = ruleForestService.buildForest();

        assertThat(forest).hasSize(1);
        assertThat(forest.get(0).getRule().getName()).isEqualTo("Root");
        assertThat(forest.get(0).getEdges()).hasSize(1);
        assertThat(forest.get(0).getEdges().get(0).child().getRule().getName()).isEqualTo("Leaf");
    }

    @Test
    void buildForest_ignores_unknown_targetRuleId() {
        var rule = Rule.create("Root", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of(new ChainConfiguration("nonexistent-id", Map.of())));

        when(ruleRepository.findAllActive()).thenReturn(List.of(rule));

        var forest = ruleForestService.buildForest();

        assertThat(forest).hasSize(1);
        assertThat(forest.get(0).getEdges()).isEmpty();
    }

    @Test
    void buildForest_multiple_roots_sorted_by_priority() {
        var r1 = Rule.create("High", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"), List.of(), List.of());
        var r2 = Rule.create("Low", "", 5,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"), List.of(), List.of());

        when(ruleRepository.findAllActive()).thenReturn(List.of(r2, r1));

        var forest = ruleForestService.buildForest();

        assertThat(forest).hasSize(2);
        assertThat(forest.get(0).getRule().getName()).isEqualTo("High");
        assertThat(forest.get(1).getRule().getName()).isEqualTo("Low");
    }

    @Test
    void buildForest_empty_repository_returns_empty_forest() {
        when(ruleRepository.findAllActive()).thenReturn(List.of());

        var forest = ruleForestService.buildForest();

        assertThat(forest).isEmpty();
    }
}
```

<a id="arquivo-139"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/service/RuleVersionServiceTest.java`

```java
package com.matheusjfa.yggdrasil.application.service;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleVersionServiceTest {

    @Mock private RuleVersionRepository ruleVersionRepository;
    @InjectMocks private RuleVersionService ruleVersionService;

    private static Rule simpleRule() {
        return Rule.create("My Rule", "desc", 1,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(new RuleAction("APPROVE", Map.of())), List.of());
    }

    @Test
    void snapshot_calls_nextVersion_and_saves_with_correct_fields() {
        var rule = simpleRule();
        when(ruleVersionRepository.nextVersion(rule.getId())).thenReturn(3);

        ruleVersionService.snapshot(rule, ChangeType.UPDATE);

        var captor = ArgumentCaptor.forClass(RuleVersion.class);
        verify(ruleVersionRepository).save(captor.capture());
        var saved = captor.getValue();

        assertThat(saved.getRuleId()).isEqualTo(rule.getId());
        assertThat(saved.getVersion()).isEqualTo(3);
        assertThat(saved.getChangeType()).isEqualTo(ChangeType.UPDATE);
        assertThat(saved.getName()).isEqualTo("My Rule");
        assertThat(saved.getStatus()).isEqualTo(RuleStatus.ACTIVE);
    }

    @Test
    void snapshot_uses_version_from_nextVersion() {
        var rule = simpleRule();
        when(ruleVersionRepository.nextVersion(rule.getId())).thenReturn(1);

        ruleVersionService.snapshot(rule, ChangeType.CREATE);

        var captor = ArgumentCaptor.forClass(RuleVersion.class);
        verify(ruleVersionRepository).save(captor.capture());
        assertThat(captor.getValue().getVersion()).isEqualTo(1);
    }

    @Test
    void snapshot_uses_authenticated_user_as_changedBy() {
        var rule = simpleRule();
        when(ruleVersionRepository.nextVersion(rule.getId())).thenReturn(1);

        var auth = new UsernamePasswordAuthenticationToken("user|auth0-123", null, List.of());
        SecurityContextHolder.setContext(new SecurityContextImpl(auth));

        try {
            ruleVersionService.snapshot(rule, ChangeType.CREATE);
        } finally {
            SecurityContextHolder.clearContext();
        }

        var captor = ArgumentCaptor.forClass(RuleVersion.class);
        verify(ruleVersionRepository).save(captor.capture());
        assertThat(captor.getValue().getChangedBy()).isEqualTo("user|auth0-123");
    }

    @Test
    void snapshot_uses_system_when_no_authentication() {
        var rule = simpleRule();
        when(ruleVersionRepository.nextVersion(rule.getId())).thenReturn(1);
        SecurityContextHolder.clearContext();

        ruleVersionService.snapshot(rule, ChangeType.CREATE);

        var captor = ArgumentCaptor.forClass(RuleVersion.class);
        verify(ruleVersionRepository).save(captor.capture());
        assertThat(captor.getValue().getChangedBy()).isEqualTo("system");
    }
}
```

<a id="arquivo-140"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/EvaluateContextUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.evaluation;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import com.matheusjfa.yggdrasil.domain.evaluation.RuleEvaluator;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluateContextUseCaseTest {

    @Mock private RuleForestService ruleForestService;
    @Mock private RuleEvaluator ruleEvaluator;
    @Mock private RuleExecutionRepository executionRepository;
    @Mock private GetMetricsUseCase getMetricsUseCase;

    private SimpleMeterRegistry meterRegistry;
    private EvaluateContextUseCase useCase;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        useCase = new EvaluateContextUseCase(ruleForestService, ruleEvaluator, executionRepository, meterRegistry, getMetricsUseCase);
    }

    private static Rule simpleRule(String name) {
        return Rule.create(name, "", 1,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(new RuleAction("APPROVE", Map.of())), List.of());
    }

    @Test
    void execute_fires_root_when_condition_is_true() {
        var rootNode = new RuleNode(simpleRule("Age Rule"));
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 25), ExecutionOrigin.SYNC, false));

        assertThat(output.hasMatches()).isTrue();
        assertThat(output.firedRules()).hasSize(1);
        assertThat(output.firedRules().get(0).ruleName()).isEqualTo("Age Rule");
    }

    @Test
    void execute_returns_no_matches_when_root_condition_is_false() {
        var rule = Rule.create("Age Rule", "", 1,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(), List.of());
        var rootNode = new RuleNode(rule);
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(false);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 16), ExecutionOrigin.SYNC, false));

        assertThat(output.hasMatches()).isFalse();
        assertThat(output.firedRules()).isEmpty();
    }

    @Test
    void execute_fires_child_when_both_conditions_are_true() {
        var childRule = Rule.create("Child Rule", "", 2,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "y"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
        var parentRule = Rule.create("Parent Rule", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "y"),
                List.of(new RuleAction("APPROVE", Map.of())),
                List.of(new ChainConfiguration(childRule.getId(), Map.of())));

        var childNode = new RuleNode(childRule);
        var parentNode = new RuleNode(parentRule);
        parentNode.addEdge(new ChainConfiguration(childRule.getId(), Map.of()), childNode);

        when(ruleForestService.buildForest()).thenReturn(List.of(parentNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("x", "y"), ExecutionOrigin.SYNC, false));

        assertThat(output.firedRules()).hasSize(2);
        assertThat(output.firedRules().get(0).ruleName()).isEqualTo("Parent Rule");
        assertThat(output.firedRules().get(1).ruleName()).isEqualTo("Child Rule");
    }

    @Test
    void execute_does_not_fire_child_when_child_condition_is_false() {
        var childRule = Rule.create("Child Rule", "", 2,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "y"),
                List.of(), List.of());
        var parentRule = Rule.create("Parent Rule", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "y"),
                List.of(new RuleAction("APPROVE", Map.of())),
                List.of(new ChainConfiguration(childRule.getId(), Map.of())));

        var childNode = new RuleNode(childRule);
        var parentNode = new RuleNode(parentRule);
        parentNode.addEdge(new ChainConfiguration(childRule.getId(), Map.of()), childNode);

        when(ruleForestService.buildForest()).thenReturn(List.of(parentNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true).thenReturn(false);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("x", "y"), ExecutionOrigin.SYNC, false));

        assertThat(output.firedRules()).hasSize(1);
        assertThat(output.firedRules().get(0).ruleName()).isEqualTo("Parent Rule");
    }

    @Test
    void execute_detects_cycle_via_visited_set() {
        var rule = Rule.create("Rule", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        var node = new RuleNode(rule);
        node.addEdge(new ChainConfiguration(rule.getId(), Map.of()), node);

        when(ruleForestService.buildForest()).thenReturn(List.of(node));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("x", "v"), ExecutionOrigin.SYNC, false));

        assertThat(output.firedRules()).hasSize(1);
    }

    @Test
    void execute_dryRun_skips_persistence_and_returns_null_executionId() {
        var rootNode = new RuleNode(simpleRule("Age Rule"));
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 25), ExecutionOrigin.SYNC, true));

        assertThat(output.executionId()).isNull();
        assertThat(output.hasMatches()).isTrue();
        verify(executionRepository, never()).save(any());
    }

    @Test
    void execute_notDryRun_persists_and_returns_executionId() {
        var rootNode = new RuleNode(simpleRule("Age Rule"));
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 25), ExecutionOrigin.SYNC, false));

        assertThat(output.executionId()).isNotNull();
        verify(executionRepository).save(any());
    }

    @Test
    void execute_notDryRun_increments_counters_and_evicts_cache() {
        var rootNode = new RuleNode(simpleRule("Age Rule"));
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 25), ExecutionOrigin.SYNC, false));

        assertThat(meterRegistry.counter("yggdrasil.evaluations.total").count()).isEqualTo(1.0);
        assertThat(meterRegistry.find("yggdrasil.rules.fired").counters()).isNotEmpty();
        verify(getMetricsUseCase).evict();
    }

    @Test
    void execute_dryRun_does_not_increment_counters_or_evict_cache() {
        var rootNode = new RuleNode(simpleRule("Age Rule"));
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);

        useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 25), ExecutionOrigin.SYNC, true));

        assertThat(meterRegistry.find("yggdrasil.evaluations.total").counter()).isNull();
        verify(getMetricsUseCase, never()).evict();
    }

    @Test
    void execute_records_timer_for_both_dryRun_and_real() {
        var rootNode = new RuleNode(simpleRule("Age Rule"));
        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(false);
        when(executionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 10), ExecutionOrigin.SYNC, false));
        useCase.execute(new EvaluateContextUseCase.Input(Map.of("age", 10), ExecutionOrigin.SYNC, true));

        assertThat(meterRegistry.find("yggdrasil.evaluation.duration").timer().count()).isEqualTo(2L);
    }
}
```

<a id="arquivo-141"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/evaluation/ReplayExecutionUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.evaluation;

import com.matheusjfa.yggdrasil.domain.execution.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplayExecutionUseCaseTest {

    @Mock private RuleExecutionRepository executionRepository;
    @Mock private EvaluateContextUseCase evaluateContextUseCase;
    @InjectMocks private ReplayExecutionUseCase useCase;

    private static RuleExecution pastExecution(Map<String, Object> context) {
        return RuleExecution.reconstitute("past-id", context, List.of(), List.of(), ExecutionOrigin.SYNC, Instant.now());
    }

    @Test
    void replay_notDryRun_delegates_with_REPLAY_origin_and_persists() {
        var past = pastExecution(Map.of("x", 1));
        when(executionRepository.findById("past-id")).thenReturn(Optional.of(past));
        var expected = new EvaluateContextUseCase.Output("new-id", true, List.of(), Map.of("x", 1));
        when(evaluateContextUseCase.execute(argThat(i ->
                i.origin() == ExecutionOrigin.REPLAY && !i.dryRun() && i.context().equals(Map.of("x", 1))
        ))).thenReturn(expected);

        var result = useCase.execute("past-id", false);

        assertThat(result.executionId()).isEqualTo("new-id");
        verify(evaluateContextUseCase).execute(argThat(i -> i.origin() == ExecutionOrigin.REPLAY && !i.dryRun()));
    }

    @Test
    void replay_dryRun_delegates_with_dryRun_true_and_skips_persistence() {
        var past = pastExecution(Map.of("x", 1));
        when(executionRepository.findById("past-id")).thenReturn(Optional.of(past));
        var expected = new EvaluateContextUseCase.Output(null, false, List.of(), Map.of("x", 1));
        when(evaluateContextUseCase.execute(argThat(i ->
                i.origin() == ExecutionOrigin.REPLAY && i.dryRun()
        ))).thenReturn(expected);

        var result = useCase.execute("past-id", true);

        assertThat(result.executionId()).isNull();
        verify(evaluateContextUseCase).execute(argThat(i -> i.origin() == ExecutionOrigin.REPLAY && i.dryRun()));
    }

    @Test
    void replay_throws_when_execution_not_found() {
        when(executionRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("unknown", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unknown");
    }
}
```

<a id="arquivo-142"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/metrics/GetMetricsUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.metrics;

import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMetricsUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleExecutionRepository executionRepository;
    @InjectMocks private GetMetricsUseCase useCase;

    @Test
    void execute_returns_total_executions_and_per_rule_counts() {
        var rule = Rule.create("Discount", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        when(ruleRepository.findAll()).thenReturn(List.of(rule));
        when(executionRepository.countAll()).thenReturn(42L);
        when(executionRepository.countFiredByRuleId(rule.getId())).thenReturn(7L);

        var output = useCase.execute();

        assertThat(output.totalExecutions()).isEqualTo(42L);
        assertThat(output.ruleMetrics()).hasSize(1);
        assertThat(output.ruleMetrics().get(0).ruleName()).isEqualTo("Discount");
        assertThat(output.ruleMetrics().get(0).firedCount()).isEqualTo(7L);
    }

    @Test
    void execute_returns_empty_ruleMetrics_when_no_rules() {
        when(ruleRepository.findAll()).thenReturn(List.of());
        when(executionRepository.countAll()).thenReturn(0L);

        var output = useCase.execute();

        assertThat(output.totalExecutions()).isEqualTo(0L);
        assertThat(output.ruleMetrics()).isEmpty();
    }
}
```

<a id="arquivo-143"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/CreateRuleUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRuleUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleForestService ruleForestService;
    @Mock private RuleVersionService ruleVersionService;
    @InjectMocks private CreateRuleUseCase useCase;

    @Test
    void execute_creates_rule_and_returns_output() {
        var condition = new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18);
        var actions = List.of(new RuleAction("APPROVE", Map.of()));
        var input = new CreateRuleUseCase.Input("Test Rule", "desc", 1, condition, actions, List.of());

        var saved = Rule.create("Test Rule", "desc", 1, condition, actions, List.of());
        when(ruleRepository.save(any())).thenReturn(saved);

        var output = useCase.execute(input);

        assertThat(output.name()).isEqualTo("Test Rule");
        assertThat(output.status()).isEqualTo(RuleStatus.ACTIVE);
        assertThat(output.priority()).isEqualTo(1);
    }

    @Test
    void execute_snapshots_with_CREATE_after_save() {
        var condition = new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18);
        var actions = List.of(new RuleAction("APPROVE", Map.of()));
        var input = new CreateRuleUseCase.Input("Test Rule", "desc", 1, condition, actions, List.of());
        var saved = Rule.create("Test Rule", "desc", 1, condition, actions, List.of());
        when(ruleRepository.save(any())).thenReturn(saved);

        useCase.execute(input);

        verify(ruleVersionService).snapshot(saved, ChangeType.CREATE);
    }
}
```

<a id="arquivo-144"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRuleUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportRuleUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @InjectMocks private ExportRuleUseCase useCase;

    private static Rule rule() {
        return Rule.create("My Rule", "desc", 1,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(new RuleAction("APPROVE", Map.of())), List.of());
    }

    @Test
    void execute_returns_package_with_single_rule_entry() {
        var rule = rule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));

        var pkg = useCase.execute(rule.getId());

        assertThat(pkg.version()).isEqualTo("1.0");
        assertThat(pkg.rules()).hasSize(1);
        assertThat(pkg.rules().get(0).id()).isEqualTo(rule.getId());
        assertThat(pkg.rules().get(0).name()).isEqualTo("My Rule");
        assertThat(pkg.rules().get(0).status()).isEqualTo(RuleStatus.ACTIVE);
    }

    @Test
    void execute_throws_RuleException_when_rule_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing"))
                .isInstanceOf(RuleException.class);
    }
}
```

<a id="arquivo-145"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ExportRulesUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportRulesUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @InjectMocks private ExportRulesUseCase useCase;

    private static Rule rule(String name) {
        return Rule.create(name, "desc", 1,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(new RuleAction("APPROVE", Map.of())), List.of());
    }

    @Test
    void execute_returns_package_with_all_rules() {
        var r1 = rule("Rule A");
        var r2 = rule("Rule B");
        when(ruleRepository.findAll()).thenReturn(List.of(r1, r2));

        var pkg = useCase.execute();

        assertThat(pkg.version()).isEqualTo("1.0");
        assertThat(pkg.rules()).hasSize(2);
        assertThat(pkg.rules()).extracting(RulePackage.RuleEntry::name)
                .containsExactly("Rule A", "Rule B");
    }

    @Test
    void execute_returns_empty_package_when_no_rules() {
        when(ruleRepository.findAll()).thenReturn(List.of());

        var pkg = useCase.execute();

        assertThat(pkg.rules()).isEmpty();
    }
}
```

<a id="arquivo-146"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetForestUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetForestUseCaseTest {

    @Mock
    private RuleForestService ruleForestService;

    @InjectMocks
    private GetForestUseCase useCase;

    @Test
    void execute_returns_empty_when_forest_is_empty() {
        when(ruleForestService.buildForest()).thenReturn(List.of());

        var output = useCase.execute();

        assertThat(output.nodes()).isEmpty();
        assertThat(output.edges()).isEmpty();
    }

    @Test
    void execute_returns_single_node_with_no_edges() {
        var rule = Rule.create("Root", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("ACT", Map.of())), List.of());
        var node = new RuleNode(rule);

        when(ruleForestService.buildForest()).thenReturn(List.of(node));

        var output = useCase.execute();

        assertThat(output.nodes()).hasSize(1);
        assertThat(output.nodes().get(0).id()).isEqualTo(rule.getId());
        assertThat(output.nodes().get(0).label()).isEqualTo("Root");
        assertThat(output.nodes().get(0).priority()).isEqualTo(1);
        assertThat(output.nodes().get(0).status()).isEqualTo("ACTIVE");
        assertThat(output.nodes().get(0).actionsCount()).isEqualTo(1);
        assertThat(output.edges()).isEmpty();
    }

    @Test
    void execute_returns_two_nodes_and_one_edge_for_root_with_child() {
        var childRule = Rule.create("Child", "", 2,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        var rootRule = Rule.create("Root", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());

        var childNode = new RuleNode(childRule);
        var rootNode = new RuleNode(rootRule);
        rootNode.addEdge(new ChainConfiguration(childRule.getId(), Map.of("score", "#ctx['score']")), childNode);

        when(ruleForestService.buildForest()).thenReturn(List.of(rootNode));

        var output = useCase.execute();

        assertThat(output.nodes()).hasSize(2);
        assertThat(output.edges()).hasSize(1);
        assertThat(output.edges().get(0).id()).isEqualTo(rootRule.getId() + "-" + childRule.getId());
        assertThat(output.edges().get(0).source()).isEqualTo(rootRule.getId());
        assertThat(output.edges().get(0).target()).isEqualTo(childRule.getId());
        assertThat(output.edges().get(0).enrichments()).containsEntry("score", "#ctx['score']");
    }

    @Test
    void execute_deduplicates_shared_node_appearing_as_child_of_two_parents() {
        var sharedRule = Rule.create("Shared", "", 3,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        var parent1Rule = Rule.create("Parent1", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        var parent2Rule = Rule.create("Parent2", "", 2,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());

        var sharedNode = new RuleNode(sharedRule);
        var parent1Node = new RuleNode(parent1Rule);
        var parent2Node = new RuleNode(parent2Rule);
        parent1Node.addEdge(new ChainConfiguration(sharedRule.getId(), Map.of()), sharedNode);
        parent2Node.addEdge(new ChainConfiguration(sharedRule.getId(), Map.of()), sharedNode);

        when(ruleForestService.buildForest()).thenReturn(List.of(parent1Node, parent2Node));

        var output = useCase.execute();

        assertThat(output.nodes()).hasSize(3);
        assertThat(output.edges()).hasSize(2);
        long sharedCount = output.nodes().stream()
                .filter(n -> n.id().equals(sharedRule.getId()))
                .count();
        assertThat(sharedCount).isEqualTo(1);
    }
}
```

<a id="arquivo-147"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRuleUseCaseTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private GetRuleUseCase useCase;

    @Test
    void execute_returns_rule_when_found() {
        var rule = Rule.create("My Rule", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, 1),
                List.of(new RuleAction("ACT", Map.of())), List.of());
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));

        var output = useCase.execute(rule.getId());

        assertThat(output.id()).isEqualTo(rule.getId());
        assertThat(output.name()).isEqualTo("My Rule");
    }

    @Test
    void execute_throws_when_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing"))
                .isInstanceOf(RuleException.class)
                .hasMessageContaining("missing");
    }
}
```

<a id="arquivo-148"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/GetRuleVersionUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRuleVersionUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleVersionRepository ruleVersionRepository;
    @InjectMocks private GetRuleVersionUseCase useCase;

    private static Rule rule() {
        return Rule.create("Rule", "desc", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
    }

    @Test
    void execute_returns_full_version_snapshot() {
        var rule = rule();
        var snapshot = RuleVersion.of(rule, 2, ChangeType.UPDATE, "system");
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleIdAndVersion(rule.getId(), 2)).thenReturn(Optional.of(snapshot));

        var result = useCase.execute(rule.getId(), 2);

        assertThat(result.getVersion()).isEqualTo(2);
        assertThat(result.getName()).isEqualTo("Rule");
        assertThat(result.getChangeType()).isEqualTo(ChangeType.UPDATE);
    }

    @Test
    void execute_throws_RuleException_when_rule_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing", 1))
                .isInstanceOf(RuleException.class);
    }

    @Test
    void execute_throws_IllegalArgumentException_when_version_not_found() {
        var rule = rule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleIdAndVersion(rule.getId(), 99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(rule.getId(), 99))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
```

<a id="arquivo-149"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ImportRulesUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportRulesUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @InjectMocks private ImportRulesUseCase useCase;

    private static RulePackage.RuleEntry entry(String id) {
        return new RulePackage.RuleEntry(
                id, "Rule " + id, "desc", 1, RuleStatus.ACTIVE,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(new RuleAction("APPROVE", Map.of())), List.of()
        );
    }

    private static RulePackage pkg(RulePackage.RuleEntry... entries) {
        return new RulePackage("1.0", Instant.now(), List.of(entries));
    }

    @Test
    void skip_strategy_ignores_existing_rule() {
        var entry = entry("existing-id");
        var existing = Rule.reconstitute("existing-id", "Old Name", "desc", 1,
                entry.condition(), entry.actions(), entry.chains(),
                RuleStatus.ACTIVE, Instant.now(), Instant.now());
        when(ruleRepository.findById("existing-id")).thenReturn(Optional.of(existing));

        var result = useCase.execute(pkg(entry), ConflictStrategy.SKIP);

        assertThat(result.skipped()).isEqualTo(1);
        assertThat(result.imported()).isEqualTo(0);
        verify(ruleRepository, never()).save(any());
    }

    @Test
    void overwrite_strategy_updates_existing_rule() {
        var entry = entry("existing-id");
        var existing = Rule.reconstitute("existing-id", "Old Name", "desc", 1,
                entry.condition(), entry.actions(), entry.chains(),
                RuleStatus.ACTIVE, Instant.now(), Instant.now());
        when(ruleRepository.findById("existing-id")).thenReturn(Optional.of(existing));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute(pkg(entry), ConflictStrategy.OVERWRITE);

        assertThat(result.imported()).isEqualTo(1);
        assertThat(result.skipped()).isEqualTo(0);
        verify(ruleRepository).save(existing);
    }

    @Test
    void fail_strategy_throws_RuleException_when_conflict_found() {
        var entry = entry("existing-id");
        var existing = Rule.reconstitute("existing-id", "Old Name", "desc", 1,
                entry.condition(), entry.actions(), entry.chains(),
                RuleStatus.ACTIVE, Instant.now(), Instant.now());
        when(ruleRepository.findById("existing-id")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> useCase.execute(pkg(entry), ConflictStrategy.FAIL))
                .isInstanceOf(RuleException.class);

        verify(ruleRepository, never()).save(any());
    }

    @Test
    void imports_new_rule_with_preserved_id() {
        var entry = entry("new-id");
        when(ruleRepository.findById("new-id")).thenReturn(Optional.empty());
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute(pkg(entry), ConflictStrategy.SKIP);

        assertThat(result.imported()).isEqualTo(1);
        assertThat(result.skipped()).isEqualTo(0);
        verify(ruleRepository).save(argThat(r -> r.getId().equals("new-id")));
    }

    @Test
    void imports_multiple_entries_with_mixed_conflicts() {
        var newEntry = entry("new-id");
        var existingEntry = entry("existing-id");
        var existing = Rule.reconstitute("existing-id", "Old", "desc", 1,
                existingEntry.condition(), existingEntry.actions(), existingEntry.chains(),
                RuleStatus.ACTIVE, Instant.now(), Instant.now());
        when(ruleRepository.findById("new-id")).thenReturn(Optional.empty());
        when(ruleRepository.findById("existing-id")).thenReturn(Optional.of(existing));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute(pkg(newEntry, existingEntry), ConflictStrategy.SKIP);

        assertThat(result.imported()).isEqualTo(1);
        assertThat(result.skipped()).isEqualTo(1);
    }
}
```

<a id="arquivo-150"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/ListRuleVersionsUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListRuleVersionsUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleVersionRepository ruleVersionRepository;
    @InjectMocks private ListRuleVersionsUseCase useCase;

    private static Rule rule() {
        return Rule.create("Rule", "desc", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
    }

    @Test
    void execute_returns_version_summaries_ordered_by_version() {
        var rule = rule();
        var v1 = RuleVersion.of(rule, 1, ChangeType.CREATE, "system");
        var v2 = RuleVersion.of(rule, 2, ChangeType.UPDATE, "system");
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleId(rule.getId())).thenReturn(List.of(v1, v2));

        var result = useCase.execute(rule.getId());

        assertThat(result).hasSize(2);
        assertThat(result.get(0).version()).isEqualTo(1);
        assertThat(result.get(0).changeType()).isEqualTo(ChangeType.CREATE);
        assertThat(result.get(1).version()).isEqualTo(2);
        assertThat(result.get(1).changeType()).isEqualTo(ChangeType.UPDATE);
    }

    @Test
    void execute_throws_RuleException_when_rule_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing"))
                .isInstanceOf(RuleException.class);
    }
}
```

<a id="arquivo-151"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/RestoreRuleVersionUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestoreRuleVersionUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleVersionRepository ruleVersionRepository;
    @Mock private RuleVersionService ruleVersionService;
    @Mock private RuleForestService ruleForestService;
    @Mock private GetMetricsUseCase getMetricsUseCase;
    @InjectMocks private RestoreRuleVersionUseCase useCase;

    private static Rule activeRule() {
        return Rule.create("Current Name", "current desc", 5,
                new ConditionLeaf("a", ComparisonOperator.EQUAL, "b"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
    }

    private static RuleVersion snapshotOf(Rule rule, int version, String snapshotName) {
        return RuleVersion.reconstitute(
                UUID.randomUUID().toString(), rule.getId(), version,
                snapshotName, "snap desc", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("APPROVE", Map.of())), List.of(),
                RuleStatus.ACTIVE, Instant.now(), ChangeType.CREATE, "system"
        );
    }

    @Test
    void execute_applies_snapshot_fields_to_live_rule() {
        var rule = activeRule();
        rule.deactivate();
        var snapshot = snapshotOf(rule, 1, "Old Name");

        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleIdAndVersion(rule.getId(), 1)).thenReturn(Optional.of(snapshot));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(rule.getId(), 1);

        assertThat(output.name()).isEqualTo("Old Name");
        assertThat(output.priority()).isEqualTo(1);
    }

    @Test
    void execute_preserves_current_status() {
        var rule = activeRule();
        rule.deactivate();
        var snapshot = snapshotOf(rule, 1, "Old Name");

        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleIdAndVersion(rule.getId(), 1)).thenReturn(Optional.of(snapshot));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(rule.getId(), 1);

        assertThat(output.status()).isEqualTo(RuleStatus.INACTIVE);
    }

    @Test
    void execute_snapshots_with_RESTORE_and_evicts_caches() {
        var rule = activeRule();
        var snapshot = snapshotOf(rule, 1, "Old Name");

        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleIdAndVersion(rule.getId(), 1)).thenReturn(Optional.of(snapshot));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(rule.getId(), 1);

        verify(ruleVersionService).snapshot(any(Rule.class), eq(ChangeType.RESTORE));
        verify(ruleForestService).evict();
        verify(getMetricsUseCase).evict();
    }

    @Test
    void execute_throws_RuleException_when_rule_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing", 1))
                .isInstanceOf(RuleException.class);
    }

    @Test
    void execute_throws_IllegalArgumentException_when_version_not_found() {
        var rule = activeRule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleVersionRepository.findByRuleIdAndVersion(rule.getId(), 99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(rule.getId(), 99))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
```

<a id="arquivo-152"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/TestRuleUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.domain.evaluation.RuleEvaluator;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestRuleUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleEvaluator ruleEvaluator;
    @InjectMocks private TestRuleUseCase useCase;

    private Rule makeRule() {
        return Rule.reconstitute("rule-1", "Test Rule", "desc", 1,
                new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
                List.of(new RuleAction("APPROVE", Map.of())), List.of(),
                RuleStatus.ACTIVE, Instant.now(), Instant.now());
    }

    @Test
    void execute_returns_fired_true_with_actions_when_condition_matches() {
        when(ruleRepository.findById("rule-1")).thenReturn(Optional.of(makeRule()));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(true);

        var output = useCase.execute("rule-1", Map.of("age", 25));

        assertThat(output.fired()).isTrue();
        assertThat(output.actions()).hasSize(1);
        assertThat(output.actions().get(0).type()).isEqualTo("APPROVE");
    }

    @Test
    void execute_returns_fired_false_with_empty_actions_when_condition_does_not_match() {
        when(ruleRepository.findById("rule-1")).thenReturn(Optional.of(makeRule()));
        when(ruleEvaluator.evaluate(any(), any())).thenReturn(false);

        var output = useCase.execute("rule-1", Map.of("age", 10));

        assertThat(output.fired()).isFalse();
        assertThat(output.actions()).isEmpty();
    }

    @Test
    void execute_throws_when_rule_not_found() {
        when(ruleRepository.findById("bad-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("bad-id", Map.of()))
                .isInstanceOf(RuleException.class)
                .hasMessageContaining("bad-id");
    }
}
```

<a id="arquivo-153"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleStatusUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRuleStatusUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleForestService ruleForestService;
    @Mock private RuleVersionService ruleVersionService;
    @InjectMocks private UpdateRuleStatusUseCase useCase;

    private static Rule activeRule() {
        return Rule.create("My Rule", "desc", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
    }

    @Test
    void execute_deactivates_rule() {
        var rule = activeRule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(rule.getId(), RuleStatus.INACTIVE);

        assertThat(output.status()).isEqualTo(RuleStatus.INACTIVE);
    }

    @Test
    void execute_activates_rule() {
        var rule = activeRule();
        rule.deactivate();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute(rule.getId(), RuleStatus.ACTIVE);

        assertThat(output.status()).isEqualTo(RuleStatus.ACTIVE);
    }

    @Test
    void execute_throws_when_rule_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing", RuleStatus.ACTIVE))
                .isInstanceOf(RuleException.class);
    }

    @Test
    void execute_snapshots_with_STATUS_CHANGE_after_save() {
        var rule = activeRule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(rule.getId(), RuleStatus.INACTIVE);

        verify(ruleVersionService).snapshot(any(Rule.class), eq(ChangeType.STATUS_CHANGE));
    }
}
```

<a id="arquivo-154"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/rule/UpdateRuleUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.rule;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.application.service.RuleVersionService;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRuleUseCaseTest {

    @Mock private RuleRepository ruleRepository;
    @Mock private RuleForestService ruleForestService;
    @Mock private RuleVersionService ruleVersionService;
    @InjectMocks private UpdateRuleUseCase useCase;

    private static Rule existingRule() {
        return Rule.create("Old Name", "old desc", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
    }

    @Test
    void execute_updates_rule_and_returns_output() {
        var rule = existingRule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var input = new UpdateRuleUseCase.Input("New Name", "new desc", 2, null, null, null);
        var output = useCase.execute(rule.getId(), input);

        assertThat(output.name()).isEqualTo("New Name");
        assertThat(output.priority()).isEqualTo(2);
    }

    @Test
    void execute_throws_when_rule_not_found() {
        when(ruleRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("missing",
                new UpdateRuleUseCase.Input("x", null, null, null, null, null)))
                .isInstanceOf(RuleException.class);
    }

    @Test
    void execute_snapshots_with_UPDATE_after_save() {
        var rule = existingRule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(rule.getId(), new UpdateRuleUseCase.Input("New Name", null, null, null, null, null));

        verify(ruleVersionService).snapshot(any(Rule.class), eq(ChangeType.UPDATE));
    }

    @Test
    void execute_evicts_forest_after_save() {
        var rule = existingRule();
        when(ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        useCase.execute(rule.getId(), new UpdateRuleUseCase.Input("New Name", null, null, null, null, null));

        verify(ruleForestService).evict();
    }
}
```

<a id="arquivo-155"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/schedule/CreateScheduleUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateScheduleUseCaseTest {

    @Mock private ScheduleRepository scheduleRepository;
    @InjectMocks private CreateScheduleUseCase useCase;

    @Test
    void execute_creates_and_returns_schedule() {
        when(scheduleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        var input = new CreateScheduleUseCase.Input("Daily", "0 0 0 * * *", "http://ctx/daily", true);

        var output = useCase.execute(input);

        assertThat(output.name()).isEqualTo("Daily");
        assertThat(output.enabled()).isTrue();
        assertThat(output.nextFireAt()).isNotNull();
    }

    @Test
    void execute_throws_for_invalid_cron() {
        var input = new CreateScheduleUseCase.Input("Bad", "not-a-cron", "http://ctx", true);

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not-a-cron");
    }
}
```

<a id="arquivo-156"></a>

### `src/test/java/com/matheusjfa/yggdrasil/application/usecase/schedule/ToggleScheduleStatusUseCaseTest.java`

```java
package com.matheusjfa.yggdrasil.application.usecase.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleException;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToggleScheduleStatusUseCaseTest {

    @Mock private ScheduleRepository scheduleRepository;
    @InjectMocks private ToggleScheduleStatusUseCase useCase;

    private Schedule makeSchedule(boolean enabled) {
        return Schedule.reconstitute("s-1", "Test", "0 0 * * * *", "http://ctx",
                enabled, null, Instant.now().plusSeconds(3600), Instant.now(), Instant.now());
    }

    @Test
    void execute_disables_an_enabled_schedule() {
        when(scheduleRepository.findById("s-1")).thenReturn(Optional.of(makeSchedule(true)));
        when(scheduleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute("s-1", false);

        assertThat(output.enabled()).isFalse();
    }

    @Test
    void execute_enables_a_disabled_schedule() {
        when(scheduleRepository.findById("s-1")).thenReturn(Optional.of(makeSchedule(false)));
        when(scheduleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var output = useCase.execute("s-1", true);

        assertThat(output.enabled()).isTrue();
    }

    @Test
    void execute_throws_when_schedule_not_found() {
        when(scheduleRepository.findById("bad-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute("bad-id", false))
                .isInstanceOf(ScheduleException.class)
                .hasMessageContaining("bad-id");
    }
}
```

<a id="arquivo-157"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/engine/SpelGeneratorTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.engine;

import com.matheusjfa.yggdrasil.domain.rule.condition.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SpelGeneratorTest {

    private final SpelGenerator generator = new SpelGenerator();

    @Test
    void leaf_equality_generates_spel() {
        var leaf = new ConditionLeaf("age", ComparisonOperator.EQUAL, 18);
        assertThat(generator.generate(leaf)).isEqualTo("#age == 18");
    }

    @Test
    void leaf_string_equality_wraps_in_quotes() {
        var leaf = new ConditionLeaf("country", ComparisonOperator.EQUAL, "BR");
        assertThat(generator.generate(leaf)).isEqualTo("#country == 'BR'");
    }

    @Test
    void leaf_greater_than_generates_spel() {
        var leaf = new ConditionLeaf("score", ComparisonOperator.GREATER_THAN, 700);
        assertThat(generator.generate(leaf)).isEqualTo("#score > 700");
    }

    @Test
    void leaf_less_than_generates_spel() {
        var leaf = new ConditionLeaf("score", ComparisonOperator.LESS_THAN, 700);
        assertThat(generator.generate(leaf)).isEqualTo("#score < 700");
    }

    @Test
    void leaf_greater_than_or_equal_generates_spel() {
        var leaf = new ConditionLeaf("score", ComparisonOperator.GREATER_THAN_OR_EQUAL, 700);
        assertThat(generator.generate(leaf)).isEqualTo("#score >= 700");
    }

    @Test
    void leaf_less_than_or_equal_generates_spel() {
        var leaf = new ConditionLeaf("score", ComparisonOperator.LESS_THAN_OR_EQUAL, 700);
        assertThat(generator.generate(leaf)).isEqualTo("#score <= 700");
    }

    @Test
    void leaf_not_equal_generates_spel() {
        var leaf = new ConditionLeaf("status", ComparisonOperator.NOT_EQUAL, "BLOCKED");
        assertThat(generator.generate(leaf)).isEqualTo("#status != 'BLOCKED'");
    }

    @Test
    void leaf_contains_generates_spel() {
        var leaf = new ConditionLeaf("name", ComparisonOperator.CONTAINS, "john");
        assertThat(generator.generate(leaf)).isEqualTo("#name.contains('john')");
    }

    @Test
    void leaf_starts_with_generates_spel() {
        var leaf = new ConditionLeaf("email", ComparisonOperator.STARTS_WITH, "admin");
        assertThat(generator.generate(leaf)).isEqualTo("#email.startsWith('admin')");
    }

    @Test
    void spel_condition_passes_through_expression() {
        var spel = new SpelCondition("#age > 18 && #country == 'BR'");
        assertThat(generator.generate(spel)).isEqualTo("#age > 18 && #country == 'BR'");
    }

    @Test
    void and_group_joins_children_with_and() {
        var group = new ConditionGroup(LogicalOperator.AND, List.of(
            new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
            new ConditionLeaf("score", ComparisonOperator.GREATER_THAN_OR_EQUAL, 700)
        ));
        assertThat(generator.generate(group)).isEqualTo("(#age > 18) && (#score >= 700)");
    }

    @Test
    void or_group_joins_children_with_or() {
        var group = new ConditionGroup(LogicalOperator.OR, List.of(
            new ConditionLeaf("score", ComparisonOperator.GREATER_THAN_OR_EQUAL, 700),
            new ConditionLeaf("vip", ComparisonOperator.EQUAL, true)
        ));
        assertThat(generator.generate(group)).isEqualTo("(#score >= 700) || (#vip == true)");
    }

    @Test
    void leaf_string_with_single_quote_is_escaped() {
        var leaf = new ConditionLeaf("name", ComparisonOperator.EQUAL, "O'Brien");
        assertThat(generator.generate(leaf)).isEqualTo("#name == 'O\\'Brien'");
    }

    @Test
    void leaf_is_null_generates_spel() {
        var leaf = new ConditionLeaf("field", ComparisonOperator.IS_NULL, null);
        assertThat(generator.generate(leaf)).isEqualTo("#field == null");
    }

    @Test
    void leaf_is_not_null_generates_spel() {
        var leaf = new ConditionLeaf("field", ComparisonOperator.IS_NOT_NULL, null);
        assertThat(generator.generate(leaf)).isEqualTo("#field != null");
    }

    @Test
    void leaf_in_generates_spel_or_chain() {
        var leaf = new ConditionLeaf("status", ComparisonOperator.IN, List.of("ACTIVE", "PENDING"));
        assertThat(generator.generate(leaf)).isEqualTo("(#status == 'ACTIVE' || #status == 'PENDING')");
    }

    @Test
    void leaf_not_in_generates_spel_and_chain() {
        var leaf = new ConditionLeaf("status", ComparisonOperator.NOT_IN, List.of("BLOCKED", "BANNED"));
        assertThat(generator.generate(leaf)).isEqualTo("(#status != 'BLOCKED' && #status != 'BANNED')");
    }

    @Test
    void leaf_in_with_numbers_generates_spel() {
        var leaf = new ConditionLeaf("score", ComparisonOperator.IN, List.of(1, 2, 3));
        assertThat(generator.generate(leaf)).isEqualTo("(#score == 1 || #score == 2 || #score == 3)");
    }

    @Test
    void leaf_is_between_generates_spel() {
        var leaf = new ConditionLeaf("age", ComparisonOperator.IS_BETWEEN, List.of(18, 65));
        assertThat(generator.generate(leaf)).isEqualTo("(#age >= 18 && #age <= 65)");
    }

    @Test
    void leaf_last_month_generates_spel() {
        var leaf = new ConditionLeaf("createdAt", ComparisonOperator.LAST_MONTH, null);
        assertThat(generator.generate(leaf)).isEqualTo(
            "(#createdAt != null && #createdAt.year == #__now__.minusMonths(1).year && #createdAt.monthValue == #__now__.minusMonths(1).monthValue)"
        );
    }

    @Test
    void leaf_last_n_days_generates_spel() {
        var leaf = new ConditionLeaf("createdAt", ComparisonOperator.LAST_N_DAYS, 7);
        assertThat(generator.generate(leaf)).isEqualTo(
            "(#createdAt != null && !#createdAt.isBefore(#__now__.minusDays(7)) && !#createdAt.isAfter(#__now__))"
        );
    }

    @Test
    void nested_group_generates_spel() {
        var inner = new ConditionGroup(LogicalOperator.OR, List.of(
            new ConditionLeaf("score", ComparisonOperator.GREATER_THAN_OR_EQUAL, 700),
            new SpelCondition("#vip == true")
        ));
        var outer = new ConditionGroup(LogicalOperator.AND, List.of(
            new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
            inner
        ));
        assertThat(generator.generate(outer))
            .isEqualTo("(#age > 18) && ((#score >= 700) || (#vip == true))");
    }
}
```

<a id="arquivo-158"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/persistence/execution/RuleExecutionDocumentMapperTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.execution;

import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.execution.FiredRule;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecution;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RuleExecutionDocumentMapperTest {

    private final RuleExecutionDocumentMapper mapper = new RuleExecutionDocumentMapperImpl();

    @Test
    void toDocument_maps_all_fields() {
        var context = Map.<String, Object>of("age", 25, "score", 80);
        var firedRules = List.of(new FiredRule("rule-1", "My Rule", List.of()));
        var execution = RuleExecution.record(context, firedRules, List.of(), ExecutionOrigin.SYNC);

        var doc = mapper.toDocument(execution);

        assertThat(doc.getId()).isEqualTo(execution.getId());
        assertThat(doc.getInputContext()).containsEntry("age", 25);
        assertThat(doc.getFiredRules()).hasSize(1);
        assertThat(doc.getOrigin()).isEqualTo(ExecutionOrigin.SYNC);
        assertThat(doc.getExecutedAt()).isEqualTo(execution.getExecutedAt());
    }

    @Test
    void toDomain_roundtrip_preserves_all_fields() {
        var context = Map.<String, Object>of("field", "value");
        var execution = RuleExecution.record(context, List.of(), List.of(), ExecutionOrigin.SCHEDULED);

        var doc = mapper.toDocument(execution);
        var restored = mapper.toDomain(doc);

        assertThat(restored.getId()).isEqualTo(execution.getId());
        assertThat(restored.getOrigin()).isEqualTo(ExecutionOrigin.SCHEDULED);
        assertThat(restored.getExecutedAt()).isEqualTo(execution.getExecutedAt());
        assertThat(restored.getInputContext()).containsEntry("field", "value");
    }
}
```

<a id="arquivo-159"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/persistence/rule/RuleDocumentMapperTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.rule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.RuleStatus;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({RuleDocumentMapperImpl.class, RuleDocumentMapperTest.Config.class})
class RuleDocumentMapperTest {

    @Autowired
    private RuleDocumentMapper mapper;

    @TestConfiguration
    static class Config {
        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper().registerModule(new JavaTimeModule());
        }
    }

    @Test
    void toDocument_maps_all_fields() {
        var condition = new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18);
        var actions = List.of(new RuleAction("APPROVE", Map.of()));
        var rule = com.matheusjfa.yggdrasil.domain.rule.Rule.create(
                "Price Rule", "desc", 5, condition, actions, List.of());

        var doc = mapper.toDocument(rule);

        assertThat(doc.getId()).isEqualTo(rule.getId());
        assertThat(doc.getName()).isEqualTo("Price Rule");
        assertThat(doc.getDescription()).isEqualTo("desc");
        assertThat(doc.getPriority()).isEqualTo(5);
        assertThat(doc.getConditionJson()).contains("age").contains("GREATER_THAN");
        assertThat(doc.getActions()).hasSize(1);
        assertThat(doc.getStatus()).isEqualTo(RuleStatus.ACTIVE);
    }

    @Test
    void toDomain_roundtrip_preserves_condition() {
        var condition = new ConditionLeaf("score", ComparisonOperator.EQUAL, 100);
        var rule = com.matheusjfa.yggdrasil.domain.rule.Rule.create(
                "Score Rule", "", 1, condition, List.of(), List.of());

        var doc = mapper.toDocument(rule);
        var restored = mapper.toDomain(doc);

        assertThat(restored.getId()).isEqualTo(rule.getId());
        assertThat(restored.getName()).isEqualTo("Score Rule");
        assertThat(restored.getCondition()).isInstanceOf(ConditionLeaf.class);
        var leaf = (ConditionLeaf) restored.getCondition();
        assertThat(leaf.field()).isEqualTo("score");
        assertThat(leaf.operator()).isEqualTo(ComparisonOperator.EQUAL);
    }
}
```

<a id="arquivo-160"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/persistence/schedule/ScheduleDocumentMapperTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.persistence.schedule;

import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleDocumentMapperTest {

    private final ScheduleDocumentMapper mapper = new ScheduleDocumentMapperImpl();

    @Test
    void toDocument_maps_all_fields() {
        var nextFire = Instant.parse("2026-06-01T10:00:00Z");
        var schedule = Schedule.create("nightly", "0 0 * * *", "http://host/ctx", true, nextFire);

        var doc = mapper.toDocument(schedule);

        assertThat(doc.getId()).isEqualTo(schedule.getId());
        assertThat(doc.getName()).isEqualTo("nightly");
        assertThat(doc.getCronExpression()).isEqualTo("0 0 * * *");
        assertThat(doc.getContextUrl()).isEqualTo("http://host/ctx");
        assertThat(doc.isEnabled()).isTrue();
        assertThat(doc.getNextFireAt()).isEqualTo(nextFire);
        assertThat(doc.getLastFiredAt()).isNull();
    }

    @Test
    void toDomain_roundtrip_preserves_all_fields() {
        var nextFire = Instant.parse("2026-06-01T10:00:00Z");
        var schedule = Schedule.create("nightly", "0 0 * * *", "http://host/ctx", false, nextFire);

        var doc = mapper.toDocument(schedule);
        var restored = mapper.toDomain(doc);

        assertThat(restored.getId()).isEqualTo(schedule.getId());
        assertThat(restored.getName()).isEqualTo("nightly");
        assertThat(restored.getCronExpression()).isEqualTo("0 0 * * *");
        assertThat(restored.isEnabled()).isFalse();
        assertThat(restored.getNextFireAt()).isEqualTo(nextFire);
    }
}
```

<a id="arquivo-161"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/EvaluationControllerTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user|test")
@Import(SecurityConfiguration.class)
@WebMvcTest(EvaluationController.class)
class EvaluationControllerTest {

    @MockitoBean private JwtDecoder jwtDecoder;
    @MockitoBean private EvaluateContextUseCase evaluateContextUseCase;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private static final EvaluateContextUseCase.Output DUMMY_OUTPUT =
            new EvaluateContextUseCase.Output("exec-1", true, List.of(), Map.of());

    @Test
    void evaluate_without_param_passes_dryRun_false() throws Exception {
        when(evaluateContextUseCase.execute(argThat(i -> !i.dryRun() && i.origin() == ExecutionOrigin.SYNC)))
                .thenReturn(DUMMY_OUTPUT);

        mockMvc.perform(post("/rules/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("context", Map.of("k", "v")))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.executionId").value("exec-1"));
    }

    @Test
    void evaluate_with_dryRun_true_passes_dryRun_true() throws Exception {
        when(evaluateContextUseCase.execute(argThat(i -> i.dryRun() && i.origin() == ExecutionOrigin.SYNC)))
                .thenReturn(new EvaluateContextUseCase.Output(null, true, List.of(), Map.of()));

        mockMvc.perform(post("/rules/evaluate")
                        .param("dryRun", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("context", Map.of("k", "v")))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.executionId").doesNotExist());
    }

    @Test
    @WithAnonymousUser
    void evaluate_returns_401_without_authentication() throws Exception {
        mockMvc.perform(post("/rules/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"facts\":{}}"))
                .andExpect(status().isUnauthorized());
    }
}
```

<a id="arquivo-162"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/ExecutionControllerTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.application.usecase.evaluation.ReplayExecutionUseCase;
import com.matheusjfa.yggdrasil.application.usecase.execution.GetExecutionUseCase;
import com.matheusjfa.yggdrasil.application.usecase.execution.ListExecutionsUseCase;
import com.matheusjfa.yggdrasil.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user|test")
@Import(SecurityConfiguration.class)
@WebMvcTest(ExecutionController.class)
class ExecutionControllerTest {

    @MockitoBean private JwtDecoder jwtDecoder;
    @MockitoBean private ListExecutionsUseCase listExecutionsUseCase;
    @MockitoBean private GetExecutionUseCase getExecutionUseCase;
    @MockitoBean private ReplayExecutionUseCase replayExecutionUseCase;
    @Autowired private MockMvc mockMvc;

    @Test
    void replay_without_param_passes_dryRun_false() throws Exception {
        var output = new EvaluateContextUseCase.Output("new-id", true, List.of(), Map.of());
        when(replayExecutionUseCase.execute(eq("abc"), eq(false))).thenReturn(output);

        mockMvc.perform(post("/rules/executions/abc/replay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.executionId").value("new-id"));
    }

    @Test
    void replay_with_dryRun_true_passes_dryRun_true() throws Exception {
        var output = new EvaluateContextUseCase.Output(null, false, List.of(), Map.of());
        when(replayExecutionUseCase.execute(eq("abc"), eq(true))).thenReturn(output);

        mockMvc.perform(post("/rules/executions/abc/replay").param("dryRun", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.executionId").doesNotExist());
    }

    @Test
    void replay_unknown_id_returns_400() throws Exception {
        when(replayExecutionUseCase.execute(eq("bad-id"), eq(false)))
                .thenThrow(new IllegalArgumentException("Execution not found: bad-id"));

        mockMvc.perform(post("/rules/executions/bad-id/replay"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Execution not found: bad-id"));
    }

    @Test
    @WithAnonymousUser
    void getExecution_returns_401_without_authentication() throws Exception {
        mockMvc.perform(get("/rules/executions/some-id"))
                .andExpect(status().isUnauthorized());
    }
}
```

<a id="arquivo-163"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/MetricsControllerTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import com.matheusjfa.yggdrasil.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user|test")
@Import(SecurityConfiguration.class)
@WebMvcTest(MetricsController.class)
class MetricsControllerTest {

    @MockitoBean private JwtDecoder jwtDecoder;
    @MockitoBean private GetMetricsUseCase getMetricsUseCase;
    @Autowired private MockMvc mockMvc;

    @Test
    void metrics_returns_200_with_totalExecutions_and_ruleMetrics() throws Exception {
        var ruleMetrics = List.of(new GetMetricsUseCase.RuleMetric("rule-1", "Discount Rule", 42L));
        when(getMetricsUseCase.execute()).thenReturn(new GetMetricsUseCase.Output(100L, ruleMetrics));

        mockMvc.perform(get("/rules/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalExecutions").value(100))
                .andExpect(jsonPath("$.ruleMetrics[0].ruleName").value("Discount Rule"))
                .andExpect(jsonPath("$.ruleMetrics[0].firedCount").value(42));
    }

    @Test
    void metrics_returns_empty_ruleMetrics_when_none() throws Exception {
        when(getMetricsUseCase.execute()).thenReturn(new GetMetricsUseCase.Output(0L, List.of()));

        mockMvc.perform(get("/rules/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalExecutions").value(0))
                .andExpect(jsonPath("$.ruleMetrics").isArray())
                .andExpect(jsonPath("$.ruleMetrics").isEmpty());
    }

    @Test
    @WithAnonymousUser
    void metrics_returns_401_without_authentication() throws Exception {
        mockMvc.perform(get("/rules/metrics"))
                .andExpect(status().isUnauthorized());
    }
}
```

<a id="arquivo-164"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleControllerForestTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.rule.*;
import com.matheusjfa.yggdrasil.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user|test")
@Import(SecurityConfiguration.class)
@WebMvcTest(RuleController.class)
class RuleControllerForestTest {

    @MockitoBean private JwtDecoder jwtDecoder;
    @MockitoBean private CreateRuleUseCase createRuleUseCase;
    @MockitoBean private GetRuleUseCase getRuleUseCase;
    @MockitoBean private ListRulesUseCase listRulesUseCase;
    @MockitoBean private UpdateRuleUseCase updateRuleUseCase;
    @MockitoBean private UpdateRuleStatusUseCase updateRuleStatusUseCase;
    @MockitoBean private DeleteRuleUseCase deleteRuleUseCase;
    @MockitoBean private TestRuleUseCase testRuleUseCase;
    @MockitoBean private GetForestUseCase getForestUseCase;
    @MockitoBean private ListRuleVersionsUseCase listRuleVersionsUseCase;
    @MockitoBean private GetRuleVersionUseCase getRuleVersionUseCase;
    @MockitoBean private RestoreRuleVersionUseCase restoreRuleVersionUseCase;

    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getForest_returns_200_with_nodes_and_edges() throws Exception {
        var nodes = List.of(new GetForestUseCase.NodeDto("abc", "Root", 1, "ACTIVE", 2));
        var edges = List.of(new GetForestUseCase.EdgeDto("abc-def", "abc", "def", Map.of()));
        when(getForestUseCase.execute()).thenReturn(new GetForestUseCase.Output(nodes, edges));

        mockMvc.perform(get("/rules/forest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodes").isArray())
                .andExpect(jsonPath("$.nodes[0].id").value("abc"))
                .andExpect(jsonPath("$.nodes[0].label").value("Root"))
                .andExpect(jsonPath("$.nodes[0].priority").value(1))
                .andExpect(jsonPath("$.nodes[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$.nodes[0].actionsCount").value(2))
                .andExpect(jsonPath("$.edges").isArray())
                .andExpect(jsonPath("$.edges[0].id").value("abc-def"))
                .andExpect(jsonPath("$.edges[0].source").value("abc"))
                .andExpect(jsonPath("$.edges[0].target").value("def"));
    }

    @Test
    void getForest_returns_empty_arrays_when_forest_is_empty() throws Exception {
        when(getForestUseCase.execute()).thenReturn(new GetForestUseCase.Output(List.of(), List.of()));

        mockMvc.perform(get("/rules/forest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodes").isArray())
                .andExpect(jsonPath("$.nodes").isEmpty())
                .andExpect(jsonPath("$.edges").isEmpty());
    }

    @Test
    @WithAnonymousUser
    void getForest_returns_401_without_authentication() throws Exception {
        mockMvc.perform(get("/rules/forest"))
                .andExpect(status().isUnauthorized());
    }
}
```

<a id="arquivo-165"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/rest/RuleControllerVersionTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.rest;

import com.matheusjfa.yggdrasil.application.usecase.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.*;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.domain.rule.version.ChangeType;
import com.matheusjfa.yggdrasil.domain.rule.version.RuleVersion;
import com.matheusjfa.yggdrasil.infrastructure.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user|test")
@Import(SecurityConfiguration.class)
@WebMvcTest(RuleController.class)
class RuleControllerVersionTest {

    @MockitoBean private JwtDecoder jwtDecoder;
    @MockitoBean private CreateRuleUseCase createRuleUseCase;
    @MockitoBean private GetRuleUseCase getRuleUseCase;
    @MockitoBean private ListRulesUseCase listRulesUseCase;
    @MockitoBean private UpdateRuleUseCase updateRuleUseCase;
    @MockitoBean private UpdateRuleStatusUseCase updateRuleStatusUseCase;
    @MockitoBean private DeleteRuleUseCase deleteRuleUseCase;
    @MockitoBean private TestRuleUseCase testRuleUseCase;
    @MockitoBean private GetForestUseCase getForestUseCase;
    @MockitoBean private ListRuleVersionsUseCase listRuleVersionsUseCase;
    @MockitoBean private GetRuleVersionUseCase getRuleVersionUseCase;
    @MockitoBean private RestoreRuleVersionUseCase restoreRuleVersionUseCase;

    @Autowired private MockMvc mockMvc;

    @Test
    void get_history_returns_200_with_version_list() throws Exception {
        var summary = new ListRuleVersionsUseCase.VersionSummary("v1", 1, ChangeType.CREATE, Instant.now());
        when(listRuleVersionsUseCase.execute("rule-1")).thenReturn(List.of(summary));

        mockMvc.perform(get("/rules/rule-1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].version").value(1))
                .andExpect(jsonPath("$[0].changeType").value("CREATE"));
    }

    @Test
    void get_history_version_returns_200_with_full_snapshot() throws Exception {
        var rule = Rule.create("Rule", "desc", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(new RuleAction("LOG", Map.of())), List.of());
        var snapshot = RuleVersion.of(rule, 2, ChangeType.UPDATE, "system");
        when(getRuleVersionUseCase.execute("rule-1", 2)).thenReturn(snapshot);

        mockMvc.perform(get("/rules/rule-1/history/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.changeType").value("UPDATE"))
                .andExpect(jsonPath("$.name").value("Rule"));
    }

    @Test
    void post_restore_returns_200_with_restored_rule() throws Exception {
        var condition = new ConditionLeaf("x", ComparisonOperator.EQUAL, "v");
        var output = new UpdateRuleUseCase.Output(
                UUID.randomUUID().toString(), "Old Name", "desc", 1,
                condition, List.of(new RuleAction("LOG", Map.of())), List.of(),
                RuleStatus.ACTIVE, Instant.now(), Instant.now());
        when(restoreRuleVersionUseCase.execute("rule-1", 1)).thenReturn(output);

        mockMvc.perform(post("/rules/rule-1/restore/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Old Name"));
    }

    @Test
    @WithAnonymousUser
    void getHistory_returns_401_without_authentication() throws Exception {
        mockMvc.perform(get("/rules/some-id/history"))
                .andExpect(status().isUnauthorized());
    }
}
```

<a id="arquivo-166"></a>

### `src/test/java/com/matheusjfa/yggdrasil/infrastructure/scheduler/ScheduleRunnerTest.java`

```java
package com.matheusjfa.yggdrasil.infrastructure.scheduler;

import com.matheusjfa.yggdrasil.application.usecase.evaluation.EvaluateContextUseCase;
import com.matheusjfa.yggdrasil.domain.execution.ExecutionOrigin;
import com.matheusjfa.yggdrasil.domain.schedule.Schedule;
import com.matheusjfa.yggdrasil.domain.schedule.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleRunnerTest {

    @Mock private ScheduleRepository scheduleRepository;
    @Mock private EvaluateContextUseCase evaluateContextUseCase;
    @Mock private RestTemplate restTemplate;
    @InjectMocks private ScheduleRunner runner;

    private Schedule makeDueSchedule() {
        return Schedule.reconstitute("s-1", "Test", "0 0 * * * *", "http://ctx/context",
                true, null, Instant.now().minusSeconds(1), Instant.now(), Instant.now());
    }

    @Test
    @SuppressWarnings("unchecked")
    void run_fires_due_schedules_and_updates_lastFiredAt() {
        var schedule = makeDueSchedule();
        when(scheduleRepository.findEnabledDue(any())).thenReturn(List.of(schedule));
        when(restTemplate.getForObject(eq("http://ctx/context"), eq(Map.class)))
                .thenReturn(Map.of("key", "value"));
        when(evaluateContextUseCase.execute(any()))
                .thenReturn(new EvaluateContextUseCase.Output("exec-1", false, List.of(), Map.of()));
        when(scheduleRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        runner.run();

        var captor = ArgumentCaptor.forClass(EvaluateContextUseCase.Input.class);
        verify(evaluateContextUseCase).execute(captor.capture());
        assertThat(captor.getValue().origin()).isEqualTo(ExecutionOrigin.SCHEDULED);
        assertThat(captor.getValue().context()).containsKey("key");
        verify(scheduleRepository).save(argThat(s -> s.getLastFiredAt() != null));
    }

    @Test
    @SuppressWarnings("unchecked")
    void run_skips_schedule_and_does_not_throw_when_context_fetch_fails() {
        var schedule = makeDueSchedule();
        when(scheduleRepository.findEnabledDue(any())).thenReturn(List.of(schedule));
        when(restTemplate.getForObject(eq("http://ctx/context"), eq(Map.class)))
                .thenThrow(new RestClientException("timeout"));

        assertThatNoException().isThrownBy(() -> runner.run());
        verify(evaluateContextUseCase, never()).execute(any());
        verify(scheduleRepository, never()).save(any());
    }
}
```

<a id="arquivo-167"></a>

### `src/test/java/com/matheusjfa/yggdrasil/integration/MetricsCacheIntegrationTest.java`

```java
package com.matheusjfa.yggdrasil.integration;

import com.matheusjfa.yggdrasil.application.usecase.metrics.GetMetricsUseCase;
import com.matheusjfa.yggdrasil.domain.execution.RuleExecutionRepository;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationRequestConsumer;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationResponseProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@Testcontainers
class MetricsCacheIntegrationTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @MockitoBean JwtDecoder jwtDecoder;
    @MockitoBean EvaluationRequestConsumer evaluationRequestConsumer;
    @MockitoBean EvaluationResponseProducer evaluationResponseProducer;
    @MockitoBean RuleRepository ruleRepository;
    @MockitoBean RuleExecutionRepository executionRepository;

    @Autowired GetMetricsUseCase getMetricsUseCase;

    @AfterEach
    void resetCache() {
        getMetricsUseCase.evict();
    }

    @Test
    void execute_is_cached_on_second_call() {
        when(ruleRepository.findAll()).thenReturn(List.of());
        when(executionRepository.countAll()).thenReturn(0L);

        getMetricsUseCase.execute();
        getMetricsUseCase.execute();

        verify(executionRepository, times(1)).countAll();
    }

    @Test
    void evict_clears_cache_so_next_call_hits_repository() {
        when(ruleRepository.findAll()).thenReturn(List.of());
        when(executionRepository.countAll()).thenReturn(0L);

        getMetricsUseCase.execute();
        getMetricsUseCase.evict();
        getMetricsUseCase.execute();

        verify(executionRepository, times(2)).countAll();
    }
}
```

<a id="arquivo-168"></a>

### `src/test/java/com/matheusjfa/yggdrasil/integration/RuleEvaluationIntegrationTest.java`

```java
package com.matheusjfa.yggdrasil.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusjfa.yggdrasil.domain.rule.RuleAction;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationRequestConsumer;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationResponseProducer;
import com.matheusjfa.yggdrasil.infrastructure.persistence.rule.MongoRuleRepository;
import com.matheusjfa.yggdrasil.infrastructure.rest.dto.CreateRuleRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@AutoConfigureMockMvc
@Testcontainers
class RuleEvaluationIntegrationTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoRuleRepository mongoRuleRepository;

    @Autowired
    private CacheManager cacheManager;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private EvaluationRequestConsumer evaluationRequestConsumer;

    @MockitoBean
    private EvaluationResponseProducer evaluationResponseProducer;

    @AfterEach
    void cleanup() {
        mongoRuleRepository.deleteAll();
        var cache = cacheManager.getCache("active-rules");
        if (cache != null) cache.clear();
    }

    @Test
    @WithMockUser(username = "user|test")
    void create_rule_then_evaluate_matching_context_returns_match() throws Exception {
        var createRequest = new CreateRuleRequest(
            "Age Rule", "Approve adults", 1,
            new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
            List.of(new RuleAction("APPROVE", Map.of())), null
        );

        mockMvc.perform(post("/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Age Rule"));

        mockMvc.perform(post("/rules/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "context": { "age": 25 } }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hasMatches").value(true))
            .andExpect(jsonPath("$.firedRules[0].ruleName").value("Age Rule"));
    }

    @Test
    @WithMockUser(username = "user|test")
    void evaluate_non_matching_context_returns_no_matches() throws Exception {
        var createRequest = new CreateRuleRequest(
            "Age Rule", "", 1,
            new ConditionLeaf("age", ComparisonOperator.GREATER_THAN, 18),
            List.of(new RuleAction("APPROVE", Map.of())), null
        );

        mockMvc.perform(post("/rules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/rules/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "context": { "age": 16 } }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.hasMatches").value(false))
            .andExpect(jsonPath("$.firedRules").isEmpty());
    }
}
```

<a id="arquivo-169"></a>

### `src/test/java/com/matheusjfa/yggdrasil/integration/RuleForestCacheIntegrationTest.java`

```java
package com.matheusjfa.yggdrasil.integration;

import com.matheusjfa.yggdrasil.application.service.RuleForestService;
import com.matheusjfa.yggdrasil.domain.rule.Rule;
import com.matheusjfa.yggdrasil.domain.rule.RuleRepository;
import com.matheusjfa.yggdrasil.domain.rule.condition.ComparisonOperator;
import com.matheusjfa.yggdrasil.domain.rule.condition.ConditionLeaf;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationRequestConsumer;
import com.matheusjfa.yggdrasil.infrastructure.messaging.EvaluationResponseProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
@Testcontainers
class RuleForestCacheIntegrationTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @MockitoBean JwtDecoder jwtDecoder;
    @MockitoBean EvaluationRequestConsumer evaluationRequestConsumer;
    @MockitoBean EvaluationResponseProducer evaluationResponseProducer;
    @MockitoBean RuleRepository ruleRepository;

    @Autowired
    RuleForestService ruleForestService;

    @BeforeEach
    void setup() {
        var rule = Rule.create("Test Rule", "", 1,
                new ConditionLeaf("x", ComparisonOperator.EQUAL, "v"),
                List.of(), List.of());
        when(ruleRepository.findAllActive()).thenReturn(List.of(rule));
    }

    @AfterEach
    void resetCache() {
        ruleForestService.evict();
    }

    @Test
    void buildForest_is_cached_on_second_call() {
        ruleForestService.buildForest();
        ruleForestService.buildForest();

        verify(ruleRepository, times(1)).findAllActive();
    }

    @Test
    void evict_clears_cache_so_next_call_hits_repository() {
        ruleForestService.buildForest();
        ruleForestService.evict();
        ruleForestService.buildForest();

        verify(ruleRepository, times(2)).findAllActive();
    }
}
```

<a id="arquivo-170"></a>

### `src/test/resources/application-test.properties`

```properties
spring.kafka.bootstrap-servers=localhost:9092
```

<a id="arquivo-171"></a>

### `src/test/resources/docker-java.properties`

```properties
api.version=1.44
```

<a id="arquivo-172"></a>

### `src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker`

```text
mock-maker-subclass
```

