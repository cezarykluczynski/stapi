#!/bin/sh
java -Dspring.profiles.active=default,docker,stapi-custom \
  -Dstapi.datasource.main.password=$POSTGRES_PASSWORD \
  -jar /app.war &
./docker-entrypoint.sh postgres
