package com.banque.notification.servicenotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationTotalsDTO {
    private Long totalNotifications;
    private Long sentNotifications;
    private Long unsentNotifications;
}
