package com.gymtracker;

import com.gymtracker.dao.ContactDAO;

public class TestDeleteContact {

    public static void main(String[] args) {

        ContactDAO contactDAO = new ContactDAO();

        int id = 1;

        boolean result = contactDAO.deleteContact(id);

        if (result) {
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Failed to delete contact.");
        }
    }
}