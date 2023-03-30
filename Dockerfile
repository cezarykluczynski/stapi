FROM postgres:15.2-alpine3.17
RUN apk update && apk upgrade && apk add openjdk17-jre
ARG STAPI_DATA_VERSION=$STAPI_DATA_VERSION
ENV POSTGRES_PASSWORD=postgres86
ENV POSTGRES_DB=stapi
ENV STAPI_DATA_VERSION $STAPI_DATA_VERSION
COPY server/build/libs/stapi.war app.war
COPY data/postgres.sql /docker-entrypoint-initdb.d/
COPY contract/ /contract/
COPY server/src/main/resources/form.docx /
RUN mv /usr/local/bin/docker-entrypoint.sh /usr/local/bin/docker-entrypoint-pg.sh
COPY docker/docker-entrypoint.sh /usr/local/bin/
