package com.banque.scoring.servicescoring.service;

import com.banque.scoring.servicescoring.entity.Scoring;
import com.banque.scoring.servicescoring.repository.ScoringRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScoringService {

    private final ScoringRepository scoringRepository;

    public ScoringService(ScoringRepository scoringRepository) {
        this.scoringRepository = scoringRepository;
    }



    // Dans ScoringService.java

    public long countTotalScores() {
        return scoringRepository.count();
    }

    public long countByStatus(String statut) {
        return scoringRepository.countByStatut(statut);
    }

    public double getAverageScore() {
        return scoringRepository.findAll()
                .stream()
                .mapToInt(Scoring::getScore)
                .average()
                .orElse(0);
    }

    public int getMinScore() {
        return scoringRepository.findAll()
                .stream()
                .mapToInt(Scoring::getScore)
                .min()
                .orElse(0);
    }

    public int getMaxScore() {
        return scoringRepository.findAll()
                .stream()
                .mapToInt(Scoring::getScore)
                .max()
                .orElse(0);
    }


    // Calculer un score simple
    public Scoring calculateScore(Long clientId, Long creditId, int revenus, int dettes) {
        int scoreValue = (revenus / (dettes + 1)) * 10; // exemple simple
        String decision = scoreValue > 50 ? "APPROUVE" : "REFUSE";

        Scoring scoring = Scoring.builder()
                .clientId(clientId)
                .creditId(creditId)
                .score(scoreValue)
                .statut(decision)
                .dateCreation(LocalDateTime.now())
                .dateMiseAJour(LocalDateTime.now())
                .build();

        return scoringRepository.save(scoring);
    }

    // Récupérer le dernier score d’un client
    public Scoring getLastScoreByClient(Long clientId) {
        return scoringRepository.findTopByClientIdOrderByDateCreationDesc(clientId);
    }

    // Récupérer l’historique des scores d’un client
    public List<Scoring> getHistoryByClient(Long clientId) {
        return scoringRepository.findByClientIdOrderByDateCreationDesc(clientId);
    }

    // Récupérer le dernier score d’une demande de crédit spécifique
    public Scoring getScoreByCredit(Long creditId) {
        return scoringRepository.findTopByCreditIdOrderByDateCreationDesc(creditId);
    }

    // Mettre à jour le statut d’un scoring
    public Scoring updateStatus(Long id, String statut) {
        return scoringRepository.findById(id)
                .map(scoring -> {
                    scoring.setStatut(statut);
                    scoring.setDateMiseAJour(LocalDateTime.now());
                    return scoringRepository.save(scoring);
                })
                .orElseThrow(() -> new RuntimeException("Scoring introuvable avec l'ID : " + id));
    }


    // Supprimer un scoring
    public void deleteScoring(Long id) {
        scoringRepository.deleteById(id);
    }
}
