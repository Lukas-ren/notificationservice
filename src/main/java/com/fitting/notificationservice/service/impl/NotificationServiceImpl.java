package com.fitting.notificationservice.service.impl;

import com.fitting.notificationservice.dto.NotificationRequest;
import com.fitting.notificationservice.dto.NotificationResponse;
import com.fitting.notificationservice.entity.Notification;
import com.fitting.notificationservice.entity.NotificationStatus;
import com.fitting.notificationservice.entity.NotificationType;
import com.fitting.notificationservice.exception.BusinessException;
import com.fitting.notificationservice.exception.ResourceNotFoundException;
import com.fitting.notificationservice.repository.NotificationRepository;
import com.fitting.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationResponse create(NotificationRequest request) {
        log.info("Creando notificación tipo {} para: {}", request.getType(), request.getRecipientEmail());

        Notification notification = Notification.builder()
                .recipientEmail(request.getRecipientEmail())
                .recipientName(request.getRecipientName())
                .type(request.getType())
                .subject(request.getSubject())
                .message(request.getMessage())
                .referenceId(request.getReferenceId())
                .status(NotificationStatus.PENDING)
                .build();

        Notification saved = notificationRepository.save(notification);
        log.info("Notificación creada con ID: {}", saved.getId());
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse findById(Long id) {
        log.debug("Buscando notificación con ID: {}", id);
        return toResponse(getNotificationOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findAll() {
        log.debug("Listando todas las notificaciones");
        return notificationRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findByRecipientEmail(String email) {
        log.debug("Buscando notificaciones para: {}", email);
        return notificationRepository.findByRecipientEmail(email)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findByStatus(NotificationStatus status) {
        log.debug("Buscando notificaciones con estado: {}", status);
        return notificationRepository.findByStatus(status)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findByType(NotificationType type) {
        log.debug("Buscando notificaciones de tipo: {}", type);
        return notificationRepository.findByType(type)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> findByReferenceId(Long referenceId) {
        log.debug("Buscando notificaciones para referencia ID: {}", referenceId);
        return notificationRepository.findByReferenceId(referenceId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public NotificationResponse markAsSent(Long id) {
        log.info("Marcando notificación {} como SENT", id);

        Notification notification = getNotificationOrThrow(id);

        if (notification.getStatus() != NotificationStatus.PENDING) {
            throw new BusinessException(
                    "Solo se pueden marcar como enviadas notificaciones en estado PENDING. " +
                            "Estado actual: " + notification.getStatus());
        }

        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());

        return toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public NotificationResponse markAsFailed(Long id) {
        log.warn("Marcando notificación {} como FAILED", id);

        Notification notification = getNotificationOrThrow(id);

        if (notification.getStatus() != NotificationStatus.PENDING) {
            throw new BusinessException(
                    "Solo se pueden marcar como fallidas notificaciones en estado PENDING. " +
                            "Estado actual: " + notification.getStatus());
        }

        notification.setStatus(NotificationStatus.FAILED);

        return toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando notificación con ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notificación", id);
        }
        notificationRepository.deleteById(id);
        log.info("Notificación eliminada: {}", id);
    }

    // ── Helper repository ───────────────────────────────────────────────────────

    private Notification getNotificationOrThrow(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación", id));
    }

    // ── Mapper interno ──────────────────────────────────────────────────────────

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .recipientEmail(n.getRecipientEmail())
                .recipientName(n.getRecipientName())
                .type(n.getType())
                .subject(n.getSubject())
                .message(n.getMessage())
                .referenceId(n.getReferenceId())
                .status(n.getStatus())
                .createdAt(n.getCreatedAt())
                .sentAt(n.getSentAt())
                .build();
    }
}