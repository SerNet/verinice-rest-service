FROM openjdk:11-jdk

ENV MAVEN_CONFIG="--global-settings /m2/settings.xml"
COPY m2-settings.xml /m2/settings.xml

RUN apt-get update && apt-get -y install postgresql-client python3-requests python3-pytest