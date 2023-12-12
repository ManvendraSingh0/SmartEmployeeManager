package com.smartmanager.component;
import com.smartmanager.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class UserLogoutListener implements ApplicationListener<LogoutSuccessEvent> {

    @Autowired
    private UserLogService userLogService;

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        String username = event.getAuthentication().getName();
        userLogService.trackUserLogout(username);
    }
}
