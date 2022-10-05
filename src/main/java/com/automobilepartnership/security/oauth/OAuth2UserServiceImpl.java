package com.automobilepartnership.security.oauth;

import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.MemberRepository;
import com.automobilepartnership.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = delegate.loadUser(userRequest).getAttributes();

        OAuthProvider oAuthProvider = OAuthProvider.of(registrationId, userNameAttributeName, attributes);
        Member member = saveOrUpdate(oAuthProvider, registrationId);

        return new UserPrincipal(member);
    }

    private Member saveOrUpdate(OAuthProvider oAuthProvider, String registrationId) {
        Optional<Member> optionalMember = memberRepository.findByEmail(oAuthProvider.getEmail());
        Member member;

        if (optionalMember.isEmpty()) {
            member = Member.builder()
                    .email(oAuthProvider.getEmail())
                    .password(null)
                    .name(oAuthProvider.getName())
                    .age(0)
                    .imageUrl(oAuthProvider.getImageUrl())
                    .provider(registrationId)
                    .build();
            memberRepository.save(member);
        } else {
            member = optionalMember.get();
            member.update(oAuthProvider.getName(), oAuthProvider.getImageUrl());
        }
        return member;
    }
}