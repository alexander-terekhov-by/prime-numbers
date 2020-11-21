package org.example.primes.apigateway.service;

import io.grpc.ManagedChannel;
import org.example.primes.Number;
import org.example.primes.ReactorPrimeNumbersStreamerGrpc;
import reactor.core.publisher.Flux;

/**
 * @author Alexander Terekhov
 */
public class PrimesGrpcClientWrapper {

    private final ReactorPrimeNumbersStreamerGrpc.ReactorPrimeNumbersStreamerStub client;

    public PrimesGrpcClientWrapper(ManagedChannel grpcChannel) {
        this.client = ReactorPrimeNumbersStreamerGrpc.newReactorStub(grpcChannel);
    }

    public Flux<Integer> retrievePrimes(Integer number) {
        return client.listPrimes(toDto(number)).map(Number::getValue);
    }

    private Number toDto(Integer number) {
        return Number.newBuilder().setValue((number)).build();
    }
}