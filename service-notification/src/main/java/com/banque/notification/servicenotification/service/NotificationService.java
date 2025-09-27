package com.banque.notification.servicenotification.service;

import com.banque.notification.servicenotification.dto.NotificationTotalsDTO;
import com.banque.notification.servicenotification.dto.NotificationsByClientDTO;
import com.banque.notification.servicenotification.entity.Notification;
import com.banque.notification.servicenotification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    public NotificationTotalsDTO getNotificationTotals() {
        Long total = notificationRepository.count();
        Long sent = notificationRepository.countBySentTrue();
        Long unsent = total - sent;
        return new NotificationTotalsDTO(total, sent, unsent);
    }

    public List<NotificationsByClientDTO> getNotificationsByClientStats() {
        return notificationRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Notification::getClientId, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new NotificationsByClientDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
    public Notification sendEmail(Long clientId, String message) {
        Notification notification = Notification.builder()
                .clientId(clientId)
                .message(message)
                .sent(false)
                .build();

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("eelouahinajat@gmail.com"); // ici tu peux mettre email réel du client
            mailMessage.setSubject("Notification de la Banque");
            mailMessage.setText(message);
            mailMessage.setFrom("eelouahinajat@gmail.com");

            mailSender.send(mailMessage);
            notification.setSent(true);
        } catch (Exception e) {
            notification.setSent(false);
            e.printStackTrace();
        }

        return notificationRepository.save(notification);
    }



    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByClient(Long clientId) {
        return notificationRepository.findByClientId(clientId);
    }

    public Notification updateStatus(Long id, boolean sent) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée avec id: " + id));
        notification.setSent(sent);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
