FROM openjdk:8-jre-alpine

LABEL application=io.virtuelabs.device \
      maintainer=SangramReddy<reddy.horcrux@gmail.com>

COPY ./app/device-*.jar /app/app.jar

RUN addgroup -S virtuelabs && adduser -S virtuelabs -G virtuelabs && \
    chown -R virtuelabs:virtuelabs /app && \
    chmod -R 755 /app

WORKDIR /app

ENV RUN_PORT=10001
EXPOSE 10001

USER virtuelabs

CMD java -jar app.jar



