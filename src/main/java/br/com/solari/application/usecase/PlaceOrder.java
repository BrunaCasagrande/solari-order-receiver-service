package br.com.solari.application.usecase;

import br.com.solari.application.domain.Order;
import br.com.solari.infrastructure.event.OrderProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrder {

    private final OrderProducer orderProducer;

    @Autowired
    public PlaceOrder(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public Order createOrderAndSendToKafka(Order request) throws JsonProcessingException {

        final var buildDomain =
                Order.createOrder(
                        request.getProducts(),
                        request.getCpf(),
                        request.getPaymentData());

        //TODO esse SYSO pode ser substituido no futuro por uma inclusao na base de dados por motivos de auditoria
        System.out.println("### Pedido criado: " + buildDomain.toString());

        orderProducer.sendMessage(buildDomain);

        return buildDomain;
    }
}
