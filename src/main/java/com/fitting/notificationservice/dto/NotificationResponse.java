package com.fitting.notificationservice.dto;

import com.fitting.notificationservice.entity.NotificationStatus;
import com.fitting.notificationservice.entity.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos de la notificación retornados por la API")
public class NotificationResponse {

    @Schema(description = "ID de la notificación", example = "1")
    private Long id;

    @Schema(description = "Email del destinatario", example = "juan@fitting.com")
    private String recipientEmail;

    @Schema(description = "Nombre del destinatario", example = "Juan Pérez")
    private String recipientName;

    @Schema(description = "Tipo de notificación", example = "ORDER_CONFIRMED")
    private NotificationType type;

    @Schema(description = "Asunto", example = "Tu orden fue confirmada")
    private String subject;

    @Schema(description = "Mensaje", example = "Tu orden ORD-20260522-0001 ha sido confirmada.")
    private String message;

    @Schema(description = "ID del recurso origen", example = "1")
    private Long referenceId;

    @Schema(description = "Estado de la notificación", example = "PENDING",
            allowableValues = {"PENDING","SENT","FAILED"})
    private NotificationStatus status;

    @Schema(description = "Fecha de creación", example = "2026-05-22T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de envío", example = "2026-05-22T10:31:00")
    private LocalDateTime sentAt;
}