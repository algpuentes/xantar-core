FROM openjdk:11
ADD target/xantar.jar xantar.jar
ENTRYPOINT ["java", "-jar","xantar.jar"]
EXPOSE 8080