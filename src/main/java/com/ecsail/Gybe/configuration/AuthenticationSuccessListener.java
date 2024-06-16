package com.ecsail.Gybe.configuration;

import com.ecsail.Gybe.service.interfaces.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessListener.class);
    private final AuthenticationService authenticationService;

    public AuthenticationSuccessListener(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        if(authenticationService.recordLoginEvent(username, true) == 1)
            logger.info(username + " logged into the system.");
    }

}
