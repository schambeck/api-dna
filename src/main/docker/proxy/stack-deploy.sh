#!/bin/bash
echo "Deploying stack srv-proxy..."
docker stack deploy -c docker-compose.yml srv-proxy
echo "Stack deployed!"
