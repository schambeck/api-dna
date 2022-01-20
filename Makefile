APP = dna-web
VERSION = 0.0.1-SNAPSHOT
JAR = target/${APP}-${VERSION}.jar

DOCKER_FOLDER = src/main/docker
DOCKER_CONF = ${DOCKER_FOLDER}/docker-compose.yml
DOCKER_IMAGE = ${APP}:latest
REPLICAS = 2

AB_FOLDER = ab-results
AB_TIME = 10
AB_CONCURRENCY = 5
BASE_URL = http://localhost:8080
STATS_ENDPOINT = ${BASE_URL}/mutant/stats
CREATE_ENDPOINT = ${BASE_URL}/mutant
PAYLOAD = ${AB_FOLDER}/dna-10.json

clean:
	./mvnw clean

all: clean
	./mvnw compile

install: clean
	./mvnw install

check: clean
	./mvnw verify

dist: clean
	./mvnw package -DskipTests

dist-run: dist run

run:
	java -jar ${JAR}

dist-start-docker: dist --copy-jar-docker start-docker

--copy-jar-docker:
	cp ${JAR} ${DOCKER_FOLDER}

start-docker:
	docker-compose -f ${DOCKER_CONF} up -d --build --scale web=${REPLICAS}

stop-docker: --docker-down

stop-rm-docker: --docker-down --rm-docker-image

--docker-down:
	docker-compose -f ${DOCKER_CONF} down

--rm-docker-image:
	docker rmi ${DOCKER_IMAGE}

docker-bash:
	docker exec -it docker_web_1 /bin/bash

docker-logs:
	docker-compose -f ${DOCKER_CONF} logs -f \web

ab-run-stats:
	ab -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${STATS_ENDPOINT} > ${AB_FOLDER}/stats-c${AB_CONCURRENCY}.txt

ab-run-create:
	ab -p ${PAYLOAD} -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${CREATE_ENDPOINT} > ${AB_FOLDER}/create-c${AB_CONCURRENCY}.txt
