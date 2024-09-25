FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/yana-scormda-nima-qilay-oxshamayapti-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 7007

ENTRYPOINT ["java", "-jar", "app.jar"]
