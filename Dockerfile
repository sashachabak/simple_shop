FROM maven:3.8.4-jdk-11 AS build

WORKDIR /home
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN mvn clean package -Dmaven.test.skip=true
COPY ./entrypoint.sh ./entrypoint.sh
ENTRYPOINT [ "./entrypoint.sh" ]