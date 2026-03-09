FROM gcr.io/distroless/java25-debian13@sha256:34596c2b01eae269c67370572ccf9cba6cd26ecd2eb171e1d90419060c6297c5
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
