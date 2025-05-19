FROM eclipse-temurin:21-jdk-jammy

# instala netcat
RUN apt-get update \
 && apt-get install -y netcat \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8081

ENTRYPOINT sh -c "\
  until nc -z kafka 9092; do \
    echo 'Aguardando Kafka…'; \
    sleep 3; \
  done; \
  exec java -jar app.jar \
"