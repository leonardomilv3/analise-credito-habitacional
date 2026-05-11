# analise-credito-habitacional
O objetivo do desafio é desenvolver uma API backend moderna utilizando Java 21 e Quarkus, demonstrando boas práticas de engenharia de software, organização arquitetural e domínio de APIs REST.


# Desafio Técnico

## Contexto

A área de TI da Caixa Econômica Federal deseja modernizar um sistema interno simplificado de gerenciamento de propostas de crédito habitacional.

O objetivo do desafio é desenvolver uma API backend moderna utilizando Java 21 e Quarkus, demonstrando boas práticas de engenharia de software, organização arquitetural e domínio de APIs REST.

---

# Objetivos Avaliados

* Organização arquitetural
* Qualidade de código
* Segurança básica
* Modelagem de domínio
* APIs REST
* Persistência de dados
* Testes
* Dockerização
* Clean Code

---

# Escopo Reduzido

## Obrigatório

* Autenticação JWT
* CRUD de usuários
* CRUD de propostas
* Controle de acesso por perfil
* PostgreSQL
* Flyway
* Validação de regras de negócio
* Histórico de alteração de status
* Testes unitários
* Docker Compose
* Swagger/OpenAPI

## Opcional (Diferencial)

* Kafka/RabbitMQ
* Observabilidade
* CI/CD
* Kubernetes
* Redis
* Testcontainers

---

# Regras Simplificadas

## Perfis

### ADMIN

* Gerencia usuários
* Aprova/reprova propostas

### CLIENTE

* Cria propostas
* Consulta próprias propostas

---

# Fluxo Principal

1. Cliente cria proposta
2. Sistema salva proposta com status PENDENTE
3. ADMIN aprova ou reprova
4. Sistema registra histórico

---

# Stack Obrigatória

## Backend

* Java 21
* Quarkus 3+
* Maven
* RESTEasy Reactive
* Hibernate ORM Panache
* PostgreSQL
* Flyway
* JWT Authentication
* Bean Validation
* MapStruct
* SmallRye OpenAPI
* JUnit 5
* Mockito
* Testcontainers
* Docker
* Docker Compose

---

# Diferenciais Esperados

* Clean Architecture
* DDD estratégico
* Event Driven Architecture
* CQRS (opcional)
* Cache
* Observabilidade
* OpenTelemetry
* Resilience4J/Fault Tolerance
* Kubernetes readiness
* CI/CD
* Rate limiting
* Auditoria
* Soft delete
* Idempotência
* Logs estruturados
* Segurança OWASP
* Feature flags

---

# Objetivo do Sistema

Desenvolver um sistema de gerenciamento de propostas de crédito habitacional.

O sistema deve permitir:

1. Cadastro e autenticação de usuários
2. Criação de propostas de crédito
3. Análise automática de crédito
4. Aprovação/reprovação
5. Histórico completo de movimentações
6. Upload de documentos
7. Consulta paginada e filtrada
8. Auditoria
9. Processamento assíncrono
10. Controle de acesso por perfil


---

# Fluxo Principal

## Fluxo da Proposta

1. Cliente cria proposta
2. Sistema executa análise automática
3. Status muda para EM_ANALISE
4. Analista aprova ou reprova
5. Cliente acompanha evolução
6. Todas movimentações devem ser auditadas

---

# Modelagem de Domínio

## Entidades

---

## User

### Campos

```json
{
  "id": "uuid",
  "name": "string",
  "email": "string",
  "cpf": "string",
  "password": "string hash",
  "role": "ADMIN|ANALISTA|CLIENTE",
  "active": true,
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Regras

* Email único
* CPF único
* Senha criptografada com BCrypt
* Não retornar password em responses

---

## LoanProposal

### Campos

```json
{
  "id": "uuid",
  "customerId": "uuid",
  "propertyValue": 450000,
  "downPayment": 100000,
  "requestedAmount": 350000,
  "monthlyIncome": 18000,
  "installments": 360,
  "status": "PENDENTE",
  "analysisScore": 0,
  "analysisReason": "string",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Status possíveis

```text
PENDENTE
EM_ANALISE
APROVADA
REPROVADA
CANCELADA
```

### Regras

* Entrada mínima de 20%
* Parcela não pode exceder 30% da renda
* Máximo 420 parcelas
* Valor máximo de crédito: R$ 1.500.000
* Apenas CLIENTE pode criar proposta

---

## ProposalHistory

### Campos

```json
{
  "id": "uuid",
  "proposalId": "uuid",
  "previousStatus": "PENDENTE",
  "newStatus": "EM_ANALISE",
  "changedBy": "uuid",
  "reason": "string",
  "createdAt": "timestamp"
}
```

---

## Document

### Campos

```json
{
  "id": "uuid",
  "proposalId": "uuid",
  "fileName": "string",
  "contentType": "application/pdf",
  "size": 102400,
  "storagePath": "string",
  "uploadedAt": "timestamp"
}
```

---

# Arquitetura Esperada

## Estrutura de Pastas

```text
src/main/java
├── application
│   ├── usecase
│   ├── dto
│   ├── mapper
│   ├── service
│   └── exception
│
├── domain
│   ├── entity
│   ├── repository
│   ├── enum
│   └── valueobject
│
├── infrastructure
│   ├── persistence
│   ├── config
│   ├── security
│   ├── client
│   ├── messaging
│   └── observability
│
└── presentation
    ├── controller
    ├── request
    └── response
```

---

# Requisitos Técnicos

## Segurança

### Obrigatório

* JWT
* Refresh token
* BCrypt
* RBAC
* CORS configurável
* Rate limiting
* Input validation
* Sanitização
* Headers de segurança

### JWT Claims Esperadas

```json
{
  "sub": "user-id",
  "email": "user@email.com",
  "role": "ADMIN",
  "iat": 1710000000,
  "exp": 1710003600
}
```

---

# Endpoints Obrigatórios

# AUTH

---

## POST /api/v1/auth/register

### Request

```json
{
  "name": "Leonardo Silva",
  "email": "leo@caixa.gov.br",
  "cpf": "12345678900",
  "password": "Senha@123",
  "role": "CLIENTE"
}
```

### Regras

* Senha mínima 8 caracteres
* Deve conter:

  * letra maiúscula
  * letra minúscula
  * número
  * caractere especial

### Response 201

```json
{
  "id": "c8d5b1c2-6d11-4ef9-b08f-60c8bce11aa1",
  "name": "Leonardo Silva",
  "email": "leo@caixa.gov.br",
  "role": "CLIENTE",
  "createdAt": "2026-05-06T10:00:00Z"
}
```

### Response 409

```json
{
  "message": "Email already exists",
  "timestamp": "2026-05-06T10:00:00Z"
}
```

---

## POST /api/v1/auth/login

### Request

```json
{
  "email": "leo@caixa.gov.br",
  "password": "Senha@123"
}
```

### Response 200

```json
{
  "accessToken": "jwt-token",
  "refreshToken": "refresh-token",
  "expiresIn": 3600,
  "tokenType": "Bearer"
}
```

### Response 401

```json
{
  "message": "Invalid credentials",
  "timestamp": "2026-05-06T10:00:00Z"
}
```

---

## POST /api/v1/auth/refresh

### Request

```json
{
  "refreshToken": "refresh-token"
}
```

### Response 200

```json
{
  "accessToken": "new-jwt-token",
  "expiresIn": 3600
}
```

---

# USERS

---

## GET /api/v1/users/me

### Headers

```text
Authorization: Bearer TOKEN
```

### Response 200

```json
{
  "id": "uuid",
  "name": "Leonardo Silva",
  "email": "leo@caixa.gov.br",
  "cpf": "12345678900",
  "role": "CLIENTE",
  "active": true,
  "createdAt": "2026-05-06T10:00:00Z"
}
```

---

## GET /api/v1/users

### Permissão

ADMIN

### Query Params

```text
?page=0
&size=10
&sort=name
&direction=asc
```

### Response 200

```json
{
  "content": [
    {
      "id": "uuid",
      "name": "Leonardo Silva",
      "email": "leo@caixa.gov.br",
      "role": "CLIENTE"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

---

# PROPOSTAS

---

## POST /api/v1/proposals

### Permissão

CLIENTE

### Request

```json
{
  "propertyValue": 500000,
  "downPayment": 100000,
  "requestedAmount": 400000,
  "monthlyIncome": 18000,
  "installments": 360
}
```

### Regras

* Entrada >= 20%
* Renda mínima proporcional
* Parcelas <= 420
* RequestedAmount = propertyValue - downPayment

### Response 201

```json
{
  "id": "uuid",
  "status": "PENDENTE",
  "analysisScore": 0,
  "createdAt": "2026-05-06T10:00:00Z"
}
```

### Response 422

```json
{
  "message": "Down payment must be at least 20%",
  "field": "downPayment",
  "timestamp": "2026-05-06T10:00:00Z"
}
```

---

## GET /api/v1/proposals/{id}

### Permissão

* CLIENTE → apenas próprias propostas
* ADMIN → todas

### Response 200

```json
{
  "id": "uuid",
  "customer": {
    "id": "uuid",
    "name": "Leonardo Silva"
  },
  "propertyValue": 500000,
  "downPayment": 100000,
  "requestedAmount": 400000,
  "monthlyIncome": 18000,
  "installments": 360,
  "status": "EM_ANALISE",
  "analysisScore": 82,
  "analysisReason": "Boa capacidade de pagamento",
  "createdAt": "2026-05-06T10:00:00Z",
  "updatedAt": "2026-05-06T11:00:00Z"
}
```

---

## GET /api/v1/proposals

### Query Params

```text
?page=0
&size=10
&status=APROVADA
&customerName=Leonardo
&sort=createdAt
&direction=desc
```

### Response 200

```json
{
  "content": [
    {
      "id": "uuid",
      "status": "APROVADA",
      "requestedAmount": 400000,
      "analysisScore": 85,
      "customerName": "Leonardo Silva",
      "createdAt": "2026-05-06T10:00:00Z"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

---

## PATCH /api/v1/proposals/{id}/status

### Permissão

ADMIN

### Request

```json
{
  "status": "APROVADA",
  "reason": "Renda aprovada"
}
```

### Regras

* Apenas EM_ANALISE pode virar APROVADA/REPROVADA
* Deve gerar histórico
* Deve publicar evento

### Response 200

```json
{
  "id": "uuid",
  "previousStatus": "EM_ANALISE",
  "newStatus": "APROVADA",
  "updatedAt": "2026-05-06T10:00:00Z"
}
```

---

## DELETE /api/v1/proposals/{id}

### Regra

Soft delete

### Response 204

Sem corpo.

---

# DOCUMENTOS

---

## POST /api/v1/proposals/{id}/documents

### Content-Type

```text
multipart/form-data
```

### Regras

* Máximo 10MB
* Apenas PDF/JPG/PNG
* Validar MIME type

### Response 201

```json
{
  "documentId": "uuid",
  "fileName": "contracheque.pdf",
  "uploadedAt": "2026-05-06T10:00:00Z"
}
```

---

## GET /api/v1/proposals/{id}/documents

### Response 200

```json
[
  {
    "id": "uuid",
    "fileName": "contracheque.pdf",
    "size": 102400,
    "uploadedAt": "2026-05-06T10:00:00Z"
  }
]
```

---

# HISTÓRICO

---

## GET /api/v1/proposals/{id}/history

### Response 200

```json
[
  {
    "previousStatus": "PENDENTE",
    "newStatus": "EM_ANALISE",
    "changedBy": "Analista XPTO",
    "reason": "Análise iniciada",
    "createdAt": "2026-05-06T10:00:00Z"
  }
]
```

---

# HEALTHCHECK

---

## GET /q/health

### Response

```json
{
  "status": "UP"
}
```

---

# Processamento Assíncrono

## Objetivo

Quando uma proposta for criada:

1. Publicar evento
2. Consumidor executa análise automática
3. Atualiza score
4. Move status para EM_ANALISE

---

## Evento Esperado

```json
{
  "proposalId": "uuid",
  "customerId": "uuid",
  "requestedAmount": 400000,
  "monthlyIncome": 18000,
  "timestamp": "2026-05-06T10:00:00Z"
}
```

---

# Critérios de Análise Automática

## Score

### Exemplo de regra

```text
Renda >= 5x parcela -> +50 pontos
Entrada >= 30% -> +20 pontos
Valor < 500k -> +10 pontos
Parcelas <= 240 -> +20 pontos
```

### Resultado

```text
0-49 -> REPROVADA
50-69 -> EM_ANALISE_MANUAL
70+ -> APROVADA_PARCIAL
```

---

# Banco de Dados

## Obrigatório

* Flyway migrations
* Índices
* Constraints
* Chaves estrangeiras
* Paginação eficiente
* Soft delete
* Auditoria

---

# Exemplo de Migration

```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

---

# Observabilidade

## Logs

### Obrigatório

* Correlation ID
* Structured logging
* Request logging
* Error logging

### Exemplo

```json
{
  "timestamp": "2026-05-06T10:00:00Z",
  "level": "INFO",
  "correlationId": "abc-123",
  "message": "Proposal created",
  "proposalId": "uuid"
}
```

---

# Métricas

## Expor

* Tempo de resposta
* Número de requests
* Taxa de erro
* Uso de memória
* Requests por endpoint

---

# Resiliência

## Aplicar

* Retry
* Timeout
* Circuit breaker
* Bulkhead

---

# Testes Esperados

## Unitários

* Services
* UseCases
* Validators
* Mappers

## Integração

* Controllers
* Repository
* JWT
* Banco real com Testcontainers

## Cobertura

Meta mínima:

```text
80%
```

---

# Casos de Teste Obrigatórios

## Autenticação

* Login válido
* Login inválido
* Token expirado
* Refresh token

## Propostas

* Criar proposta válida
* Entrada menor que 20%
* Cliente acessando proposta de outro cliente
* Aprovação inválida de status

---

# Documentação

## Obrigatório

* README completo
* Swagger/OpenAPI
* Collection Postman/Insomnia
* Docker Compose
* Instruções de execução
* Arquitetura desenhada

---

# Docker Compose Esperado

## Serviços

* app
* postgres
* kafka/rabbitmq (opcional mas diferencial)
* prometheus (diferencial)
* grafana (diferencial)

---

# Requisitos Não Funcionais

## Performance

* P95 < 300ms para consultas
* Paginação obrigatória
* Queries otimizadas

## Segurança

* Nenhum segredo hardcoded
* Uso de variáveis de ambiente
* Proteção contra SQL Injection
* Proteção contra Broken Access Control

## Escalabilidade

* Stateless API
* Ready para Kubernetes
* Health checks
* Graceful shutdown

---

# Diferenciais Avançados

## Muito valorizado

* Kubernetes manifests
* Helm charts
* GitHub Actions
* SonarQube
* Redis cache
* Outbox pattern
* Saga pattern
* Feature toggles
* Multi-stage Docker build
* API versioning
* Contract testing
* Arquitetura hexagonal madura

---

# Estrutura Esperada do README

## Deve conter

```text
1. Visão geral
2. Arquitetura
3. Tecnologias
4. Decisões técnicas
5. Como executar
6. Como rodar testes
7. Endpoints
8. Segurança
9. Observabilidade
10. Melhorias futuras
```

---

# Critérios de Avaliação

## Arquitetura

* Separação de responsabilidades
* Organização do projeto
* Clareza
* Escalabilidade

## Código

* Clean code
* SOLID
* Tratamento de exceções
* Legibilidade

## Segurança

* Implementação JWT
* Controle de acesso
* Boas práticas OWASP

## Banco

* Modelagem
* Índices
* Queries
* Integridade

## Testes

* Qualidade
* Cobertura
* Cenários

## DevOps

* Docker
* Observabilidade
* CI/CD

---

# Estrutura Esperada de Exceções

## Exemplo

```json
{
  "timestamp": "2026-05-06T10:00:00Z",
  "status": 422,
  "error": "Validation Error",
  "message": "Down payment must be at least 20%",
  "path": "/api/v1/proposals",
  "fields": [
    {
      "field": "downPayment",
      "message": "must be greater than 20%"
    }
  ]
}
```

---

# Sugestão de Entregáveis

## Repositório Git

Deve conter:

```text
- Código fonte
- README
- Docker compose
- Collection Postman
- Migrations
- Testes
- Diagramas
```

---

# Bônus

## Frontend simples

Opcional:

* Angular
* React
* Vue

Apenas para consumo da API.

---

# Sugestão de Cronograma de Desenvolvimento

## Dia 1

* Setup do projeto
* Arquitetura base
* Banco + migrations
* Segurança JWT

## Dia 2

* CRUD usuários
* CRUD propostas
* Regras de negócio

## Dia 3

* Mensageria
* Auditoria
* Upload documentos
* Testes

## Dia 4

* Observabilidade
* Docker
* README
* Refatoração final

---

# Extra — O que seria considerado um projeto excelente

Um projeto excelente teria:

* Clean Architecture madura
* Excelente modelagem de domínio
* Testes robustos
* Logs estruturados
* Segurança sólida
* Event driven bem implementado
* Excelente documentação
* Docker profissional
* Código altamente legível
* Padrões modernos do mercado financeiro
* Deploy Kubernetes-ready
* Pipeline CI/CD
* Tratamento avançado de erros
* Performance e observabilidade


