package org.example.primes.server;

import org.example.primes.Number;
import org.example.primes.ReactorPrimeNumbersStreamerGrpc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PrimeNumbersGrpcService extends ReactorPrimeNumbersStreamerGrpc.PrimeNumbersStreamerImplBase {

    private final PrimeNumberService primesService;

    public PrimeNumbersGrpcService(PrimeNumberService primesService) {
        this.primesService = primesService;
    }

    @Override
    public Flux<Number> listPrimes(Mono<Number> request) {
        return request
                .map(Number::getValue)
                .flatMapMany(primesService::getPrimesTill)
                .map(this::toNumber);
    }

    private Number toNumber(Integer integer) {
        return Number.newBuilder().setValue(integer).build();
    }
}