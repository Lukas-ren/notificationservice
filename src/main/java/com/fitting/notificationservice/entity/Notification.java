package com.fitting.notificationservice.entity;

import com.fitting.notificationservice.entity.NotificationStatus;
import com.fitting.notificationservice.entity.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String recipientEmail;

    @Column(nullable = false, length = 100)
    private String recipientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    // Referencia opcional al recurso que originó la notificación
    @Column
    private Long referenceId;           // orderId, paymentId, shippingId, etc.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private NotificationStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime sentAt;       // momento en que se envió

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}