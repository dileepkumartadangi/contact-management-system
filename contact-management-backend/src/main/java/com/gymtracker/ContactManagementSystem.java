package com.gymtracker;

import com.gymtracker.dao.ContactDAO;
import com.gymtracker.model.Contact;

import java.util.List;
import java.util.Scanner;

public class ContactManagementSystem {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ContactDAO contactDAO = new ContactDAO();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("       CONTACT MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Exit");
            System.out.println("========================================");

            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.println();
                    System.out.println("----- Add New Contact -----");

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();

                    Contact contact = new Contact(name, phone, email, address);

                    boolean result = contactDAO.addContact(contact);

                    if (result) {
                        System.out.println("Contact added successfully!");
                    } else {
                        System.out.println("Failed to add contact.");
                    }

                    break;

                case 2:

                    System.out.println();
                    System.out.println("----- All Contacts -----");

                    List<Contact> contacts = contactDAO.getAllContacts();

                    if (contacts.isEmpty()) {

                        System.out.println("No contacts found.");

                    } else {

                        for (Contact c : contacts) {

                            System.out.println("ID: " + c.getId());
                            System.out.println("Name: " + c.getName());
                            System.out.println("Phone: " + c.getPhone());
                            System.out.println("Email: " + c.getEmail());
                            System.out.println("Address: " + c.getAddress());

                            System.out.println("---------------------------");
                        }
                    }

                    break;

                case 3:

                    System.out.println();
                    System.out.println("----- Search Contact -----");

                    System.out.print("Enter contact name: ");
                    String searchName = scanner.nextLine();

                    Contact foundContact = contactDAO.getContactByName(searchName);

                    if (foundContact != null) {

                        System.out.println();
                        System.out.println("Contact Found!");
                        System.out.println("ID: " + foundContact.getId());
                        System.out.println("Name: " + foundContact.getName());
                        System.out.println("Phone: " + foundContact.getPhone());
                        System.out.println("Email: " + foundContact.getEmail());
                        System.out.println("Address: " + foundContact.getAddress());

                    } else {

                        System.out.println("Contact not found.");
                    }

                    break;

                case 4:

                    System.out.println();
                    System.out.println("----- Update Contact -----");

                    System.out.print("Enter contact ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new name: ");
                    String updateName = scanner.nextLine();

                    System.out.print("Enter new phone: ");
                    String updatePhone = scanner.nextLine();

                    System.out.print("Enter new email: ");
                    String updateEmail = scanner.nextLine();

                    System.out.print("Enter new address: ");
                    String updateAddress = scanner.nextLine();

                    Contact updateContact = new Contact(
                            updateName,
                            updatePhone,
                            updateEmail,
                            updateAddress
                    );

                    updateContact.setId(updateId);

                    boolean updateResult = contactDAO.updateContact(updateContact);

                    if (updateResult) {
                        System.out.println("Contact updated successfully!");
                    } else {
                        System.out.println("Contact not found or update failed.");
                    }

                    break;

                case 5:

                    System.out.println();
                    System.out.println("----- Delete Contact -----");

                    System.out.print("Enter contact ID to delete: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid ID! Please enter a number.");
                        scanner.nextLine();
                        break;
                    }

                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Are you sure you want to delete this contact? (Y/N): ");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("Y")) {

                        boolean deleteResult = contactDAO.deleteContact(deleteId);

                        if (deleteResult) {
                            System.out.println("Contact deleted successfully!");
                        } else {
                            System.out.println("Contact not found or deletion failed.");
                        }

                    } else {

                        System.out.println("Deletion cancelled.");
                    }

                    break;

                case 6:
                    running = false;
                    System.out.println("Thank you for using Contact Management System!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}