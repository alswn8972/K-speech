FROM adoptopenjdk:8-jdk-hotspot
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} eureka.jar

EXPOSE 8761
ENTRYPOINT ["java","-jar","/eureka.jar"]