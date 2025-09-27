package com.banque.credits.servicecredits.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;
    private String adresse;
    private boolean active;

    // Valeurs fictives pour test
    private Integer revenus = 5000;
    private Integer dettes = 1000;
}
