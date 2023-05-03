#!/usr/bin/env bash

[[ -z "${JAVA_OPTS}" ]] && JAVA_OPTS="-Xms512m -Xmx1g"

export JAVA_OPTS="${JAVA_OPTS} -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport"

exec java ${JAVA_OPTS} -jar hermes-producer.jar