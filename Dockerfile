FROM gcr.io/distroless/java25-debian13@sha256:b3eab8bcaa9abc92c4d5580b661adfae79289e7f22df2e029967fb8cb325f711
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
