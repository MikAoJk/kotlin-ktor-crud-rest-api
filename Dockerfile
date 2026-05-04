FROM gcr.io/distroless/java25-debian13@sha256:70036fed3c5ba28821c5fad98b613a2fb173cc52fa893b509abedaa2e82ba9c1
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
