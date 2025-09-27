package com.banque.credits.servicecredits.controller;

import com.banque.credits.servicecredits.entity.Credit;
import com.banque.credits.servicecredits.entity.CreditStatus;
import com.banque.credits.servicecredits.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/credits")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    // 1️⃣ Créer une nouvelle demande de crédit (appelle automatiquement Scoring)
    @PostMapping("/demandes")
    public ResponseEntity<Credit> createCredit(@RequestBody Credit credit) {
        Credit saved = creditService.createCredit(credit);
        return ResponseEntity.ok(saved);
    }

    // 2️⃣ Récupérer un crédit par ID
    @GetMapping("/demandes/{id}")
    public ResponseEntity<Credit> getCredit(@PathVariable Long id) {
        Credit credit = creditService.getCreditById(id);
        return ResponseEntity.ok(credit);
    }

    // 3️⃣ Lister tous les crédits d’un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Credit>> getCreditsByClient(@PathVariable Long clientId) {
        List<Credit> credits = creditService.getCreditsByClientId(clientId);
        return ResponseEntity.ok(credits);
    }

    // 4️⃣ Mettre à jour le statut d’un crédit
    @PatchMapping("/demandes/{id}/status")
    public ResponseEntity<Credit> updateCreditStatus(@PathVariable Long id,
                                                     @RequestParam CreditStatus statut) {
        Credit updated = creditService.updateCreditStatus(id, statut);
        return ResponseEntity.ok(updated);
    }

    // 5️⃣ Enregistrer un remboursement
    @PostMapping("/remboursements")
    public ResponseEntity<Credit> addRemboursement(@RequestParam Long creditId,
                                                   @RequestParam BigDecimal montant) {
        Credit updated = creditService.addRemboursement(creditId, montant);
        return ResponseEntity.ok(updated);
    }

    // 6️⃣ Supprimer une demande de crédit
    @DeleteMapping("/demandes/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }

    // 7️⃣ Pagination et filtrage
    @GetMapping
    public ResponseEntity<Page<Credit>> getCredits(Pageable pageable) {
        Page<Credit> page = creditService.getCredits(pageable);
        return ResponseEntity.ok(page);
    }

    // 8️⃣ Export CSV
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCredits() {
        ByteArrayInputStream stream = creditService.exportCreditsToCSV();
        byte[] bytes;
        try {
            bytes = stream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lecture CSV", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=credits.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

    // 9️⃣ Activer / Désactiver un crédit
    @PatchMapping("/{id}/status-active")
    public ResponseEntity<Credit> setActiveStatus(@PathVariable Long id,
                                                  @RequestParam boolean active) {
        Credit updated = creditService.setActiveStatus(id, active);
        return ResponseEntity.ok(updated);
    }
}
