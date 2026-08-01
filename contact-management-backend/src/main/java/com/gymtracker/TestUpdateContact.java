package com.gymtracker;

import com.gymtracker.dao.ContactDAO;
import com.gymtracker.model.Contact;

public class TestUpdateContact {

    public static void main(String[] args) {

        Contact contact = new Contact(
                "Dileep Kumar",
                "9999999999",
                "dileepkumar@gmail.com",
                "Visakhapatnam"
        );

        contact.setId(1);

        ContactDAO contactDAO = new ContactDAO();

        boolean result = contactDAO.updateContact(contact);

        if (result) {
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Failed to update contact.");
        }
    }
}