package com.fitting.notificationservice.dto;

import com.fitting.notificationservice.entity.NotificationType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @NotBlank(message = "El email del destinatario es obligatorio")
    @Email(message = "El email no tiene formato válido")
    @Size(max = 150)
    private String recipientEmail;

    @NotBlank(message = "El nombre del destinatario es obligatorio")
    @Size(max = 100)
    private String recipientName;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType type;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 150)
    private String subject;

    @NotBlank(message = "El mensaje es obligatorio")
    private String message;

    private Long referenceId;
}