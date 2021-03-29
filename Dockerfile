FROM openjdk:11

COPY . /usr/src/app

WORKDIR /usr/src/app

ENTRYPOINT ["java","-jar","-Dspring.config.location=classpath:/application.properties","target/flop-0.0.1-SNAPSHOT.jar"]