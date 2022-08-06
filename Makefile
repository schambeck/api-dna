APP = api-dna
VERSION = 0.0.1-SNAPSHOT
JAR = ${APP}-${VERSION}.jar
TARGET_JAR = target/${JAR}
#JAVA_OPTS = -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m -Dspring.main.lazy-initialization=true -Dspring.config.location=classpath:/application.properties
#JAVA_OPTS = -Dserver.port=8080 -javaagent:new-relic/newrelic.jar
JAVA_OPTS = -Dserver.port=8080

#DOCKER_IMAGE = schambeck.jfrog.io/schambeck-docker/${APP}:latest
DOCKER_IMAGE = ${APP}:latest
DOCKER_FOLDER = src/main/docker
DOCKER_CONF = ${DOCKER_FOLDER}/Dockerfile
COMPOSE_CONF = ${DOCKER_FOLDER}/docker-compose.yml
REPLICAS = 1

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
	java ${JAVA_OPTS} -jar ${TARGET_JAR}

# Docker

dist-docker-build: dist docker-build

docker-build:
	DOCKER_BUILDKIT=1 docker build -f ${DOCKER_CONF} -t ${DOCKER_IMAGE} --build-arg=JAR_FILE=${JAR} target

docker-run:
	docker run -p8080:8080 ${DOCKER_IMAGE}

--rm-docker-image:
	docker rmi ${DOCKER_IMAGE}

docker-bash:
	docker exec -it docker_web_1 /bin/bash

docker-tag:
	docker tag ${APP} ${DOCKER_IMAGE}

docker-push:
	docker push ${DOCKER_IMAGE}

docker-pull:
	docker pull ${DOCKER_IMAGE}

docker-cp-jar:
	cp ${TARGET_JAR} ${DOCKER_FOLDER}

dist-docker-build-cp-jar: dist docker-build docker-cp-jar

# Docker Compose

dist-compose-up: dist compose-up

compose-up:
	docker-compose -f ${COMPOSE_CONF} up -d --build

compose-down: --compose-down

compose-down-rmi: --compose-down --rm-docker-image

--compose-down:
	docker-compose -f ${COMPOSE_CONF} down

compose-logs:
	docker-compose -f ${COMPOSE_CONF} logs -f \web

# Docker Swarm

docker-service-inspect:
	docker service inspect ${APP}

docker-stack-deploy:
	cd ${DOCKER_FOLDER} && docker stack deploy -c <(docker-compose config) ${APP}

dist-docker-build-cp-jar-stack-deploy: dist-docker-build-cp-jar docker-stack-deploy

docker-service-rm-web:
	docker service rm ${APP}_web

docker-service-rm-db:
	docker service rm ${APP}_db

docker-service-rm-nginx:
	docker service rm ${APP}_nginx

docker-service-rm-haproxy:
	docker service rm ${APP}_haproxy

docker-service-rm-eureka:
	docker service rm ${APP}_eureka

docker-service-rm: docker-service-rm-web docker-service-rm-db docker-service-rm-haproxy

# Apache Benchmark

ab-run-stats:
	ab -H "Authorization: Bearer ${TOKEN}" -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${STATS_ENDPOINT} > ${AB_FOLDER}/stats-c${AB_CONCURRENCY}.txt

ab-run-create:
	ab -p ${PAYLOAD} -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${CREATE_ENDPOINT} > ${AB_FOLDER}/create-c${AB_CONCURRENCY}.txt

# HTTPie

httpie-stats:
	http GET ${STATS_ENDPOINT} 'Authorization:Bearer ${TOKEN}'
