package com.smartmanager.controller;

import com.smartmanager.dao.Contact_repo;
import com.smartmanager.dao.Repository;
import com.smartmanager.entities.Contact;
import com.smartmanager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private Contact_repo contact_repo;
    @Autowired
    private Repository repository;
    @GetMapping("/search/{criteria}/{query}")
    public ResponseEntity<?> search(@PathVariable("criteria") String criteria, @PathVariable("query") String query, Principal principal)
    {
        User user = this.repository.getUserByEmail(principal.getName());
        List<Contact> employees=null;
        System.out.println(query);
        System.out.println(criteria);
        if(query!=null) {
            if(criteria.equals("Name"))
                employees = contact_repo.findByNameContainingAndUser(query,user);
            else if(criteria.equals("SecondName"))  employees = contact_repo.findBySecondNameContainingAndUser(query,user);
            else if(criteria.equals("email"))  employees = contact_repo.findByEmailContainingAndUser(query,user);
        }
        return ResponseEntity.ok(employees);
    }
}
