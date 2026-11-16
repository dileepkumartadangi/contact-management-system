package com.dileep.contactmanagement.service;

import com.dileep.contactmanagement.dto.ContactRequest;
import com.dileep.contactmanagement.dto.ContactResponse;
import com.dileep.contactmanagement.entity.Contact;
import com.dileep.contactmanagement.repository.ContactRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // CREATE CONTACT
    @Override
    public ContactResponse createContact(ContactRequest request) {

        Contact contact = new Contact();

        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());

        Contact savedContact = contactRepository.save(contact);

        return convertToResponse(savedContact);
    }

    // GET ALL CONTACTS
    @Override
    public List<ContactResponse> getAllContacts() {

        return contactRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET CONTACT BY ID
    @Override
    public ContactResponse getContactById(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contact not found with ID: " + id));

        return convertToResponse(contact);
    }

    // UPDATE CONTACT
    @Override
    public ContactResponse updateContact(Long id, ContactRequest request) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contact not found with ID: " + id));

        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());

        Contact updatedContact = contactRepository.save(contact);

        return convertToResponse(updatedContact);
    }

    // DELETE CONTACT
    @Override
    public void deleteContact(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Contact not found with ID: " + id));

        contactRepository.delete(contact);
    }

    // SEARCH CONTACTS
    @Override
    public List<ContactResponse> searchContacts(String name) {

        return contactRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // CONVERT ENTITY TO RESPONSE DTO
    private ContactResponse convertToResponse(Contact contact) {

        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getPhone()
        );
    }
}