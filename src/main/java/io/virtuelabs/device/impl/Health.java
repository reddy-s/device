package io.virtuelabs.device.impl;

import grpc.health.v1.HealthOuterClass.HealthCheckResponse;
import grpc.health.v1.HealthOuterClass.HealthCheckResponse.ServingStatus;
import grpc.health.v1.HealthGrpc;
import grpc.health.v1.HealthOuterClass.HealthCheckRequest;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

/** gRPC Health Checking Protocol */
public class Health {
  private static final Logger LOGGER = Logger.getLogger(Health.class.getName());

  /** The implementation of Health Checking Service. */
  public static class HealthImpl extends HealthGrpc.HealthImplBase {
    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
      LOGGER.log(Level.INFO, "Health check: Request received");
      String service = request.getService();
      if (service == null) {
        responseObserver.onError(
          new StatusException(Status.NOT_FOUND.withDescription("Unknown service")));
      } else {
        LOGGER.log(Level.INFO, "Health check: Sending response");
        HealthCheckResponse response =
          HealthCheckResponse.newBuilder().setStatus(getServingStatus()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
      }
    }

    private ServingStatus getServingStatus() {
      LOGGER.log(Level.INFO, "Getting status");
      return ServingStatus.SERVING;
    }
  }
}
