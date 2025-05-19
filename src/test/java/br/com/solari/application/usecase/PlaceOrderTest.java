package br.com.solari.application.usecase;

import br.com.solari.application.domain.Order;
import br.com.solari.application.domain.PaymentData;
import br.com.solari.application.domain.PaymentMethod;
import br.com.solari.infrastructure.event.OrderProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaceOrderTest {

    private PlaceOrder placeOrder;
    private OrderProducer orderProducer;

    @BeforeEach
    void setUp() {
        orderProducer = mock(OrderProducer.class);
        placeOrder = new PlaceOrder(orderProducer);
    }

    @Test
    void shouldCreateOrderAndSendToKafkaSuccessfully() throws JsonProcessingException {
        Map<Integer, Integer> products = Map.of(1, 2, 3, 4);
        String cpf = "12345678901";
        PaymentData paymentData = new PaymentData(PaymentMethod.CREDIT_CARD, "1234-5678-9012-3456");
        Order request = Order.builder()
                .products(products)
                .cpf(cpf)
                .paymentData(paymentData)
                .build();

        Order result = placeOrder.createOrderAndSendToKafka(request);

        assertNotNull(result.getId());
        assertEquals(products, result.getProducts());
        assertEquals(cpf, result.getCpf());
        assertEquals(paymentData, result.getPaymentData());

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderProducer, times(1)).sendMessage(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();

        assertEquals(result, capturedOrder);
    }

    @Test
    void shouldThrowExceptionWhenOrderIsInvalid() {
        Order invalidRequest = Order.builder().build();

        assertThrows(Exception.class, () -> placeOrder.createOrderAndSendToKafka(invalidRequest));
        verifyNoInteractions(orderProducer);
    }
}