package com.banque.credits.servicecredits.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditTotalsDTO {
    private BigDecimal totalDemandes;
    private BigDecimal totalRembourse;
}
