package com.banque.credits.servicecredits.service;

import com.banque.credits.servicecredits.entity.ScoringDTO;
import com.banque.credits.servicecredits.entity.ScoringRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-scoring", url = "http://localhost:8082")
public interface ScoringFeignService {

    @PostMapping(value = "/api/scoring/calculate", consumes = "application/json")
    ScoringDTO calculateScore(@RequestBody ScoringRequest request);
}
