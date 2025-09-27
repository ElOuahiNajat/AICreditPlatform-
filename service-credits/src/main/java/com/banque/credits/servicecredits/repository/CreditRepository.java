package com.banque.credits.servicecredits.repository;

import com.banque.credits.servicecredits.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    // Pas besoin d'ajouter d'autres méthodes pour le moment
}
