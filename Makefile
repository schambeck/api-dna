APP = api-dna
VERSION = 1.0.0
JAR = ${APP}-${VERSION}.jar
TARGET_JAR = target/${JAR}
#JAVA_OPTS = -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m -Dspring.main.lazy-initialization=true -Dspring.config.location=classpath:/application.properties
#JAVA_OPTS = -Dserver.port=8080 -javaagent:new-relic/newrelic.jar
JAVA_OPTS = -Dserver.port=0

DOCKER_IMAGE = ${APP}:latest
DOCKER_FOLDER = src/main/docker
DOCKER_CONF = ${DOCKER_FOLDER}/Dockerfile
COMPOSE_APP_CONF = ${DOCKER_FOLDER}/docker-compose.yml
COMPOSE_SRV_CONF = ${DOCKER_FOLDER}/docker-compose-srv.yml
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

dist-docker-build-deploy: dist docker-build stack-app-deploy

dist-docker-build: dist docker-build

dist-docker-build-push: dist docker-build docker-push

docker-build-push: docker-build docker-push

docker-build:
	DOCKER_BUILDKIT=1 docker build -f ${DOCKER_CONF} -t ${DOCKER_IMAGE} --build-arg=JAR_FILE=${JAR} target

docker-run:
	docker run -d \
		--restart=always \
		--net schambeck-bridge \
		--name ${APP} \
	  	--env SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/dna \
		--env SPRING_DATASOURCE_USERNAME=postgres \
		--env SPRING_DATASOURCE_PASSWORD=postgres \
		--env DISCOVERY_URI=http://srv-discovery:8761/eureka \
		--env AUTH_URI=http://srv-authorization:9000/auth/realms/schambeck \
		--env SPRING_RABBITMQ_HOST=rabbitmq \
		--env SPRING_RABBITMQ_PORT=5672 \
		--env SPRING_RABBITMQ_VIRTUAL_HOST= \
		--env SPRING_RABBITMQ_USERNAME=guest \
		--env SPRING_RABBITMQ_PASSWORD=guest \
		--publish 8080:8080 \
		${DOCKER_IMAGE}

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

# Compose

dist-compose-up: dist compose-up

dist-docker-build-compose-up: dist docker-build compose-up

compose-up:
	docker-compose -p ${APP} -f ${COMPOSE_APP_CONF} up -d --build

compose-down: --compose-down

compose-down-rmi: --compose-down --rm-docker-image

--compose-down:
	docker-compose -p ${APP} -f ${COMPOSE_APP_CONF} down

compose-logs:
	docker-compose -f ${COMPOSE_APP_CONF} logs -f \web

# Swarm

stack-app-deploy:
	docker stack deploy -c ${COMPOSE_APP_CONF} --with-registry-auth ${APP}

stack-srv-deploy:
	docker stack deploy -c ${COMPOSE_SRV_CONF} --with-registry-auth srv

stack-app-rm:
	docker stack rm ${APP}

stack-srv-rm:
	docker stack rm srv

service-logs:
	docker service logs ${APP}_web -f

# Apache Benchmark

ab-run-stats:
	ab -H "Authorization: Bearer ${TOKEN}" -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${STATS_ENDPOINT} > ${AB_FOLDER}/stats-c${AB_CONCURRENCY}.txt

ab-run-create:
	ab -p ${PAYLOAD} -T application/json -t ${AB_TIME} -c ${AB_CONCURRENCY} ${CREATE_ENDPOINT} > ${AB_FOLDER}/create-c${AB_CONCURRENCY}.txt

# Security

CODE =
TOKEN =

auth-create-token:
	http POST 'http://localhost:9000/oauth2/token?grant_type=authorization_code&code=${CODE}&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/api-dna-client-oidc' \
		Authorization:'Basic YXJ0aWNsZXMtY2xpZW50OnNlY3JldA=='

auth0-create-token:
	curl POST https://dev-lug9ug4n.us.auth0.com/oauth/token \
	  Content-type:'application/json' \
	  --data '{"client_id":"sC15uRS2NZh0M9UOQgNI8YbXM50aSiRa","client_secret":"mmgZ7gk9RPo2dkWAAmZ1K6I6wHnTXDApQKyAswlV-FyWlsTmCMK8ejoLumSjJHVt","audience":"http://dna-schambeck.herokuapp.com","grant_type":"client_credentials"}'

# HTTPie

stats-get:
	http GET ${STATS_ENDPOINT} 'Authorization:Bearer ${TOKEN}'

mutant-create:
	http POST ${CREATE_ENDPOINT} 'Authorization:Bearer ${TOKEN}' < ${PAYLOAD}
