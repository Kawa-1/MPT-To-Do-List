#!/bin/bash

REPO_BACKEND_URL="git@github.com:Kawa-1/MPT-To-Do-List.git"
REPO_FRONTEND_URL="git@github.com:Kawa-1/MPT-To-Do-List-FE.git"

REPO_BACKEND_DIR="/tmp/backend"
REPO_FRONTEND_DIR="/tmp/frontend"

USER_ID=$(id -u)
USER_GID=$(id -g)

docker rm -f $(docker ps -a -q)
docker rmi -f $(docker images -q)

rm -rf "$REPO_BACKEND_DIR"
rm -rf "$REPO_FRONTEND_DIR"

git clone "$REPO_BACKEND_URL" "$REPO_BACKEND_DIR"

git clone "$REPO_FRONTEND_URL" "$REPO_FRONTEND_DIR"

if ! command -v docker compose &> /dev/null
then
    echo "docker-compose could not be found, please install it."
    exit 1
fi

cd "$REPO_BACKEND_DIR/backend"
git checkout dev
docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.9.4-eclipse-temurin-21-alpine  mvn clean install
sudo chown -R $USER_ID:$USER_GID .
cd ..
docker compose up --build -d

cd "$REPO_FRONTEND_DIR"
git checkout test
DOCKER_BUILDKIT=1 docker build -t frontend:1.0.0 "$REPO_FRONTEND_DIR/frontend"
docker run -d --network host --name frontend frontend:1.0.0
