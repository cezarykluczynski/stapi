#!/bin/sh
java -Dspring.profiles.active=default,stapi-custom \
  -Dstapi.datasource.main.password=$POSTGRES_PASSWORD \
  -jar /app.war &
./docker-entrypoint.sh postgres
