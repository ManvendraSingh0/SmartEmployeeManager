package com.smartmanager.service;
import com.smartmanager.dao.UserLogRepository;
import com.smartmanager.entities.User;
import com.smartmanager.entities.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
public class UserLogService implements LogService {
    @Autowired
    private UserLogRepository userLogRepository;
    public void trackUserLogin(String email) {
        UserLog userLog = new UserLog(email, new Date(), null);
        userLogRepository.save(userLog);
    }
    public void trackUserLogout(String email) {
        UserLog userLog = userLogRepository.findByUsernameAndLogoutTimeIsNull(email);
        System.out.println("logged out checking..........");
        if (userLog != null) {
            System.out.println("logged out broooooooooooooooo..........");
            userLog.setLogoutTime(new Date());
            userLogRepository.save(userLog);
        }
    }
    @Override
    public Page<UserLog> searchEmployees(int pageNo, int pageSize, String keyword) {
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userLogRepository.findByUsernameContainingIgnoreCaseAndLogoutTimeIsNotNull(keyword,pageable);
    }
}

