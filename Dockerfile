FROM openjdk:8
MAINTAINER Rawin Ngamloet <rawin.ngamloet@outlook.com>

WORKDIR /usr/src/app

ARG JAR_FILE
COPY startup.sh .
COPY target/${JAR_FILE} ./app.jar
RUN chmod 700 startup.sh
ENTRYPOINT [ "./startup.sh" ]