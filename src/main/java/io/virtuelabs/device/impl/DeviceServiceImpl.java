package io.virtuelabs.device.impl;


import io.grpc.stub.StreamObserver;
import io.virtuelabs.contract.DeviceRequest;
import io.virtuelabs.contract.DeviceResponse;
import io.virtuelabs.contract.DeviceServiceGrpc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceServiceImpl extends DeviceServiceGrpc.DeviceServiceImplBase {

  private static final Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

  @Override
  public void sendEvent(DeviceRequest request, StreamObserver<DeviceResponse> responseObserver) {

    LOGGER.log(Level.INFO, "Received request form device: {0}", request.getDeviceId());
    try {
      LOGGER.log(Level.INFO, "Processing request form device: {0}", request.getDeviceId());
      responseObserver.onNext(
        DeviceResponse.newBuilder()
          .setAnomaly(true)
          .setMessage("Test Response")
          .build()
      );
    } catch (RuntimeException e) {
      responseObserver.onError(e);
      throw e;
    }

    LOGGER.log(Level.INFO, "Responding to request form device: {0}", request.getDeviceId());
    responseObserver.onCompleted();
  }
}
