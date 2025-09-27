package com.banque.scoring.servicescoring.controller;

import com.banque.scoring.servicescoring.entity.Scoring;
import com.banque.scoring.servicescoring.service.ScoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200") // <--- autorise ton front
@RestController
@RequestMapping("/api/scoring")
public class ScoringController {

    private final ScoringService scoringService;

    public ScoringController(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    // Calculer un score
    @PostMapping("/calculate")
    public ResponseEntity<Scoring> calculateScore(@RequestBody ScoringRequest request) {
        Scoring scoring = scoringService.calculateScore(
                request.getClientId(),
                request.getCreditId(),
                request.getRevenus(),
                request.getDettes()
        );
        return ResponseEntity.ok(scoring);
    }

    // Récupérer le dernier score d’un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Scoring> getLastScore(@PathVariable Long clientId) {
        return ResponseEntity.ok(scoringService.getLastScoreByClient(clientId));
    }

    // Historique des scores d’un client
    @GetMapping("/history/{clientId}")
    public ResponseEntity<List<Scoring>> getHistory(@PathVariable Long clientId) {
        return ResponseEntity.ok(scoringService.getHistoryByClient(clientId));
    }

    // Récupérer le score d’une demande de crédit
    @GetMapping("/credit/{creditId}")
    public ResponseEntity<Scoring> getScoreByCredit(@PathVariable Long creditId) {
        return ResponseEntity.ok(scoringService.getScoreByCredit(creditId));
    }

    // Mettre à jour le statut
    @PatchMapping("/{id}/update-status")
    public ResponseEntity<Scoring> updateStatus(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(scoringService.updateStatus(id, statut));
    }

    // Supprimer un scoring
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScoring(@PathVariable Long id) {
        scoringService.deleteScoring(id);
        return ResponseEntity.ok().build();
    }

    // DTO pour la requête de calcul
    public static class ScoringRequest {
        private Long clientId;
        private Long creditId;
        private int revenus;
        private int dettes;

        public Long getClientId() { return clientId; }
        public void setClientId(Long clientId) { this.clientId = clientId; }
        public Long getCreditId() { return creditId; }
        public void setCreditId(Long creditId) { this.creditId = creditId; }
        public int getRevenus() { return revenus; }
        public void setRevenus(int revenus) { this.revenus = revenus; }
        public int getDettes() { return dettes; }
        public void setDettes(int dettes) { this.dettes = dettes; }
    }
}
