package org.example.primes.server;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

/**
 * @author Alexander Terekhov
 */
public class PrimeNumberServiceTest {
    private PrimeNumberService primeNumberService = new PrimeNumberService();

    @Test
    void shouldNotReturnPrimesIfBoundLessThan2() {
        StepVerifier.create(primeNumberService.getPrimesTill(1))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier.create(primeNumberService.getPrimesTill(0))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier.create(primeNumberService.getPrimesTill(-465))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldReturn2For2() {
        StepVerifier.create(primeNumberService.getPrimesTill(2))
                .expectNext(2)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldGeneratePrimes() {
        StepVerifier.create(primeNumberService.getPrimesTill(15))
                .expectNext(2, 3, 5, 7, 11, 13)
                .expectNextCount(0)
                .verifyComplete();
    }
}