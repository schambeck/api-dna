APP = dna-web
VERSION = 0.0.1-SNAPSHOT
JAR = target/${APP}-${VERSION}.jar

DOCKER_IMAGE = ${APP}:latest
DOCKER_FOLDER = src/main/docker
DOCKER_CONF = ${DOCKER_FOLDER}/Dockerfile
COMPOSE_CONF = ${DOCKER_FOLDER}/docker-compose.yml
REPLICAS = 2

AB_FOLDER = ab-results
AB_TIME = 10
AB_CONCURRENCY = 5
BASE_URL = http://localhost:8080
STATS_ENDPOINT = ${BASE_URL}/mutant/stats
CREATE_ENDPOINT = ${BASE_URL}/mutant
PAYLOAD = ${AB_FOLDER}/dna-10.json

# Maven

clean:
	./mvnw clean

all: clean
	./mvnw compile

install: clean
	./mvnw install

check: clean
	./mvnw verify

check-unit: clean
	./mvnw test

check-integration: clean
	./mvnw integration-test

dist: clean
	./mvnw package -Dmaven.test.skip=true

dist-run: dist run

run:
	java -jar ${JAR}

# Docker

dist-docker-build: dist docker-build

docker-build:
	docker build -f ${DOCKER_CONF} -t ${APP}:latest --build-arg=JAR_FILE=${JAR} .

docker-run:
	docker run -p8080:8080 ${APP}:latest

--rm-docker-image:
	docker rmi ${DOCKER_IMAGE}

docker-bash:
	docker exec -it docker_web_1 /bin/bash

# Docker Compose

dist-compose-up: dist compose-up

compose-up:
	docker-compose -f ${COMPOSE_CONF} up -d --build --scale web=${REPLICAS}

compose-down: --compose-down

compose-down-rmi: --compose-down --rm-docker-image

--compose-down:
	docker-compose -f ${COMPOSE_CONF} down

compose-logs:
	docker-compose -f ${COMPOSE_CONF} logs -f \web

# Apache Benchmark

ab-run-stats:
	ab -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${STATS_ENDPOINT} > ${AB_FOLDER}/stats-c${AB_CONCURRENCY}.txt

ab-run-create:
	ab -p ${PAYLOAD} -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${CREATE_ENDPOINT} > ${AB_FOLDER}/create-c${AB_CONCURRENCY}.txt
