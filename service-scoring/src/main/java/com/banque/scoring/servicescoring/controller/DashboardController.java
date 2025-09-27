package com.banque.scoring.servicescoring.controller;

import com.banque.scoring.servicescoring.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // pour Angular
public class DashboardController {

    private final ScoringService scoringService;

    // Nombre total de scorings
    @GetMapping("/total-scores")
    public Map<String, Long> getTotalScores() {
        long total = scoringService.countTotalScores();
        return Map.of("totalScores", total);
    }

    // Nombre de scorings APPROUVE / REFUSE
    @GetMapping("/status-count")
    public Map<String, Long> getStatusCount() {
        long approved = scoringService.countByStatus("APPROUVE");
        long refused = scoringService.countByStatus("REFUSE");
        return Map.of(
                "APPROUVE", approved,
                "REFUSE", refused
        );
    }

    // Score moyen
    @GetMapping("/average-score")
    public Map<String, Double> getAverageScore() {
        double avg = scoringService.getAverageScore();
        return Map.of("averageScore", avg);
    }

    // Score maximum et minimum
    @GetMapping("/min-max-score")
    public Map<String, Integer> getMinMaxScore() {
        int min = scoringService.getMinScore();
        int max = scoringService.getMaxScore();
        return Map.of(
                "minScore", min,
                "maxScore", max
        );
    }
}
