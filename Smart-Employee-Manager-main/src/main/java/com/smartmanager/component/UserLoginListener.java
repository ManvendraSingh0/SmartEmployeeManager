package com.smartmanager.component;

import com.smartmanager.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class UserLoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserLogService userLogService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        userLogService.trackUserLogin(username);
    }
}
