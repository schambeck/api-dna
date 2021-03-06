# DNA
[![maven](https://github.com/schambeck/api-dna/actions/workflows/maven.yml/badge.svg)](https://github.com/schambeck/api-dna/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/schambeck/api-dna/branch/main/graph/badge.svg?token=7YX6TXBH4M)](https://codecov.io/gh/schambeck/api-dna)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=schambeck_api-dna&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=schambeck_api-dna)

## Mutant Detector

### Tech Stack

- Java 11
- Spring Boot
- OAuth2, Auth0
- PostgreSQL, Flyway
- Swagger
- Caffeine Cache
- Docker Compose
- JUnit 5, Mockito, Testcontainers, JaCoCo
- Spring Boot Admin
- Micrometer, Prometheus

### Related Projects

- Angular: [ui-dna](https://github.com/schambeck/ui-dna)
- Server-sent events: [api-notification](https://github.com/schambeck/api-notification)
- Java Microbenchmark Harness: [api-dna-bench](https://github.com/schambeck/api-dna-bench)
- Spring Boot Admin: [spring-admin](https://github.com/schambeck/admin)

### Build artifact

    ./mvnw clean package

Executable file generated: target/api-dna-0.0.1-SNAPSHOT.jar

### Execute application

    java -jar api-dna-0.0.1-SNAPSHOT.jar

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


