package com.banque.credits.servicecredits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditStatusDTO {
    private String statut;
    private Long count;
}
