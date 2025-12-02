FROM gcr.io/distroless/java25-debian13@sha256:0ea0c4491a57effa3c49c669564b3642d591ed52a3928f22b4cf829bd2736a57
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
