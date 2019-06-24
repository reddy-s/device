package io.virtuelabs.device;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.virtuelabs.device.impl.DeviceServiceImpl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Device {

    private static final Logger LOGGER = Logger.getLogger(Device.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {

        LOGGER.log(Level.INFO, "Preparing the DEVICE gRPC server for start up");
        Server server = ServerBuilder.forPort(10001)
                .addService(new DeviceServiceImpl())
                .build();
        LOGGER.log(Level.INFO, "Starting DEVICE gRPC server");
        server.start();
        LOGGER.log(Level.INFO, "Successfully started DEVICE gRPC server and is running on port 10001");
        Runtime.getRuntime().addShutdownHook( new Thread ( () -> {
            LOGGER.log(Level.INFO,"Received Shutdown Request");
            server.shutdown();
            LOGGER.log(Level.INFO,"Sucessfully stopped DEVICE gRPC server");
        }));

        server.awaitTermination();
    }
}
