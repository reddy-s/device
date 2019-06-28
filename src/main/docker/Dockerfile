FROM openjdk:8-jre-alpine

LABEL application=io.virtuelabs.device \
      maintainer=SangramReddy<reddy.horcrux@gmail.com>

ARG GRPC_HEALTH_PROBE_VERSION=v0.3.0

COPY ./app/device-*.jar /app/app.jar

RUN addgroup -S virtuelabs && adduser -S virtuelabs -G virtuelabs && \
    chown -R virtuelabs:virtuelabs /app && \
    chmod -R 755 /app && \
    wget -qO/app/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 && \
    chmod +x /app/grpc_health_probe

WORKDIR /app

ENV RUN_PORT=10001

EXPOSE 10001

USER virtuelabs

CMD java -jar app.jar



