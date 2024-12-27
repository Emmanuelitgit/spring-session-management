package com.spring_boot_session.Configuration;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


@Slf4j
@WebListener
@Configuration
public class SessionListener implements HttpSessionListener {

    // this works when using local storage like the server for storing sessions
    // it also woks for external storage when configured to use RedisIndexedSessionRepository
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("session created for http session:=============");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("Session destroyed for http session:===============");
    }
}