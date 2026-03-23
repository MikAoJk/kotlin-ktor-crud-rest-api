FROM gcr.io/distroless/java25-debian13@sha256:7b193e5f805680970c06ad61ebdb2c752dee04320890c88102b57504dff1ca27
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
