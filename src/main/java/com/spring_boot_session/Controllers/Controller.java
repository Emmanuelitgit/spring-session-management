package com.spring_boot_session.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Enumeration;

@Slf4j
@org.springframework.stereotype.Controller
public class Controller {
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/create-session")
    public ResponseEntity<Cookie> createSession(HttpSession httpSession, HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "Emmanuel");
        cookie.setMaxAge(10);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        log.info("Cookie created successfully");
        return ResponseEntity.ok(cookie);
    }

    @GetMapping("/session")
    public ResponseEntity<String> getUserSession(HttpSession httpSession, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object sessionData = httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        if (sessionData == null){
            return ResponseEntity.ok("Session not found");
        }
        return ResponseEntity.ok(sessionData.toString());
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser(HttpSession httpSession, HttpServletRequest request) {
        return ResponseEntity.ok("User==================");
    }

    // SecurityContextLogoutHandler is very useful when creating a custom logout endpoint.
    // it used to clear the authenticated user details stored in the security context
    // the cookie implementation here is used to clear the session id from the browser upon successfully logout
    @GetMapping("/custom-logout")
    public String customLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        Cookie[] cookies = request.getCookies();
        if (cookies !=null){
            for (Cookie cookie:cookies){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
        }
        return "redirect:/logoutSuccess";
    }

    // the page we redirect users to when logout is successfully
    @GetMapping("/logoutSuccess")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok("You have been signed out");
    }
}