FROM postgres:10.23-alpine3.16
RUN apk update && apk upgrade && apk add openjdk8-jre
ENV POSTGRES_PASSWORD=postgres86
ENV POSTGRES_DB=stapi
COPY server/build/libs/stapi.war app.war
# temporarily:
COPY data/step_40_stapi.sql /docker-entrypoint-initdb.d/
COPY docker/startup.sh /startup.sh
ENTRYPOINT ["./startup.sh"]
