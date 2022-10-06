package com.automobilepartnership.domain.member.service;

import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.common.exception.CodeMismatchException;
import com.automobilepartnership.common.exception.MemberNotFoundException;
import com.automobilepartnership.domain.member.persistence.AuthenticationCode;
import com.automobilepartnership.domain.member.persistence.AuthenticationCodeRepository;
import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void sendMail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        String code = createCode();
        AuthenticationCode authenticationCode = new AuthenticationCode(member, code);
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
            message.setRecipients(Message.RecipientType.TO, email);
            message.setSubject("회원가입 인증 메일입니다.");
            message.setText(msg, "UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void verifyCode(Long userId, String code) {
        Member member = memberRepository.getReferenceById(userId);
        AuthenticationCode authenticationCode = authenticationCodeRepository.findByMember(member)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

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