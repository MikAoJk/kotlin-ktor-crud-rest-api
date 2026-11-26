FROM gcr.io/distroless/java25-debian13@sha256:2b69a4e2c905ec18cdf267f4b3821ac9feaf737dae588a190f918079b380e434
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
