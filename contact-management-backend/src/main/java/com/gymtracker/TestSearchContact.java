package com.gymtracker;

import com.gymtracker.dao.ContactDAO;
import com.gymtracker.model.Contact;

public class TestSearchContact {

    public static void main(String[] args) {

        ContactDAO contactDAO = new ContactDAO();

        Contact contact = contactDAO.getContactByName("Dileep");

        if (contact != null) {

            System.out.println("Contact Found!");
            System.out.println("ID: " + contact.getId());
            System.out.println("Name: " + contact.getName());
            System.out.println("Phone: " + contact.getPhone());
            System.out.println("Email: " + contact.getEmail());
            System.out.println("Address: " + contact.getAddress());

        } else {

            System.out.println("Contact not found.");
        }
    }
}