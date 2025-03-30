package br.com.solari.infrastructure.event;

import br.com.solari.application.domain.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(Order order) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String orderJson = objectMapper.writeValueAsString(order);
    kafkaTemplate.send("novo-pedido-queue", orderJson);
  }
}
