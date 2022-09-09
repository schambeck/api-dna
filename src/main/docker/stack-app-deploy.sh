#!/bin/bash
echo "Deploying stack api-dna..."
docker stack deploy -c docker-stack-app.yml --with-registry-auth api-dna
echo "Stack deployed!"
