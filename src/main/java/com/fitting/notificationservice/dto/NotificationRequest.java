package com.fitting.notificationservice.dto;

import com.fitting.notificationservice.entity.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Datos para crear una notificación")
public class NotificationRequest {

    @Schema(description = "Email del destinatario", example = "juan@fitting.com")
    @NotBlank(message = "El email del destinatario es obligatorio")
    @Email
    @Size(max = 150)
    private String recipientEmail;

    @Schema(description = "Nombre del destinatario", example = "Juan Pérez")
    @NotBlank(message = "El nombre del destinatario es obligatorio")
    @Size(max = 100)
    private String recipientName;

    @Schema(description = "Tipo de notificación", example = "ORDER_CONFIRMED",
            allowableValues = {"ORDER_CREATED","ORDER_CONFIRMED","ORDER_CANCELLED",
                    "PAYMENT_COMPLETED","PAYMENT_FAILED","PAYMENT_REFUNDED",
                    "SHIPPING_DISPATCHED","SHIPPING_DELIVERED"})
    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType type;

    @Schema(description = "Asunto de la notificación", example = "Tu orden fue confirmada")
    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 150)
    private String subject;

    @Schema(description = "Mensaje de la notificación",
            example = "Tu orden ORD-20260522-0001 ha sido confirmada.")
    @NotBlank(message = "El mensaje es obligatorio")
    private String message;

    @Schema(description = "ID del recurso que origina la notificación", example = "1")
    private Long referenceId;
}