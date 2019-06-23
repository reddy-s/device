package io.virtuelabs.device;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.virtuelabs.device.impl.DeviceServiceImpl;

import java.io.IOException;

public class Device {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Preparing the gRPC server for start up");
        Server server = ServerBuilder.forPort(10001)
                .addService(new DeviceServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook( new Thread ( () -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Sucessfully stopped the server");
        }));

        server.awaitTermination();
    }
}
