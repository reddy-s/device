package io.virtuelabs.device.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DeviceChannelConfig {

  private static DeviceChannelConfig channelConfig;

  private static ManagedChannel configurationManagedChannel;

  private DeviceChannelConfig(){}

  public static DeviceChannelConfig getInstance(){
    if (channelConfig == null){
      channelConfig = new DeviceChannelConfig();
    }
    return channelConfig;
  }

  public static ManagedChannel getConfigurationManagedChannel(){
    if (configurationManagedChannel == null){
      configurationManagedChannel = ManagedChannelBuilder
        .forAddress(System.getenv("IOT_CONFIGURATION_SERVICE"), 20001)
        .usePlaintext()
        .build();
    }
    return configurationManagedChannel;
  }
}
