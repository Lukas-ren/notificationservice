package com.fitting.notificationservice.controller;

import com.fitting.notificationservice.dto.NotificationRequest;
import com.fitting.notificationservice.dto.NotificationResponse;
import com.fitting.notificationservice.entity.NotificationStatus;
import com.fitting.notificationservice.entity.NotificationType;
import com.fitting.notificationservice.service.NotificationService;
import com.fitting.notificationservice.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> create(
            @Valid @RequestBody NotificationRequest request) {
        log.info("POST /api/v1/notifications — tipo: {}", request.getType());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Notificación creada",
                        notificationService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> findAll() {
        log.info("GET /api/v1/notifications");
        return ResponseEntity.ok(ApiResponse.ok("Lista de notificaciones",
                notificationService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> findById(@PathVariable Long id) {
        log.info("GET /api/v1/notifications/{}", id);
        return ResponseEntity.ok(ApiResponse.ok("Notificación encontrada",
                notificationService.findById(id)));
    }

    @GetMapping("/recipient/{email}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> findByEmail(
            @PathVariable String email) {
        log.info("GET /api/v1/notifications/recipient/{}", email);
        return ResponseEntity.ok(ApiResponse.ok("Notificaciones del destinatario",
                notificationService.findByRecipientEmail(email)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> findByStatus(
            @PathVariable NotificationStatus status) {
        log.info("GET /api/v1/notifications/status/{}", status);
        return ResponseEntity.ok(ApiResponse.ok("Notificaciones por estado",
                notificationService.findByStatus(status)));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> findByType(
            @PathVariable NotificationType type) {
        log.info("GET /api/v1/notifications/type/{}", type);
        return ResponseEntity.ok(ApiResponse.ok("Notificaciones por tipo",
                notificationService.findByType(type)));
    }

    @GetMapping("/reference/{referenceId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> findByReferenceId(
            @PathVariable Long referenceId) {
        log.info("GET /api/v1/notifications/reference/{}", referenceId);
        return ResponseEntity.ok(ApiResponse.ok("Notificaciones por referencia",
                notificationService.findByReferenceId(referenceId)));
    }

    @PatchMapping("/{id}/sent")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsSent(@PathVariable Long id) {
        log.info("PATCH /api/v1/notifications/{}/sent", id);
        return ResponseEntity.ok(ApiResponse.ok("Notificación marcada como enviada",
                notificationService.markAsSent(id)));
    }

    @PatchMapping("/{id}/failed")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsFailed(@PathVariable Long id) {
        log.info("PATCH /api/v1/notifications/{}/failed", id);
        return ResponseEntity.ok(ApiResponse.ok("Notificación marcada como fallida",
                notificationService.markAsFailed(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("DELETE /api/v1/notifications/{}", id);
        notificationService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Notificación eliminada", null));
    }
}