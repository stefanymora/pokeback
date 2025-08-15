# Usamos Java 17
FROM openjdk:17-jdk-slim

# Argumento con el nombre del JAR (ajústalo según tu build)
ARG JAR_FILE=target/pokestop-0.0.1-SNAPSHOT.jar

# Copiamos el JAR generado al contenedor
COPY ${JAR_FILE} app.jar

# Indicamos cómo arrancar la app
ENTRYPOINT ["java","-jar","/app.jar"]
