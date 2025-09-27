package com.banque.scoring.servicescoring.dto;

import lombok.Data;

@Data
public class ScoringRequest {
    private Long clientId;
    private Long creditId;
    private int revenus;
    private int dettes;
}
