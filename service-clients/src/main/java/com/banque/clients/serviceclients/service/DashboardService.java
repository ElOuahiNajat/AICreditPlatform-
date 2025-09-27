package com.banque.clients.serviceclients.service;

import com.banque.clients.serviceclients.entitiy.DashboardStats;
import com.banque.clients.serviceclients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ClientRepository clientRepository;

    public DashboardStats getStats() {
        long total = clientRepository.count();
        long active = clientRepository.countByActive(true);
        long inactive = clientRepository.countByActive(false);

        return new DashboardStats(total, active, inactive);
    }
}
