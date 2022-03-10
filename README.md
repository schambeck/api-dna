# DNA
[![maven](https://github.com/schambeck/dna/actions/workflows/maven.yml/badge.svg)](https://github.com/schambeck/dna/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/schambeck/dna/branch/main/graph/badge.svg?token=7YX6TXBH4M)](https://codecov.io/gh/schambeck/dna)

## Mutant Detector

### Build artifact

    ./mvnw clean package

Executable file generated: target/api-dna-0.0.1-SNAPSHOT.jar

### Execute application

    java -jar api-dna-0.0.1-SNAPSHOT.jar

## API Deployment

### Heroku

Base URL: https://dna-rest.herokuapp.com

## REST API

The REST API to the DNA app is described below.

### Get Stats

#### Request

`GET /mutant/stats`

    curl https://dna-rest.herokuapp.com/mutant/stats

#### Response

    Status: 200 OK

```json
{"ratio":0.50,"count_mutant_dna":50,"count_human_dna":100}
```

### Create a Mutant DNA

#### Request

`POST /mutant`

    curl -d '{"dna": ["AAAATT","CCCGGG","AAATTT","CCCGGG","AAATTT","CCCGGG"]}' -H "Content-Type: application/json" -X POST https://dna-rest.herokuapp.com/mutant

#### Response

    Status: 200 OK

```json
{"id": "32a14bcc-fdf2-472a-876d-e04a57438edb"}
```

### Create a Human DNA

#### Request

`POST /mutant`

    curl -d '{"dna": ["AAATTT","CCCGGG","AAATTT","CCCGGG","AAATTT","CCCGGG"]}' -H "Content-Type: application/json" -X POST https://dna-rest.herokuapp.com/mutant

#### Response

    Status: 403 Forbidden OK

```json
{"id": "32a14bcc-fdf2-472a-876d-e04a57438edb"}

```


