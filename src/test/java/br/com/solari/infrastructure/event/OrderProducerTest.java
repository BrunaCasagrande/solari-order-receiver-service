package br.com.solari.infrastructure.event;

import br.com.solari.application.domain.Order;
import br.com.solari.application.domain.PaymentData;
import br.com.solari.application.domain.PaymentMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderProducerTest {

    private OrderProducer orderProducer;
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        orderProducer = new OrderProducer(kafkaTemplate); // Injetando o mock no construtor
    }

    @Test
    void shouldSendMessageToKafkaSuccessfully() throws JsonProcessingException {
        // Arrange
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = Order.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .products(Map.of(1, 2, 3, 4))
                .cpf("12345678901")
                .paymentData(new PaymentData(PaymentMethod.CREDIT_CARD, "1234-5678-9012-3456"))
                .build();

        String expectedMessage = objectMapper.writeValueAsString(order);

        // Act
        orderProducer.sendMessage(order);

        // Assert
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("novo-pedido-queue"), messageCaptor.capture());
        assertEquals(expectedMessage, messageCaptor.getValue());
    }
}