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

public static class HealthServiceImpl extends HealthGrpc.HealthImplBase {

  private static final Logger LOGGER = Logger.getLogger(HealthServiceImpl.class.getName());

  @Override
  public void check(HealthCheckRequest request,
                    StreamObserver<HealthCheckResponse> responseObserver) {
    LOGGER.log(Level.INFO, "Health check: Request received");
    ServingStatus status = getStatus(request.getService());
    if (status == null) {
      responseObserver.onError(new StatusException(
        Status.NOT_FOUND.withDescription("unknown service " + request.getService())));
    } else {
      HealthCheckResponse response = HealthCheckResponse.newBuilder().setStatus(status).build();
      responseObserver.onNext(response);
      LOGGER.log(Level.INFO, "Health check: Sending response");
      responseObserver.onCompleted();
    }
  }

  private ServingStatus getStatus(String service) {
    LOGGER.log(Level.INFO, service);
    return ServingStatus.SERVING;
  }
}

/** gRPC Health Checking Protocol */
public class Health {
  private static final Logger LOGGER = Logger.getLogger(HealthServiceImpl.class.getName());
  private static final String ACCEPTED_SERVICE = "ext.maps.booking.partner.v2.BookingService";

  /** The implementation of Health Checking Service. */
  public static class HealthImpl extends HealthGrpc.HealthImplBase {
    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
      LOGGER.log(Level.INFO, "Health check: Request received");
      String service = request.getService();
      if (!service.equals(ACCEPTED_SERVICE)) {
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
