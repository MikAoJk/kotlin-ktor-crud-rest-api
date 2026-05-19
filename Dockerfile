FROM gcr.io/distroless/java25-debian13@sha256:583ba2e08558063002bd1b5874a81b33b7204a0ad46727d4b6cbeff5a25935ba
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
