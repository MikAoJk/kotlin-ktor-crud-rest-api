FROM gcr.io/distroless/java21-debian12
WORKDIR /app
COPY build/libs/*.jar ./
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
CMD [ "app-1.0.0.jar" ]