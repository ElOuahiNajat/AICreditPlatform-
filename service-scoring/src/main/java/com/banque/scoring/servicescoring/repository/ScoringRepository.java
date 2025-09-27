package com.banque.scoring.servicescoring.repository;

import com.banque.scoring.servicescoring.entity.Scoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoringRepository extends JpaRepository<Scoring, Long> {

    // Récupérer le dernier score d’un client
    Scoring findTopByClientIdOrderByDateCreationDesc(Long clientId);

    // Historique des scores d’un client
    List<Scoring> findByClientIdOrderByDateCreationDesc(Long clientId);

    // Récupérer le dernier score pour un crédit spécifique
    Scoring findTopByCreditIdOrderByDateCreationDesc(Long creditId);



    long countByStatut(String statut);

}
