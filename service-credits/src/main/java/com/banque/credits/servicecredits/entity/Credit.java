package com.banque.credits.servicecredits.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "credits")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    @Column(name = "duree_mois", nullable = false)
    private Integer dureeMois;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @Column(name = "date_approbation")
    private LocalDate dateApprobation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditStatus statut = CreditStatus.EN_ATTENTE;

    @Column(name = "montant_rembourse", precision = 15, scale = 2)
    private BigDecimal montantRembourse = BigDecimal.ZERO;

    // Constructeurs
    public Credit() {}

    public Credit(Long clientId, BigDecimal montant, Integer dureeMois) {
        this.clientId = clientId;
        this.montant = montant;
        this.dureeMois = dureeMois;
        this.statut = CreditStatus.EN_ATTENTE;
        this.montantRembourse = BigDecimal.ZERO;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Integer getDureeMois() {
        return dureeMois;
    }

    public void setDureeMois(Integer dureeMois) {
        this.dureeMois = dureeMois;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public LocalDate getDateApprobation() {
        return dateApprobation;
    }

    public void setDateApprobation(LocalDate dateApprobation) {
        this.dateApprobation = dateApprobation;
    }

    public CreditStatus getStatut() {
        return statut;
    }

    public void setStatut(CreditStatus statut) {
        this.statut = statut;
    }

    public BigDecimal getMontantRembourse() {
        return montantRembourse;
    }

    public void setMontantRembourse(BigDecimal montantRembourse) {
        this.montantRembourse = montantRembourse;
    }
}
