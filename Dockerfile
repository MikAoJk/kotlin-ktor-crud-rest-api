FROM gcr.io/distroless/java25-debian13@sha256:f93ba1e7fd6acec85f63c14e326ebabfe9beede446adeeeae968bca7d4232707
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
