package org.example.primes.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexander Terekhov
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class IntegrationException extends RuntimeException {

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}