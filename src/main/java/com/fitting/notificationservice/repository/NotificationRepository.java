package com.fitting.notificationservice.repository;

import com.fitting.notificationservice.entity.Notification;
import com.fitting.notificationservice.entity.NotificationStatus;
import com.fitting.notificationservice.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientEmail(String recipientEmail);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByType(NotificationType type);

    List<Notification> findByReferenceId(Long referenceId);
}