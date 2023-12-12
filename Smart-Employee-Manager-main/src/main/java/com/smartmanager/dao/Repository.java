package com.smartmanager.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.smartmanager.entities.User;
public interface Repository extends JpaRepository<User, Integer>
{
   public User findByEmail(String name);
   public User getUserByEmail(String name);
   Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrRoleContainingIgnoreCase(String keyword1, String keyword2,String keyword3, Pageable pageable);
}
     