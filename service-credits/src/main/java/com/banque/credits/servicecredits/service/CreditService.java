package com.banque.credits.servicecredits.service;

import com.banque.credits.servicecredits.dto.CreditStatusDTO;
import com.banque.credits.servicecredits.dto.CreditTotalsDTO;
import com.banque.credits.servicecredits.dto.CreditsByClientDTO;
import com.banque.credits.servicecredits.dto.MonthlyStatsDTO;
import com.banque.credits.servicecredits.entity.ClientDTO;
import com.banque.credits.servicecredits.entity.Credit;
import com.banque.credits.servicecredits.entity.CreditStatus;
import com.banque.credits.servicecredits.entity.ScoringDTO;
import com.banque.credits.servicecredits.entity.ScoringRequest;
import com.banque.credits.servicecredits.rabbit.NotificationProducer;
import com.banque.credits.servicecredits.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final ClientFeignService clientFeignService;
    private final ScoringFeignService scoringFeignService; // Injection du service Scoring
    private final NotificationProducer notificationProducer; // Injection RabbitMQ

    public ClientDTO getClientInfo(Long clientId) {
        return clientFeignService.getClientById(clientId);
    }

    // Création d’une demande de crédit + appel Scoring + notification
    public Credit createCredit(Credit credit) {
        credit.setDateDemande(LocalDate.now());
        credit.setMontantRembourse(BigDecimal.ZERO);
        credit.setStatut(CreditStatus.EN_ATTENTE);

        // 1. Sauvegarde initiale du crédit
        Credit savedCredit = creditRepository.save(credit);

        try {
            // 2. Récupération du client
            ClientDTO client = clientFeignService.getClientById(credit.getClientId());
            System.out.println("Récupération du client: " + client);

            // 3. Préparation de la requête pour Scoring
            ScoringRequest scoringRequest = new ScoringRequest();
            scoringRequest.setClientId(client.getId());
            scoringRequest.setCreditId(savedCredit.getId());
            scoringRequest.setRevenus(client.getRevenus());
            scoringRequest.setDettes(client.getDettes());
            System.out.println("Appel du service Scoring avec: " + scoringRequest);

            // 4. Appel du service Scoring
            ScoringDTO scoring = scoringFeignService.calculateScore(scoringRequest);
            System.out.println("Réponse du scoring: score=" + scoring.getScore() + ", statut=" + scoring.getStatut());

            // 5. Adaptation du statut selon le score + notification
            if (scoring.getScore() >= 600) {
                savedCredit.setStatut(CreditStatus.APPROUVE);
                savedCredit.setDateApprobation(LocalDate.now());

                // Envoi notification via RabbitMQ
                notificationProducer.sendNotification(
                        "Crédit APPROUVÉ pour le client " + client.getNom() +
                                " | Montant : " + savedCredit.getMontant()
                );
            } else {
                savedCredit.setStatut(CreditStatus.REFUSE);

                // Envoi notification via RabbitMQ
                notificationProducer.sendNotification(
                        "Crédit REFUSÉ pour le client " + client.getNom() +
                                " | Montant : " + savedCredit.getMontant()
                );
            }

            // 6. Mise à jour du crédit avec la décision
            savedCredit = creditRepository.save(savedCredit);

        } catch (Exception e) {
            // En cas d’erreur avec Scoring, le crédit reste en attente
            System.err.println("⚠️ Erreur lors de l’appel au service Scoring : " + e.getMessage());
        }

        return savedCredit;
    }

    public Credit getCreditById(Long id) {
        return creditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crédit non trouvé"));
    }

    public List<Credit> getCreditsByClientId(Long clientId) {
        return creditRepository.findAll()
                .stream()
                .filter(c -> c.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    public Page<Credit> getCredits(Pageable pageable) {
        return creditRepository.findAll(pageable);
    }

    public Credit updateCreditStatus(Long id, CreditStatus statut) {
        Credit credit = getCreditById(id);
        credit.setStatut(statut);
        if (statut == CreditStatus.APPROUVE) {
            credit.setDateApprobation(LocalDate.now());
        }
        return creditRepository.save(credit);
    }

    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
    }

    public Credit addRemboursement(Long id, BigDecimal montant) {
        Credit credit = getCreditById(id);
        credit.setMontantRembourse(credit.getMontantRembourse().add(montant));
        if (credit.getMontantRembourse().compareTo(credit.getMontant()) >= 0) {
            credit.setStatut(CreditStatus.REMBOURSE);
        }
        return creditRepository.save(credit);
    }

    public ByteArrayInputStream exportCreditsToCSV() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter osWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(osWriter)) {

            writer.println("ID;ClientId;Montant;DureeMois;DateDemande;DateApprobation;Statut;MontantRembourse");

            int chunkSize = 500;
            int page = 0;
            Page<Credit> creditPage;

            do {
                creditPage = creditRepository.findAll(Pageable.ofSize(chunkSize).withPage(page));

                for (Credit credit : creditPage.getContent()) {
                    String dateDemande = credit.getDateDemande() != null
                            ? credit.getDateDemande().format(DateTimeFormatter.ISO_DATE) : "";
                    String dateApprobation = credit.getDateApprobation() != null
                            ? credit.getDateApprobation().format(DateTimeFormatter.ISO_DATE) : "";

                    writer.printf("%d;%d;%s;%d;%s;%s;%s;%s%n",
                            credit.getId(),
                            credit.getClientId(),
                            credit.getMontant(),
                            credit.getDureeMois(),
                            dateDemande,
                            dateApprobation,
                            credit.getStatut(),
                            credit.getMontantRembourse()
                    );
                }

                page++;
            } while (creditPage.hasNext());

            writer.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Erreur export CSV", e);
        }
    }

    public Credit setActiveStatus(Long id, boolean status) {
        Credit credit = getCreditById(id);
        if (!status) {
            credit.setStatut(CreditStatus.REFUSE);
        } else {
            credit.setStatut(CreditStatus.EN_ATTENTE);
        }
        return creditRepository.save(credit);
    }

    public CreditTotalsDTO calculateTotals() {
        BigDecimal totalDemandes = creditRepository.findAll()
                .stream()
                .map(Credit::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRembourse = creditRepository.findAll()
                .stream()
                .map(Credit::getMontantRembourse)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CreditTotalsDTO(totalDemandes, totalRembourse);
    }

    public List<CreditStatusDTO> countCreditsByStatusDTO() {
        return creditRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(c -> c.getStatut().name(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(e -> new CreditStatusDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<CreditsByClientDTO> countCreditsByClientDTO() {
        return creditRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Credit::getClientId, Collectors.counting()))
                .entrySet()
                .stream()
                .map(e -> new CreditsByClientDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<MonthlyStatsDTO> monthlyCreditStatsDTO(Integer year) {
        return creditRepository.findAll()
                .stream()
                .filter(c -> year == null || (c.getDateDemande() != null && c.getDateDemande().getYear() == year))
                .collect(Collectors.groupingBy(
                        c -> YearMonth.from(c.getDateDemande()).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(e -> new MonthlyStatsDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

}
