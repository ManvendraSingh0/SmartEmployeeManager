package com.smartmanager.service;

//import com.smartmanager.entities.User;
import com.smartmanager.entities.UserLog;
import org.springframework.data.domain.Page;
public interface LogService {
    Page<UserLog> searchEmployees(int pageNo, int pageSize, String keyword);
}
