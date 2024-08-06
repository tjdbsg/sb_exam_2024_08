package com.ll.sapp.global.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Override
    @Transactional
        public OAuth2UserService loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2UserService oAuth2User = super.loadUser(userRequest);

            String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName();
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String oauthId = oAuth2User.getName();

            String oauthType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

            if (!"KAKAO".equals(oauthType)) {
                throw new OAuthTypeMatchNotFoundException("지원되지 않은 공급자 입니다.");
            }

            switch (oauthType) {
                case "KAKAO" -> {
                    Map attributesProperties = (Map) attributes.get("properties");
                    String nickname = (String) attributesProperties.get("nickname");
                    String profile_image = (String) attributesProperties.get("profile_image");
                    String email = "%s@kakao.com".formatted(oauthId);
                    String username = "KAKAO_%s".formatted(oauthId);

                    System.out.println("nickname = " + nickname);
                    System.out.println("profile_image = " + profile_image);
                    System.out.println("email = " + email);
                    System.out.println("username = " + username);
                }
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            return new SecurityUser("user", "password", authorities);
        }
    }
