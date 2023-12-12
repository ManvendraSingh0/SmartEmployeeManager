package com.smartmanager.service;

import com.smartmanager.dao.Contact_repo;
import com.smartmanager.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    private Contact_repo repo;
    @Override
    public Page<Contact> searchEmployees(int pageNo, int pageSize, String keyword) {
//        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repo.findByNameContainingIgnoreCaseOrSecondNameOrEmailContainingIgnoreCaseOrPhoneContaining(keyword,keyword, keyword,keyword,pageable);
    }

    @Override
    public Page<Contact> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repo.findAll(pageable);
    }
}
