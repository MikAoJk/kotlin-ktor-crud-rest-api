FROM gcr.io/distroless/java25-debian13@sha256:b10a14cd68320b9dd71e72736fb72d83799ab78a0d79f2b2598e5d0afe9f8c8c
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
