package com.smartmanager.service;

import com.smartmanager.entities.Contact;
import org.springframework.data.domain.Page;

public interface ContactService {
    Page<Contact> searchEmployees(int pageNo, int pageSize, String keyword);
    Page<Contact> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
