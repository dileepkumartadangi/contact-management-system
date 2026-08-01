package com.gymtracker;

import com.gymtracker.dao.ContactDAO;
import com.gymtracker.model.Contact;

import java.util.List;

public class TestGetContacts {

    public static void main(String[] args) {

        ContactDAO contactDAO = new ContactDAO();

        List<Contact> contacts = contactDAO.getAllContacts();

        for (Contact contact : contacts) {

            System.out.println("ID: " + contact.getId());
            System.out.println("Name: " + contact.getName());
            System.out.println("Phone: " + contact.getPhone());
            System.out.println("Email: " + contact.getEmail());
            System.out.println("Address: " + contact.getAddress());

            System.out.println("---------------------------");
        }
    }
}