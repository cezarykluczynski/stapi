#!/bin/sh
touch /tmp/postgres.log
/usr/local/bin/docker-entrypoint-pg.sh postgres > /tmp/postgres.log &
while true ; do
  result=$(grep -nE 'PostgreSQL init process complete; ready for start up' /tmp/postgres.log)
  echo "Waiting for the DB to start..."
  if [[ "$result" == *"ready"* ]]; then
    echo "DB started."
    break
  fi
  sleep 1
done
java -Dspring.profiles.active=default,docker,stapi-custom \
  -Dstapi.datasource.main.password=$POSTGRES_PASSWORD \
  -jar /app.war
