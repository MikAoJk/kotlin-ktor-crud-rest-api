FROM gcr.io/distroless/java25-debian13@sha256:6b3cc781107c28f82934250e56b088c44db3502cb5e9e0335d669fe8210df1cc
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
