package com.banque.credits.servicecredits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyStatsDTO {
    private String month; // ex: "2025-08"
    private Long count;
}
