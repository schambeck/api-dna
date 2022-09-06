#!/bin/bash
echo "Deploying stack srv..."
docker stack deploy -c docker-compose-srv.yml --with-registry-auth srv
echo "Stack deployed!"
