FROM gcr.io/distroless/java25-debian13@sha256:7b62d2eafa7e484add5b8be46c0b437b1540a3ce6e0f7ac91b3ffa64ba210246
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
