FROM navikt/java:11
COPY target/app.jar /app/app.jar
COPY init.sh /init-scripts/init.sh