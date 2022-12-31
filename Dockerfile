FROM postgres:15.1-alpine3.17
RUN apk update && apk upgrade && apk add openjdk17-jre
ARG STAPI_DATA_VERSION=$STAPI_DATA_VERSION
ENV POSTGRES_PASSWORD=postgres86
ENV POSTGRES_DB=stapi
ENV STAPI_DATA_VERSION $STAPI_DATA_VERSION
COPY server/build/libs/stapi.war app.war
COPY data/postgres.sql /docker-entrypoint-initdb.d/
COPY contract/ /contract/
COPY server/src/main/resources/form.docx /
COPY docker/startup.sh /startup.sh
ENTRYPOINT ["./startup.sh"]
