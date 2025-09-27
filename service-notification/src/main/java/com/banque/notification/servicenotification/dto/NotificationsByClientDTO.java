package com.banque.notification.servicenotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationsByClientDTO {
    private Long clientId;
    private Long count;
}
