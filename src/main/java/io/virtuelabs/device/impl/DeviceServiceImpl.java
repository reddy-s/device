package io.virtuelabs.device.impl;

import io.grpc.stub.StreamObserver;
import io.virtuelabs.contract.DeviceRequest;
import io.virtuelabs.contract.DeviceResponse;
import io.virtuelabs.contract.DeviceServiceGrpc;

public class DeviceServiceImpl extends DeviceServiceGrpc.DeviceServiceImplBase {

  @Override
  public void sendEvent(DeviceRequest request, StreamObserver<DeviceResponse> responseObserver) {

    System.out.println("Incoming request");
    responseObserver.onNext(
      DeviceResponse.newBuilder()
        .setAnomaly(true)
        .setMessage("Test Response")
        .build()
    );
    System.out.println("Sending response");
    responseObserver.onCompleted();
  }
}
