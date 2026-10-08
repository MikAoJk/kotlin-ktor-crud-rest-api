FROM gcr.io/distroless/java25-debian13@sha256:817363ac3b3efab113afa288fe4d4d0fba6daaf02e59ae8813e7573dbc556212
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
