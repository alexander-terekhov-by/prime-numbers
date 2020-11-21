package org.example.primes.apigateway.validation;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author Alexander Terekhov
 */
public class NumberValidatorTest {

    private final NumberValidator numberValidator = new NumberValidator();

    @Test
    void shouldMarkValuesGreatOrEqualsThan2AsValid() {
        numberValidator.validate(2);
        numberValidator.validate(5);
    }

    @Test
    void shouldMarkValuesLessThan2AsInvalid() {
        assertThrows(ValidationException.class,
                     () -> numberValidator.validate(1),
                     "The number should be greater than 2");
        assertThrows(ValidationException.class,
                     () -> numberValidator.validate(-1),
                     "The number should be greater than 2");
    }
}