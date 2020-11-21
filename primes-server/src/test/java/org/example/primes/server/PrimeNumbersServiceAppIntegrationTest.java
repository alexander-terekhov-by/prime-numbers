package org.example.primes.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.primes.Number;
import org.example.primes.ReactorPrimeNumbersStreamerGrpc;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;

/**
 * @author Alexander Terekhov
 */
public class PrimeNumbersServiceAppIntegrationTest {

    @Test
    void testApplication() {
        final Thread thread = new Thread(() -> {
            try {
                PrimeNumbersServerApp.main(new String[]{});
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        final ManagedChannel grpcChannel = ManagedChannelBuilder.forAddress("localhost", PrimeNumbersServerApp.PORT)
                .usePlaintext()
                .build();

        final Number number = Number.newBuilder().setValue(10).build();
        final Flux<Integer> numberFlux = ReactorPrimeNumbersStreamerGrpc.newReactorStub(grpcChannel).listPrimes(number)
                .map(Number::getValue);

        StepVerifier.create(numberFlux)
                .expectNext(2, 3, 5, 7)
                .expectNextCount(0)
                .verifyComplete();
    }
}