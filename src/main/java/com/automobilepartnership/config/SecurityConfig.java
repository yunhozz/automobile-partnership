package com.automobilepartnership.config;

import com.automobilepartnership.security.jwt.JwtAccessDeniedHandler;
import com.automobilepartnership.security.jwt.JwtAuthenticationEntryPoint;
import com.automobilepartnership.security.jwt.JwtFilter;
import com.automobilepartnership.security.jwt.JwtProvider;
import com.automobilepartnership.security.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final OAuth2UserServiceImpl oAuth2UserService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtProvider);

        security
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/mail/**").hasRole("GUEST")
                .antMatchers("/api/counsel/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().permitAll();

        security
                // header
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()

                // session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                // jwt
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()

                // oauth2
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")

                .and()

                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")

                .and()

                .userInfoEndpoint()
                .userService(oAuth2UserService);

        return security.build();
    }
}