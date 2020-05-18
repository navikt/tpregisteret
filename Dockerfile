FROM navikt/java:13
COPY build/libs/*.jar /app/app.jar
COPY init.sh /init-scripts/init.sh
