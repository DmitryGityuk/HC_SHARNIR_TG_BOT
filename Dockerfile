FROM adoptopenjdk/openjdk17:alpine-jre
ARG JAR_FILE=target/HC_SHARNIR_BOT-1.0.0.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]