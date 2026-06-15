package com.fitting.notificationservice.service;

import com.fitting.notificationservice.dto.NotificationRequest;
import com.fitting.notificationservice.dto.NotificationResponse;
import com.fitting.notificationservice.entity.NotificationStatus;
import com.fitting.notificationservice.entity.NotificationType;

import java.util.List;

public interface NotificationService {

    NotificationResponse create(NotificationRequest request);

    NotificationResponse findById(Long id);

    List<NotificationResponse> findAll();

    List<NotificationResponse> findByRecipientEmail(String email);

    List<NotificationResponse> findByStatus(NotificationStatus status);

    List<NotificationResponse> findByType(NotificationType type);

    List<NotificationResponse> findByReferenceId(Long referenceId);

    NotificationResponse markAsSent(Long id);

    NotificationResponse markAsFailed(Long id);

    void delete(Long id);
}