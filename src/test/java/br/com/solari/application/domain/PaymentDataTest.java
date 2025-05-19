package br.com.solari.application.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDataTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreatePaymentDataSuccessfully() {
        // Arrange
        PaymentData paymentData = PaymentData.builder()
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .creditCardNumber("1234-5678-9012-3456")
                .build();

        // Act
        Set<ConstraintViolation<PaymentData>> violations = validator.validate(paymentData);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldThrowValidationErrorWhenPaymentMethodIsNull() {
        // Arrange
        PaymentData paymentData = PaymentData.builder()
                .creditCardNumber("1234-5678-9012-3456")
                .build();

        // Act
        Set<ConstraintViolation<PaymentData>> violations = validator.validate(paymentData);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("paymentMethod is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldAllowNullCreditCardNumber() {
        // Arrange
        PaymentData paymentData = PaymentData.builder()
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .build();

        // Act
        Set<ConstraintViolation<PaymentData>> violations = validator.validate(paymentData);

        // Assert
        assertTrue(violations.isEmpty());
    }
}