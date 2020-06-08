FROM openjdk:8-jre-slim

WORKDIR /root

COPY ./build/libs/server-0.0.1-SNAPSHOT.jar .

CMD java -jar server-0.0.1-SNAPSHOT.jar .