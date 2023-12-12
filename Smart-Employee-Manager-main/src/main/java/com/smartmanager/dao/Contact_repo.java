package com.smartmanager.dao;

import java.util.List;
import java.util.Optional;

import com.smartmanager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartmanager.entities.Contact;

public interface Contact_repo extends JpaRepository<Contact,Integer> {
	public Optional<Contact> findByEmail(String email);
	public Page<Contact> findByUser_id(int id,Pageable pageable);
	public List<Contact> findByNameContainingAndUser(String name, User user);
	public List<Contact> findBySecondNameContainingAndUser(String name, User user);
	public List<Contact> findByEmailContainingAndUser(String name, User user);
	Page<Contact> findByNameContainingIgnoreCaseOrSecondNameOrEmailContainingIgnoreCaseOrPhoneContaining(String keyword,String keyword1, String keyword2, String keyword3, Pageable pageable);
}
