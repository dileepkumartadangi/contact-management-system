package com.dileep.contactmanagement.controller;

import com.dileep.contactmanagement.dto.ContactRequest;
import com.dileep.contactmanagement.dto.ContactResponse;
import com.dileep.contactmanagement.service.ContactService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ContactResponse> createContact(
            @RequestBody ContactRequest request) {

        ContactResponse response = contactService.createContact(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAllContacts() {

        return ResponseEntity.ok(contactService.getAllContacts());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(
            @PathVariable Long id) {

        return ResponseEntity.ok(contactService.getContactById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(
            @PathVariable Long id,
            @RequestBody ContactRequest request) {

        return ResponseEntity.ok(contactService.updateContact(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {

        contactService.deleteContact(id);

        return ResponseEntity.noContent().build();
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<ContactResponse>> searchContacts(
            @RequestParam String name) {

        return ResponseEntity.ok(contactService.searchContacts(name));
    }
}