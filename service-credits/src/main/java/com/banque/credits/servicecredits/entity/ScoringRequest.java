package com.banque.credits.servicecredits.entity;

import lombok.Data;

@Data
public class ScoringRequest {
    private Long clientId;
    private Long creditId;
    private Integer revenus;
    private Integer dettes;
}
