# Use the official Jenkins image as the base
FROM jenkins/jenkins:lts

# Set environment variables for Java and Gradle versions
ENV JAVA_VERSION 17
ENV GRADLE_VERSION 7.6

# Install necessary dependencies and setup the environment
USER root

# Update package list, install Java 17, wget, unzip, and clean up
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjdk-${JAVA_VERSION}-jdk wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Install Gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip

# Set Gradle's path
ENV PATH=$PATH:/opt/gradle/gradle-${GRADLE_VERSION}/bin

# Give the 'jenkins' user access to Gradle and Java installations
RUN chown -R jenkins:jenkins /opt/gradle

# Switch back to the 'jenkins' user
USER jenkins

# Set JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-${JAVA_VERSION}-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin