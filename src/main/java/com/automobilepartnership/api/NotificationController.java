package com.automobilepartnership.api;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.api.dto.notification.NotificationRequestDto;
import com.automobilepartnership.domain.notification.persistence.NotificationRepository;
import com.automobilepartnership.domain.notification.service.ConnectService;
import com.automobilepartnership.domain.notification.service.NotificationService;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final ConnectService connectService;
    private final NotificationRepository notificationRepository;

    @GetMapping("/{id}")
    public Response getNotificationRedirectUrl(@PathVariable String id) {
        return Response.success(HttpStatus.OK, notificationRepository.findRedirectUrlById(Long.valueOf(id)));
    }

    @GetMapping("/list")
    public Response getNotificationListByUser(@AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable) {
        return Response.success(HttpStatus.OK, notificationRepository.findNotificationDtoListByUserId(userPrincipal.getId(), pageable));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/auth/list")
    public Response getNotificationListAboutHoleUsers() {
        return Response.success(HttpStatus.OK, notificationService.findNotificationDtoList());
    }

    @PostMapping(value = "/connect", produces = "text/event-stream")
    public Response connectWithUser(@RequestHeader(value = "last-event-id", defaultValue = "") String lastEventId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        connectService.connectWithUser(userPrincipal.getId(), lastEventId);
        return Response.success(HttpStatus.CREATED);
    }

    @PostMapping
    public Response sendNotification(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String receiverId, @Valid @RequestBody NotificationRequestDto notificationRequestDto) {
        return Response.success(HttpStatus.CREATED, notificationService.sendToClientAndSave(userPrincipal.getId(), Long.valueOf(receiverId), notificationRequestDto));
    }

    @DeleteMapping
    public Response deleteNotification(@RequestParam String id) {
        notificationService.delete(Long.valueOf(id));
        return Response.success(HttpStatus.NO_CONTENT);
    }
}