package com.automobilepartnership.domain.member.service;

import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.domain.member.service.exception.AuthCodeNotFoundException;
import com.automobilepartnership.domain.member.service.exception.CodeMismatchException;
import com.automobilepartnership.domain.member.persistence.entity.AuthenticationCode;
import com.automobilepartnership.domain.member.persistence.repository.AuthenticationCodeRepository;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import com.automobilepartnership.domain.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    private final static Long EMAIL_CODE_EXPIRATION_TIME = 5L; // 5분 후 만료

    @Transactional
    public void sendMailToUser(Long userId) {
        Member member = memberRepository.getReferenceById(userId);
        String code = createCode();
        AuthenticationCode authenticationCode = new AuthenticationCode(member, code, LocalDateTime.now().plusMinutes(EMAIL_CODE_EXPIRATION_TIME));
        authenticationCodeRepository.save(authenticationCode);

        MimeMessage message = javaMailSender.createMimeMessage();
        String msg = "";

        msg += "<div style='margin:100px;'>";
        msg += "<h1> 안녕하세요! 회원가입을 해주셔서 감사드립니다. </h1>";
        msg += "<br>";
        msg += "<p>아래 코드를 인증 창으로 돌아가 입력해주세요.<p>";
        msg += "<br>";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msg += "<div style='font-size:130%'>";
        msg += "CODE : <strong>";
        msg += code + "</strong><div><br/> ";
        msg += "</div>";

        try {
            message.setFrom(new InternetAddress("qkrdbsgh1121@naver.com"));
            message.setRecipients(Message.RecipientType.TO, member.getEmail());
            message.setSubject("회원가입 인증 메일입니다.");
            message.setText(msg, "UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void verifyCode(Long userId, String code) {
        Member member = memberRepository.getReferenceById(userId);
        AuthenticationCode authenticationCode = authenticationCodeRepository.findByMemberAndExpireDateAfter(member, LocalDateTime.now())
                .orElseThrow(() -> new AuthCodeNotFoundException(ErrorCode.AUTH_CODE_NOT_FOUND));

        if (!authenticationCode.getCode().equals(code)) {
            throw new CodeMismatchException(ErrorCode.CODE_MISMATCH);
        }
        authenticationCode.verify(); // 인증 성공
    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = random.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    code.append((char) ((int) (random.nextInt(26)) + 97)); // a~z
                    break;
                case 1:
                    code.append((char) ((int) (random.nextInt(26)) + 65)); // A~Z
                    break;
                case 2:
                    code.append((random.nextInt(10))); // 0~9
                    break;
            }
        }
        return code.toString();
    }
}