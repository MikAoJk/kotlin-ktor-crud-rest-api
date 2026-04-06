FROM gcr.io/distroless/java25-debian13@sha256:9460bf4816a914f6d4f1368568b1f56a69edd5164d215b57194cd8844adbcdfd
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
