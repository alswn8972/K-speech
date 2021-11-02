FROM adoptopenjdk:8-jdk-hotspot
#COPY --from=builder build/libs/*.jar app.jar
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} back.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/back.jar"]