FROM postgres:10.23-alpine3.16
RUN apk update && apk upgrade && apk add openjdk8-jre
ARG STAPI_DATA_VERSION=$STAPI_DATA_VERSION
ENV POSTGRES_PASSWORD=postgres86
ENV POSTGRES_DB=stapi
ENV STAPI_DATA_VERSION $STAPI_DATA_VERSION
COPY server/build/libs/stapi.war app.war
COPY data/postgres.sql /docker-entrypoint-initdb.d/
COPY contract/ /contract/
COPY docker/startup.sh /startup.sh
ENTRYPOINT ["./startup.sh"]
