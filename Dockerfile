FROM openjdk:17-alpine

WORKDIR /app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/2024_BEOTKKOTTHON_TEAM_23_BE-0.0.1-SNAPSHOT.jar ./app.jar

CMD ["mkdir", "resources"]
CMD ["mkdir", "./resources/images"]
CMD ["mkdir", "./resources/logs"]
CMD ["java","-jar","./app.jar","--spring.profiles.active=dev"]