FROM gcr.io/distroless/java25-debian13@sha256:cc3bb44755599d4c25c26c43b05761eeb1da2e779172cee258c2202ca071abfa
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
