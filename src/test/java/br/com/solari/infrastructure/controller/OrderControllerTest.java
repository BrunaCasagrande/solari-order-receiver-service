package br.com.solari.infrastructure.controller;

import br.com.solari.application.domain.Order;
import br.com.solari.application.domain.PaymentData;
import br.com.solari.application.domain.PaymentMethod;
import br.com.solari.application.usecase.PlaceOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceOrder placeOrder;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        Map<Integer, Integer> products = Map.of(1, 2, 3, 4);
        String cpf = "12345678901";
        PaymentData paymentData = new PaymentData(PaymentMethod.CREDIT_CARD, "1234-5678-9012-3456");
        Order request = Order.builder()
                .products(products)
                .cpf(cpf)
                .paymentData(paymentData)
                .build();

        Order response = Order.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .products(products)
                .cpf(cpf)
                .paymentData(paymentData)
                .build();

        Mockito.when(placeOrder.createOrderAndSendToKafka(any(Order.class))).thenReturn(response);

        mockMvc.perform(post("/solari/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.products").isNotEmpty())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.paymentData.paymentMethod").value(PaymentMethod.CREDIT_CARD.name()));
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        Order invalidRequest = Order.builder().build();

        mockMvc.perform(post("/solari/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}