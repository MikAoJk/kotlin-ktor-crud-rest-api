FROM gcr.io/distroless/java25-debian13@sha256:7bf4beb8fde0e21616519816149c5b85b8d92ab307188a4a0bb0a7dd1fd36605
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
