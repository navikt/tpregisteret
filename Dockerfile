FROM navikt/java:15
COPY build/libs/*.jar /app/app.jar
COPY init.sh /init-scripts/init.sh
