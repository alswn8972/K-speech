FROM adoptopenjdk:8-jdk-hotspot
COPY --from=builder build/libs/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java","-jar","/app.jar"]