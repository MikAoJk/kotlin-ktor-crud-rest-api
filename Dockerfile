FROM gcr.io/distroless/java25-debian13@sha256:0aa10bfac55df3fed8ce238f4d35c5f14e9b705be763943e80b92d815e703201
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
