FROM gcr.io/distroless/java25-debian13@sha256:34f47c4e018bf205c771ad12a1c8c06d0801a69c0a5566677f0e31f166c89793
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
