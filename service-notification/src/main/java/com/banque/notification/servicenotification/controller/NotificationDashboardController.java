package com.banque.notification.servicenotification.controller;

import com.banque.notification.servicenotification.dto.NotificationTotalsDTO;
import com.banque.notification.servicenotification.dto.NotificationsByClientDTO;
import com.banque.notification.servicenotification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dashboard/notifications")
@RequiredArgsConstructor
public class NotificationDashboardController {

    private final NotificationService notificationService;

    @GetMapping("/totals")
    public NotificationTotalsDTO getTotals() {
        return notificationService.getNotificationTotals();
    }

    @GetMapping("/by-client")
    public List<NotificationsByClientDTO> getByClient() {
        return notificationService.getNotificationsByClientStats();
    }
}
