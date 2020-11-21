package org.example.primes.server;

import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author Alexander Terekhov
 */
public class PrimeNumbersServerApp {

    public static final int PORT = 8888;

    public static void main(String[] args) throws IOException, InterruptedException {
        final PrimeNumberService primeNumberService = new PrimeNumberService();
        ServerBuilder.forPort(PORT)
                .addService(new PrimeNumbersGrpcService(primeNumberService))
                .build()
                .start()
                .awaitTermination();
    }
}