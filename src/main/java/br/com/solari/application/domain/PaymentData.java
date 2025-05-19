package br.com.solari.application.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentData {

  @NotNull(message = "paymentMethod is required")
  private PaymentMethod paymentMethod;

  private String creditCardNumber;

}
