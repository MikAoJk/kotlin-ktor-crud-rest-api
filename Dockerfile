FROM gcr.io/distroless/java25-debian13@sha256:44dd6f26a473ed581dd3c628b8284cfcad9c7e039ccecb0c1f65397ceacb7a0d
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
