package org.example.primes.apigateway.validation;

/**
 * @author Alexander Terekhov
 */
public class NumberValidator {

    public void validate(Integer number) {
        if (number < 2) {
            throw new ValidationException("The number should be greater than 2");
        }
    }
}