package io.virtuelabs.device.impl;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import io.virtuelabs.contract.HealthCheckRequest;
import io.virtuelabs.contract.HealthCheckResponse;
import io.virtuelabs.contract.HealthGrpc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HealthServiceImpl extends HealthGrpc.HealthImplBase {

  private static final Logger LOGGER = Logger.getLogger(HealthServiceImpl.class.getName());

  @Override
  public void check(HealthCheckRequest request,
                    StreamObserver<HealthCheckResponse> responseObserver) {
    LOGGER.log(Level.INFO, "Health check: Request received");
    HealthCheckResponse.ServingStatus status = getStatus(request.getService());
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

  private HealthCheckResponse.ServingStatus getStatus(String service) {
    LOGGER.log(Level.INFO, service);
    return HealthCheckResponse.ServingStatus.SERVING;
  }
}
