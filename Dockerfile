FROM gcr.io/distroless/java25-debian13@sha256:3e0a1496b365a18d2c01ccfe27c8bc93b1a6b8ca7460c02b8badb791bf296fce
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
