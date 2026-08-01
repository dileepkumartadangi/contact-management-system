package com.gymtracker;

import com.gymtracker.dao.ContactDAO;
import com.gymtracker.model.Contact;

public class TestAddContact {

    public static void main(String[] args) {

        Contact contact = new Contact(
                "Dileep",
                "9014873729",
                "dileep@gmail.com",
                "Visakhapatnam"
        );

        ContactDAO contactDAO = new ContactDAO();

        boolean result = contactDAO.addContact(contact);

        if (result) {
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Failed to add contact.");
        }
    }
}