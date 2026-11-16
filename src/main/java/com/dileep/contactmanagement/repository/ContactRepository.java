package com.dileep.contactmanagement.repository;

import com.dileep.contactmanagement.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByNameContainingIgnoreCase(String name);

}