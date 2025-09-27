package com.banque.notification.servicenotification.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private Long clientId;
    private String message;
}
