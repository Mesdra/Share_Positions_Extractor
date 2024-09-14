FROM maven:3.8.7-eclipse-temurin-17 AS build

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn package

FROM openjdk:17.0.1-jdk-slim AS run
COPY --from=build /usr/app/target/share_positions_extractor-jar-with-dependencies.jar /app/runner.jar
COPY --from=build /usr/app/target/classes/application.properties /app/application.properties
ADD test.html /app/test.html
RUN apt-get update && apt-get -y install cron
ADD crontabconfig /etc/cron.d/simple-cron
RUN chmod 0644 /etc/cron.d/simple-cron
RUN crontab /etc/cron.d/simple-cron
RUN touch /var/log/cron.log
CMD (cron -f &) && tail -f /var/log/cron.log