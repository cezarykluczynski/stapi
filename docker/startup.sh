#!/bin/sh
touch /tmp/postgres.log
/usr/local/bin/docker-entrypoint.sh postgres > /tmp/postgres.log &
while true ; do
  result=$(grep -nE 'ready to accept connections' /tmp/postgres.log)
  echo "Waiting for DB to start..."
  if [[ "$result" == *"ready"* ]]; then
    echo "DB started."
    break
  fi
  sleep 1
done
java -Dspring.profiles.active=default,docker,stapi-custom \
  -Dstapi.datasource.main.password=$POSTGRES_PASSWORD \
  -jar /app.war
