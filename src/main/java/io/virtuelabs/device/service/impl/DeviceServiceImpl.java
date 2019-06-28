package io.virtuelabs.device.service.impl;


import io.grpc.stub.StreamObserver;
import io.virtuelabs.contract.*;
import io.virtuelabs.device.stub.ConfigurationClient;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceServiceImpl extends DeviceServiceGrpc.DeviceServiceImplBase {

  private static final Logger LOGGER = Logger.getLogger(DeviceServiceImpl.class.getName());

  @Override
  public void sendEvent(DeviceRequest request, StreamObserver<DeviceResponse> responseObserver) {

    String message;
    LOGGER.log(Level.INFO, "Received request form device: {0}", request.getDeviceId());
    try {
      LOGGER.log(Level.INFO, "Processing request form device: {0}", request.getDeviceId());
      if (request.getRequestType() == RequestType.REGISTRATION){
        LOGGER.log(Level.INFO, "Fetching Configuration from config server");
        ConfigResponse configResponse = ConfigurationClient.fetchDeviceConfig(request);
        message = String.format("Registration Event: Ping me every %d seconds", configResponse.getBeatFrequency());
      } else {
        message = "Non-Registration Event";
      }

      responseObserver.onNext(
        DeviceResponse.newBuilder()
          .setAnomaly(true)
          .setMessage(message)
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
