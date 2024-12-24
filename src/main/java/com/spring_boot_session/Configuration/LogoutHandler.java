package com.spring_boot_session.Configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
          log.info("logout handler:==========");
          Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession(false); // Don't create a new session
        if (session != null) {
            session.invalidate(); // Invalidate session if it exists
        }
        if (cookies != null){
             for (Cookie cookie:cookies){
                 cookie.setPath("/");
                 cookie.setHttpOnly(true);
                 cookie.setMaxAge(0);
                 response.addCookie(cookie);
             }
         }
    }
}
