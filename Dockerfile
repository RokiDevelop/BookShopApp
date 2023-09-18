FROM openjdk:11
MAINTAINER Roman Kiryukhin
ARG JAR_FILE=*.jar
COPY ./target/${JAR_FILE} book-shop-app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "book-shop-app.jar"]