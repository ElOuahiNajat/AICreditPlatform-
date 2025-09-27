package com.banque.credits.servicecredits.controller;

import com.banque.credits.servicecredits.dto.*;
import com.banque.credits.servicecredits.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.banque.credits.servicecredits.dto.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class CreditDashboardController {

    private final CreditService creditService;

    @GetMapping("/totals")
    public CreditTotalsDTO getTotals() {
        return creditService.calculateTotals();
    }

    @GetMapping("/credits-by-status")
    public List<CreditStatusDTO> getCreditsByStatus() {
        return creditService.countCreditsByStatusDTO();
    }

    @GetMapping("/credits-by-client")
    public List<CreditsByClientDTO> getCreditsByClient() {
        return creditService.countCreditsByClientDTO();
    }

    @GetMapping("/monthly-stats")
    public List<MonthlyStatsDTO> getMonthlyStats(@RequestParam(required = false) Integer year) {
        return creditService.monthlyCreditStatsDTO(year);
    }
}
