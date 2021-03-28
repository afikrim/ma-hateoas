FROM openjdk:11

COPY . /usr/src/app

WORKDIR /usr/src/app

RUN ./mvnw -f pom.xml clean install
RUN cp target/flop-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","-Dspring.config.location=classpath:/application.properties","app.jar"]