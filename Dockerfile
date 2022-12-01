# define base docker image
FROM openjdk:17
# copy files
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mc1.jar
COPY app.config /config/app.config
# how to run
ENTRYPOINT ["java","-jar","/mc1.jar"]
# port for application
EXPOSE 10001