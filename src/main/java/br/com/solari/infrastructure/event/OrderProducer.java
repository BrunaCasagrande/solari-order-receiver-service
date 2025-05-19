package br.com.solari.infrastructure.event;

import br.com.solari.application.domain.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(Order order) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String orderJson = objectMapper.writeValueAsString(order);
    kafkaTemplate.send("novo-pedido-queue", orderJson);
  }
}