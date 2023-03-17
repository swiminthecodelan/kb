FROM eclipse-temurin:17-jdk-alpine
WORKDIR /console
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /console-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh", "-c", "java -jar /console-0.0.1-SNAPSHOT.jar ${0} ${@}"]

EXPOSE 3000