package br.com.solari.application.domain;

import br.com.solari.infrastructure.gateway.database.jpa.entity.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentData {

  @NotBlank(message = "paymentMethod is required")
  private PaymentMethod paymentMethod;

  private String creditCardNumber;

  private String pixKey;

}
