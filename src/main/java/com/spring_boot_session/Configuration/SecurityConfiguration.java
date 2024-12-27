package com.spring_boot_session.Configuration;

import com.spring_boot_session.Filters.CustomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final CustomFilter customFilter;

    @Autowired
    public SecurityConfiguration(CustomFilter customFilter) {
        this.customFilter = customFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CookieClearingLogoutHandler cookies = new CookieClearingLogoutHandler("SessionId");

        return httpSecurity
               .cors(AbstractHttpConfigurer::disable)
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/create-session", "/session", "/logoutSuccess").permitAll()
                    .anyRequest().authenticated();
        })
               .formLogin(AbstractAuthenticationFilterConfigurer->{
                   AbstractAuthenticationFilterConfigurer
                           .permitAll()
                           .successHandler(new SuccessLoginHandler());
               })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer
                            .maximumSessions(1)
                            .maxSessionsPreventsLogin(false)
                            .expiredUrl("/login");
                })
               .logout(logout->{
                   logout
                           .logoutUrl("/logout")
                           .addLogoutHandler(new LogoutHandler())
                           .logoutSuccessHandler(new SuccessLogoutHandler())
                           .permitAll();
               })
               .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}