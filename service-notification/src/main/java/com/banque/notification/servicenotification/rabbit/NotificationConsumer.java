package com.banque.notification.servicenotification.rabbit;

import com.banque.notification.servicenotification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${notification.queue}")
    public void receiveNotification(String message) {
        // Ici tu peux récupérer clientId si tu l’inclus dans le message ou utiliser un fixe pour test
        Long clientId = 1L; // exemple
        notificationService.sendEmail(clientId, message);
        System.out.println("📩 Notification reçue et email envoyé : " + message);
    }
}
