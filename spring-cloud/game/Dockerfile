FROM adoptopenjdk:8-jdk-hotspot
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} game.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/game.jar"]