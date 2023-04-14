FROM amazoncorretto:17.0.6-al2023

WORKDIR /app

COPY ["hermes-producer-main/build/libs/hermes-producer-main-*.jar", "./hermes-producer.jar"]
COPY ["../hermes-producer-application.properties", "./hermes-producer-application.properties"]
COPY ["docker/pipeline/entrypoint.sh", "/entrypoint.sh"]

RUN chmod +x /entrypoint.sh
#RUN chmod +x ./hermes-producer.jar
#RUN chmod +r ./hermes-producer-application.properties


ENTRYPOINT ["/entrypoint.sh"]