#!/bin/bash

echo "start!"
sudo docker build -t springproject:1.0 .
echo "docker build"

DOCKER_APP_NAME=springproject

# "blue"라는 이름을 가진 컨테이너가 없으면 - "" / 있으면 springproject_blue~~~~
EXIST_BLUE=$(/usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps | grep blue)

echo $EXIST_BLUE
echo "now"

# EXIST_BLUE 가 빈칸인지 아닌지로 0과 1 구분
# 없으면 => 블루 빌드 후 올리기 & 그린 내리기
# 있으면 -> 그린 빌드 후 올리기 & 블루 내리기
if [ -z "$EXIST_BLUE" ]; then
    echo "blue up"
    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build

    sleep 10

    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml down
else
    echo "green up"
    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build

    sleep 10

    /usr/local/bin/docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
fi
