package io.virtuelabs.device.client;

import io.virtuelabs.contract.ConfigRequest;
import io.virtuelabs.contract.ConfigResponse;
import io.virtuelabs.contract.ConfigurationServiceGrpc;
import io.virtuelabs.contract.DeviceRequest;
import io.virtuelabs.device.config.DeviceChannelConfig;

public class ConfigurationClient {

  public static ConfigResponse fetchDeviceConfig(DeviceRequest deviceRequest) {
    ConfigurationServiceGrpc.ConfigurationServiceBlockingStub configStub = ConfigurationServiceGrpc.newBlockingStub(DeviceChannelConfig.getConfigurationManagedChannel());
    return configStub
      .sendEvent(ConfigRequest
        .newBuilder()
        .setDeviceId(deviceRequest.getDeviceId())
        .setDeviceType(deviceRequest.getDeviceType())
        .build()
    );
  }
}