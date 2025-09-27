package com.banque.notification.servicenotification.controller;

import com.banque.notification.servicenotification.dto.NotificationRequest;
import com.banque.notification.servicenotification.entity.Notification;
import com.banque.notification.servicenotification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 👉 Utilisation d'un JSON body
    @PostMapping
    public Notification sendEmail(@RequestBody NotificationRequest request) {
        return notificationService.sendEmail(request.getClientId(), request.getMessage());
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/client/{clientId}")
    public List<Notification> getNotificationsByClient(@PathVariable Long clientId) {
        return notificationService.getNotificationsByClient(clientId);
    }


    @PatchMapping("/{id}/status")
    public Notification updateStatus(@PathVariable Long id,
                                     @RequestParam boolean sent) {
        return notificationService.updateStatus(id, sent);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
