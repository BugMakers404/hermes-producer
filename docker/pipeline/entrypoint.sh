#!/usr/bin/env bash

[[ -z "${JAVA_OPTS}" ]] && JAVA_OPTS="-Xms256m -Xmx1g"

export JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -Dspring.config.additional-location=file:hermes-producer-application.properties"

exec java ${JAVA_OPTS} -jar hermes-producer.jar