package com.banque.clients.serviceclients.entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @Email
    @NotBlank
    private String email;

    private String telephone;

    private LocalDate dateNaissance;

    private String adresse;

    private boolean active = true; // Par défaut le compte est actif
}

