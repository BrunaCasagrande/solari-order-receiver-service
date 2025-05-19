package br.com.solari.application.domain;

import br.com.solari.infrastructure.config.exception.GatewayException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCreateOrderSuccessfully() {
        Map<Integer, Integer> products = Map.of(1, 2, 3, 4);
        String cpf = "12345678901";
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Ajuste aqui
        PaymentData paymentData = new PaymentData(paymentMethod, "1234-5678-9012-3456");

        Order order = Order.createOrder(products, cpf, paymentData);

        assertNotNull(order.getId());
        assertEquals(products, order.getProducts());
        assertEquals(cpf, order.getCpf());
        assertEquals(paymentData, order.getPaymentData());
    }

    @Test
    void shouldThrowExceptionWhenProductsIsNull() {
        String cpf = "12345678901";
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Ajuste aqui
        PaymentData paymentData = new PaymentData(paymentMethod, "1234-5678-9012-3456");

        GatewayException.DomainException exception = assertThrows(
                GatewayException.DomainException.class,
                () -> Order.createOrder(null, cpf, paymentData)
        );

        assertTrue(exception.getMessage().contains("products is required"));
    }

    @Test
    void shouldThrowExceptionWhenCpfIsNull() {
        Map<Integer, Integer> products = Map.of(1, 2, 3, 4);
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD; // Ajuste aqui
        PaymentData paymentData = new PaymentData(paymentMethod, "1234-5678-9012-3456");

        GatewayException.DomainException exception = assertThrows(
                GatewayException.DomainException.class,
                () -> Order.createOrder(products, null, paymentData)
        );

        assertTrue(exception.getMessage().contains("cpf is required"));
    }

    @Test
    void shouldThrowExceptionWhenPaymentDataIsNull() {
        Map<Integer, Integer> products = Map.of(1, 2, 3, 4);
        String cpf = "12345678901";

        GatewayException.DomainException exception = assertThrows(
                GatewayException.DomainException.class,
                () -> Order.createOrder(products, cpf, null)
        );

        assertTrue(exception.getMessage().contains("paymentData is required"));
    }
}