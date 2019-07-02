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

    LOGGER.log(Level.INFO, "Received request form device: {0}", request.getDeviceId());

    DeviceResponse deviceResponse;

    try {
      LOGGER.log(Level.INFO, "Processing request form device: {0}", request.getDeviceId());
      if (request.getRequestType() == RequestType.REGISTRATION){
        LOGGER.log(Level.INFO, "Fetching Configuration from config server");
        ConfigResponse configResponse = ConfigurationClient.fetchDeviceConfig(request);
        deviceResponse = DeviceResponse.newBuilder()
          .setAnomaly(false)
          .setMessage(String.format("Registration Event: Ping me every %d seconds", configResponse.getBeatFrequency()))
          .setHeartBeatFrequency(configResponse.getBeatFrequency())
          .setResponseType(ResponseType.PROFILE)
          .setNotify(false)
          .build();
      } else {
        deviceResponse = DeviceResponse.newBuilder()
          .setAnomaly(true)
          .setMessage("Non-Registration Event")
          .setResponseType(ResponseType.ACTION)
          .setNotify(true)
          .build();
      }
      responseObserver.onNext(deviceResponse);
    } catch (RuntimeException e) {
      responseObserver.onError(e);
      throw e;
    }

    LOGGER.log(Level.INFO, "Responding to request form device: {0}", request.getDeviceId());
    responseObserver.onCompleted();
  }
}
