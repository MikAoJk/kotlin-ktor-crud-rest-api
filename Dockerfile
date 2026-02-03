FROM gcr.io/distroless/java25-debian13@sha256:ef9264f88e7c1c8b537d76f0196250e70124afd58984bf9d55ecbb50e603f276
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
