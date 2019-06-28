package io.virtuelabs.device;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.virtuelabs.device.service.impl.DeviceServiceImpl;
import io.virtuelabs.device.ops.Health;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Device {

    private static final Logger LOGGER = Logger.getLogger(Device.class.getName());

    private static final int runPort = Integer.parseInt(System.getenv("RUN_PORT"));

    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.log(Level.INFO, "Preparing the DEVICE gRPC server for start up");
        Server server = ServerBuilder.forPort(runPort)
                .addService(new DeviceServiceImpl())
                .addService(new Health.HealthImpl())
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
