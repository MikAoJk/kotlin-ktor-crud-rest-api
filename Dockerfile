FROM gcr.io/distroless/java25-debian13@sha256:2c2450426c449aa19e6fb12a151172187588f6f6fc6d7183b3af559d208a5e8b
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
