package org.example.primes.apigateway.controller;

import org.example.primes.apigateway.service.PrimesGrpcClientWrapper;
import org.example.primes.apigateway.validation.NumberValidator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PrimeNumbersController {

    private final NumberValidator numberValidator;

    private final PrimesGrpcClientWrapper client;

    public PrimeNumbersController(NumberValidator numberValidator, PrimesGrpcClientWrapper client) {
        this.numberValidator = numberValidator;
        this.client = client;
    }

    @GetMapping(value = "/prime/{number:-?[0-9]+}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> findByName(@PathVariable Integer number) {
        numberValidator.validate(number);
        return client.retrievePrimes(number)
                .onErrorMap(exception -> new IntegrationException("Problems in integration", exception));
    }
}