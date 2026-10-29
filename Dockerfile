FROM gcr.io/distroless/java25-debian13@sha256:9edcd1e8d3ee33c296ec4a2318500c5b0824f953e27007c36dfbb50b7205d7da
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
