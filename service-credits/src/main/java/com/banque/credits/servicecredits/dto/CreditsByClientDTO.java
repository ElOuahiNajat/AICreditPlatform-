package com.banque.credits.servicecredits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditsByClientDTO {
    private Long clientId;
    private Long count;
}
