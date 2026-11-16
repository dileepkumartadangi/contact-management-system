package com.dileep.contactmanagement.service;

import com.dileep.contactmanagement.dto.ContactRequest;
import com.dileep.contactmanagement.dto.ContactResponse;

import java.util.List;

public interface ContactService {

    ContactResponse createContact(ContactRequest request);

    List<ContactResponse> getAllContacts();

    ContactResponse getContactById(Long id);

    ContactResponse updateContact(Long id, ContactRequest request);

    void deleteContact(Long id);

    List<ContactResponse> searchContacts(String name);
}