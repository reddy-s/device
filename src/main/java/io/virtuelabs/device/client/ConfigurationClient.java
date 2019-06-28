package io.virtuelabs.device.client;

import io.virtuelabs.contract.ConfigRequest;
import io.virtuelabs.contract.ConfigResponse;
import io.virtuelabs.contract.ConfigurationServiceGrpc;
import io.virtuelabs.contract.DeviceRequest;
import io.virtuelabs.device.config.DeviceChannelConfig;

import java.util.concurrent.TimeUnit;

public class ConfigurationClient {

  private static long timeoutMs = Long.parseLong(System.getenv("CONFIGURATION_SERVICE_REQUEST_TIMEOUT"));

  public static ConfigResponse fetchDeviceConfig(DeviceRequest deviceRequest) {
    ConfigurationServiceGrpc.ConfigurationServiceBlockingStub configStub = ConfigurationServiceGrpc
      .newBlockingStub(DeviceChannelConfig.getConfigurationManagedChannel())
      .withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS);
    return configStub
      .sendEvent(ConfigRequest
        .newBuilder()
        .setDeviceId(deviceRequest.getDeviceId())
        .setDeviceType(deviceRequest.getDeviceType())
        .build()
    );
  }
}
