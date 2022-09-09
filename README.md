# DNA
[![maven](https://github.com/schambeck/api-dna/actions/workflows/maven.yml/badge.svg)](https://github.com/schambeck/api-dna/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/schambeck/api-dna/branch/main/graph/badge.svg?token=7YX6TXBH4M)](https://codecov.io/gh/schambeck/api-dna)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=schambeck_api-dna&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=schambeck_api-dna)

## Mutant Detector

### Tech Stack

- Java 17
- Spring Boot
- Spring Security: OAuth2, SSO, Keycloak
- PostgreSQL, Flyway
- Swagger
- Caffeine Cache
- JUnit 5, Mockito, Testcontainers, JaCoCo
- Spring Boot Admin
- Micrometer, Prometheus
- Docker Swarm Mode: Cluster Managing, Container Orchestration and Routing Mesh
- Traefik: Reverse Proxy, Load Balancer, Auto Service Discovery
- Spring Cloud
  - Service Discovery: Eureka
  - Config Server, Bus AMQP and RabbitMQ
  - Client Side Load Balancer: Ribbon
  - Tracing: Sleuth, Zipkin

### Related Projects

- Angular: [ui-dna](https://github.com/schambeck/ui-dna)
- Server-sent events: [api-notification](https://github.com/schambeck/api-notification)
- Java Microbenchmark Harness: [api-dna-bench](https://github.com/schambeck/api-dna-bench)
- Spring Boot Admin: [spring-admin](https://github.com/schambeck/admin)
- Spring Cloud Discovery: [srv-discovery](https://github.com/schambeck/srv-discovery)
- Spring Cloud Config Server: [srv-config](https://github.com/schambeck/srv-config)
- Keycloak Single Sign-On Authorization: [srv-keycloak](https://github.com/schambeck/srv-keycloak)

### Initialize Swarm

    docker swarm init

### Build artifact

    ./mvnw clean package

Executable file generated: target/api-dna-1.0.0.jar

### Build docker image

    make docker-build
    
### Deploy infra stack

    make stack-srv-deploy
    
### Deploy app stack

    make stack-app-deploy
    
### Swagger

    http://api-dna.localhost

### Actuator

    http://api-dna.localhost/actuator

### Traefik Dashboard

    http://localhost:8080

### Zipkin Web UI

    http://localhost:9411

### Keycloak Admin Console

    http://srv-keycloak.localhost
    User: admin
    Pass: admin

### Config Server

    http://localhost:8888/api-dna/default

### RabbitMQ Web Interface

    http://localhost:15672
    User: guest
    Pass: guest

### Eureka Web UI

    http://localhost:8761

## API Deployment

### Heroku

Base URL: https://sch-api-dna.herokuapp.com

## REST API

The REST API to the DNA app is described below.

### Get Stats

#### Request

`GET /mutant/stats`

    curl https://sch-api-dna.herokuapp.com/mutant/stats

#### Response

    Status: 200 OK

```json
{"ratio":0.50,"count_mutant_dna":50,"count_human_dna":100}
```

### Create a Mutant DNA

#### Request

`POST /mutant`

    curl -d '{"dna": ["AAAATT","CCCGGG","AAATTT","CCCGGG","AAATTT","CCCGGG"]}' -H "Content-Type: application/json" -X POST https://sch-api-dna.herokuapp.com/mutant

#### Response

    Status: 200 OK

```json
{"id": "32a14bcc-fdf2-472a-876d-e04a57438edb"}
```

### Create a Human DNA

#### Request

`POST /mutant`

    curl -d '{"dna": ["AAATTT","CCCGGG","AAATTT","CCCGGG","AAATTT","CCCGGG"]}' -H "Content-Type: application/json" -X POST https://sch-api-dna.herokuapp.com/mutant

#### Response

    Status: 403 Forbidden OK

```json
{"id": "32a14bcc-fdf2-472a-876d-e04a57438edb"}

```
