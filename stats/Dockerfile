FROM amazoncorretto:11
COPY target/*.jar /stats.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/stats.jar"]