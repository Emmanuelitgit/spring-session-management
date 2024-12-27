package com.spring_boot_session.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionEventListener {

    // this works when storing sessions in external storage like redis
    // even when using external storage like redis  the repository type must be configured to use indexed
    @EventListener
    public void processSessionCreatedEvent(SessionCreatedEvent event) {
        log.info("Session created for indexed:=================");
    }

    @EventListener
    public void processSessionDeletedEvent(SessionDeletedEvent event) {
        log.info("Session deleted for indexed:===========");
    }

    @EventListener
    public void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        log.info("Session destroyed for indexed:=========");
    }

    @EventListener
    public void processSessionExpiredEvent(SessionExpiredEvent event) {
        log.info("Session expired for indexed:==============");
    }

}