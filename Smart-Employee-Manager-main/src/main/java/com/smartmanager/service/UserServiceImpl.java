package com.smartmanager.service;

import com.smartmanager.dao.Repository;
//import com.smartmanager.entities.Contact;
import com.smartmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Repository repository;
    @Override
    public Page<User> searchEmployees(int pageNo, int pageSize, String keyword) {
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrRoleContainingIgnoreCase(keyword,keyword,keyword,pageable);
    }
}
