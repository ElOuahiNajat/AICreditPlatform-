package com.banque.credits.servicecredits.entity;

import lombok.Data;

@Data
public class ScoringDTO {
    private Long id;
    private Long clientId;
    private Long creditId;
    private Integer score;   // correspond à `score` dans la table Scoring
    private String statut;   // correspond à `statut` dans la table Scoring
}
