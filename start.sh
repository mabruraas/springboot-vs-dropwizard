#!/usr/bin/env bash

network="sb_vs_dw"

image_dw="mab/tech_dw"
image_sb="mab/tech_sb"

container_dw="dw"
container_sb="sb"

remove_old_containers() {
  echo "Removing remaining containers"

  docker rm -f \
    ${container_dw} \
    ${container_sb}

  echo "Removed old containers"
}

remove_network() {
  echo "Removing existing network"

  docker network remove ${network}

  echo "Removed old network"
}

create_network() {
  echo "Creating new network"

  remove_network
  docker network create ${network}

  echo "Network created"
}

build_services() {
  echo "Building project"

  docker run --rm \
    -v "${PWD}":"/usr/src/maven" \
    -v "${HOME}/.m2":"/var/maven/.m2" \
    -e MAVEN_CONFIG="/var/maven/.m2" \
    -w "/usr/src/maven" \
    --user "$(id -u):$(id -g)" \
    maven:3.5-alpine \
    mvn -Duser.home=/var/maven clean package

  echo "Building Docker images"
  docker build -t ${image_dw} ${PWD}/dropwizard
  docker build -t ${image_sb} ${PWD}/springboot

  echo "Services built and packaged"
}

start_dropwizard_service() {
  echo "Starting DropWizard application"

  docker run -d \
    --name="${container_dw}" \
    --net="${network}" \
    -p 7000:8080 \
    ${image_dw}

  echo "DropWizard application started on port 7000"
}

start_springboot_service() {
  echo "Starting SpringBoot application"

  docker run -d \
    --name="${container_sb}" \
    --net="${network}" \
    -p 8000:8080 \
    ${image_sb}

  echo "SpringBoot application started on port 8000"
}


remove_old_containers
create_network
build_services
start_dropwizard_service
start_springboot_service

wait
