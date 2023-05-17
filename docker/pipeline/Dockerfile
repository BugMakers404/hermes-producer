FROM eclipse-temurin:17.0.6_10-jdk-jammy

WORKDIR /app

COPY ["hermes-producer-main/build/libs/hermes-producer-main-*.jar", "./hermes-producer.jar"]
COPY ["docker/pipeline/entrypoint.sh", "/entrypoint.sh"]

RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]