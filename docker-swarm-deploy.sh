#!/bin/bash
echo "Deploying api-dna application to the Docker Swarm..."
docker stack deploy -c <(cd src/main/docker && docker-compose config) api-dna
echo "Application deployed!"
