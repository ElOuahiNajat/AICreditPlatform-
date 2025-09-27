package com.banque.clients.serviceclients.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStats {
    private long totalClients;
    private long activeClients;
    private long inactiveClients;
}
