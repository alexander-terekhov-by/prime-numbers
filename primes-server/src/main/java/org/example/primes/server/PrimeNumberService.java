package org.example.primes.server;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Terekhov
 */
public class PrimeNumberService {

    public Flux<Integer> getPrimesTill(int upperBound) {
        if (upperBound < 2) {
            return Flux.empty();
        }
        List<Integer> foundPrimes = new ArrayList<>();
        return Flux.range(2, --upperBound)
                .filter(number -> isPrime(number, foundPrimes))
                .doOnNext(foundPrimes::add);
    }

    private boolean isPrime(int number, List<Integer> foundPrimes) {
        return foundPrimes.stream()
                .takeWhile(foundPrime -> Math.sqrt(number) >= foundPrime)
                .noneMatch(foundPrime -> canBeDivided(number, foundPrime));
    }

    private boolean canBeDivided(int number, Integer divider) {
        return number % divider == 0;
    }
}