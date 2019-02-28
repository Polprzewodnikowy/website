#!/bin/bash

./gradlew bootJar
docker-compose build
