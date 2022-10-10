package com.automobilepartnership.security.oauth;

import com.automobilepartnership.common.BaseInfo;
import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.MemberRepository;
import com.automobilepartnership.domain.member.persistence.Role;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = delegate.loadUser(userRequest).getAttributes();

        OAuthProvider oAuthProvider = OAuthProvider.of(registrationId, userNameAttributeName, attributes);
        Member member = saveOrUpdate(oAuthProvider, registrationId);

        return new UserPrincipal(member, registrationId, attributes);
    }

    private Member saveOrUpdate(OAuthProvider oAuthProvider, String registrationId) {
        Optional<Member> optionalMember = memberRepository.findByEmail(oAuthProvider.getEmail());
        Member member;

        if (optionalMember.isEmpty()) {
            BaseInfo baseInfo = new BaseInfo(oAuthProvider.getName(), 0, null);
            member = Member.builder()
                    .email(oAuthProvider.getEmail())
                    .password(null)
                    .baseInfo(baseInfo)
                    .imageUrl(oAuthProvider.getImageUrl())
                    .provider(registrationId)
                    .role(Role.GUEST)
                    .build();

            memberRepository.save(member);
        } else {
            member = optionalMember.get().updateNameAndImage(oAuthProvider.getName(), oAuthProvider.getImageUrl());
        }
        return member;
    }
}