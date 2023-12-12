package com.smartmanager.service;

import com.smartmanager.entities.Contact;
import com.smartmanager.entities.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> searchEmployees(int pageNo, int pageSize, String keyword);
}
