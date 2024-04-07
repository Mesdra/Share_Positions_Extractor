FROM maven:3.8.7-eclipse-temurin-17 as build

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn package

FROM openjdk:17-oracle as run
COPY --from=build /usr/app/target/share_positions_extractor-jar-with-dependencies.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar