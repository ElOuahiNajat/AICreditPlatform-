package com.banque.scoring.servicescoring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "scoring")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "credit_id", nullable = false)
    private Long creditId;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String statut; // EN_ATTENTE, APPROUVE, REFUSE

    @Column(name = "date_creation", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCreation;

    @Column(name = "date_mise_a_jour", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateMiseAJour;
}
