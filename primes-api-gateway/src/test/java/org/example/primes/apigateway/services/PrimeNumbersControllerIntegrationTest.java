package org.example.primes.apigateway.services;


import org.example.primes.apigateway.controller.PrimeNumbersController;
import org.example.primes.apigateway.service.PrimesGrpcClientWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;


import static org.mockito.Mockito.when;

/**
 * @author Alexander Terekhov
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PrimeNumbersController.class)
public class PrimeNumbersControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private PrimesGrpcClientWrapper clientWrapper;

    @Test
    void shouldRequestPrimes() {
        when(clientWrapper.retrievePrimes(55))
                .thenReturn(Flux.fromIterable(List.of(1, 2, 3, 4, 5)));
        webTestClient.get().uri("/prime/55")
                .exchange()
                .expectBodyList(Integer.class)
                .hasSize(5)
                .contains(1, 2, 3, 4, 5);
    }

    @Test
    void shouldNotHaveHandlerWhenNumberIsNotANumber() {
        webTestClient.get().uri("/prime/xx")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldThrowValidationError() {
        webTestClient.get().uri("/prime/-55")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldThrowIntegrationError() {
        when(clientWrapper.retrievePrimes(55))
                .thenReturn(Flux.error(new RuntimeException()));

        webTestClient.get().uri("/prime/55")
                .exchange()
                .expectStatus()
                .isEqualTo(503);
    }
}