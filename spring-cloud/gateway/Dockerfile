FROM adoptopenjdk:8-jdk-hotspot
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} gateway.jar

# ENVIRONMENT
ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

EXPOSE 8000
ENTRYPOINT ["java","-jar","/gateway.jar"]