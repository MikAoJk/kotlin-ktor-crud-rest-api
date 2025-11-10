FROM gcr.io/distroless/java21-debian12@sha256:b41ca849c90e111ed5a6d2431b474225535f266ac1b3902cd508718f160cea60
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
