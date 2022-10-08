package com.automobilepartnership.api;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.domain.member.service.MailService;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public Response sendEmailCode(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        mailService.sendMailToUser(userPrincipal.getId());
        return Response.success(HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public Response verifyCode(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String code) {
        if (!StringUtils.hasText(code)) {
            return Response.failure(HttpStatus.BAD_REQUEST, -1000, "인증 코드를 입력해주세요.");
        }
        mailService.verifyCode(userPrincipal.getId(), code);
        return Response.success(HttpStatus.CREATED);
    }
}