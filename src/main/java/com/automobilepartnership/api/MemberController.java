package com.automobilepartnership.api;

import com.automobilepartnership.api.dto.Response;
import com.automobilepartnership.api.dto.member.LoginRequestDto;
import com.automobilepartnership.api.dto.member.MemberRequestDto;
import com.automobilepartnership.api.dto.member.PasswordRequestDto;
import com.automobilepartnership.domain.member.service.LoginService;
import com.automobilepartnership.domain.member.service.MemberService;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public Response getMemberInfo(@PathVariable String id) {
        return Response.success(HttpStatus.OK, memberService.findMemberDto(Long.valueOf(id)));
    }

    @GetMapping("/me")
    public Response getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(HttpStatus.OK, memberService.findMemberDto(userPrincipal.getId()));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/list")
    public Response getMemberList() {
        return Response.success(HttpStatus.OK, memberService.findMemberDtoList());
    }

    @PostMapping("/join")
    public Response join(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        return Response.success(HttpStatus.CREATED, memberService.join(memberRequestDto));
    }

    @PostMapping("/login")
    public Response login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return Response.success(HttpStatus.CREATED, loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
    }

    @PatchMapping
    public Response updatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        memberService.updatePassword(userPrincipal.getId(), passwordRequestDto.getOldPw(), passwordRequestDto.getNewPw());
        return Response.success(HttpStatus.CREATED);
    }

    @DeleteMapping
    public Response withdraw(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String password) {
        if (!StringUtils.hasText(password)) {
            return Response.failure(HttpStatus.BAD_REQUEST, -1000, "비밀번호를 입력해주세요.");
        }
        memberService.withdraw(userPrincipal.getId(), password);
        return Response.success(HttpStatus.NO_CONTENT);
    }
}