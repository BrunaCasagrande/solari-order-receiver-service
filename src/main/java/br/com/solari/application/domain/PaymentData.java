package br.com.solari.application.domain;

import br.com.solari.infrastructure.gateway.database.jpa.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentData {

  @NotBlank(message = "paymentMethod is required")
  private PaymentMethod paymentMethod;

  private String creditCardNumber;

  private String pixKey;

}
