package com.gymtracker.dao;

import com.gymtracker.database.DBConnection;
import com.gymtracker.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    // CREATE - Add a new contact
    public boolean addContact(Contact contact) {

        String sql = "INSERT INTO contacts (name, phone, email, address) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhone());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getAddress());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // READ - Get all contacts
    public List<Contact> getAllContacts() {

        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT * FROM contacts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Contact contact = new Contact();

                contact.setId(resultSet.getInt("id"));
                contact.setName(resultSet.getString("name"));
                contact.setPhone(resultSet.getString("phone"));
                contact.setEmail(resultSet.getString("email"));
                contact.setAddress(resultSet.getString("address"));

                contacts.add(contact);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return contacts;
    }
    public Contact getContactByName(String name) {

        String sql = "SELECT * FROM contacts WHERE name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Contact contact = new Contact();

                    contact.setId(resultSet.getInt("id"));
                    contact.setName(resultSet.getString("name"));
                    contact.setPhone(resultSet.getString("phone"));
                    contact.setEmail(resultSet.getString("email"));
                    contact.setAddress(resultSet.getString("address"));

                    return contact;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
    public boolean updateContact(Contact contact) {

        String sql = "UPDATE contacts " +
                "SET name = ?, phone = ?, email = ?, address = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getPhone());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getAddress());
            statement.setInt(5, contact.getId());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
        public boolean deleteContact(int id) {

            String sql = "DELETE FROM contacts WHERE id = ?";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, id);

                int rowsAffected = statement.executeUpdate();

                return rowsAffected > 0;

            } catch (SQLException e) {

                e.printStackTrace();
                return false;
            }

    }

}