package com.smartmanager.controller;

import com.smartmanager.dao.Contact_repo;
import com.smartmanager.dao.Repository;
import com.smartmanager.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Member")
public class MemberController {
    @Autowired
    private Repository repository;
    @Autowired
    private Contact_repo contact_repo;
    @GetMapping("/MemberProfile")
    public String memberProfile(Model model, Principal principal)
    {
        String username=principal.getName();
        Optional<Contact> member=contact_repo.findByEmail(username);
        System.out.println(member);
        System.out.println("HKGGGGGGGGGGGGGGGGGGGGGGGGKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println(member.get().getImage());
        model.addAttribute("single", member.get());
        return "memberProfile";
    }
}
