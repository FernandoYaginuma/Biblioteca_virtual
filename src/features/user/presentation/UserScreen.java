package features.user.presentation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import java.util.ArrayList;
import java.util.List;
public class UserScreen extends JFrame implements ActionListener, UserInterface {

    // Components of the screen
    private JTable userTable;
    private JButton btnNewUser;
    private JButton btnEditUser;
    private JButton btnDeleteUser;

    // List to store users
    private List<User> users = new ArrayList<>();

    public UserScreen() {
        // Configure the frame
        setTitle("User Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Create the user table
        createUserTable();

        // Create and add buttons
        JPanel buttonPanel = new JPanel();
        btnNewUser = new JButton("New User");
        btnNewUser.addActionListener(this);
        buttonPanel.add(btnNewUser);

        btnEditUser = new JButton("Edit User");
        btnEditUser.addActionListener(this);
        btnEditUser.setEnabled(false); // Disable initially
        buttonPanel.add(btnEditUser);

        btnDeleteUser = new JButton("Delete User");
        btnDeleteUser.addActionListener(this);
        btnDeleteUser.setEnabled(false); // Disable initially
        buttonPanel.add(btnDeleteUser);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createUserTable() {
        // Create a data model for the table
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Cargo"}, 0);

        // Populate the table with user data
        for (User user : users) {
//            model.addRow(new Object[]{user.getName(), user.getEmail(), user.getPhone()});
        }

        // Create and add the table to the main panel
        userTable = new JTable(model);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnNewUser) {
            // Open screen to create a new user
            // ...
        } else if (source == btnEditUser) {
            // Get the selected user and open the edit screen
            // ...
        } else if (source == btnDeleteUser) {
            // Delete the selected user and update the table
            // ...
        }
    }

    // Methods to manage users (add, edit, delete)

    public void addUser(User user) {
        users.add(user);
        // Update table
        updateTable();
    }

    public void editUser(User user) {
        // Update the user in the list
        // Update table
        updateTable();
    }

    public void deleteUser(User user) {
        users.remove(user);
        // Update table
        updateTable();
    }

    private void updateTable() {
        // Clear the table
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);

        // Populate the table with user data
        for (User user : users) {
//            model.addRow(new Object[]{user.getName(), user.getEmail(), user.getPhone()});
        }

        // Refresh the table
        userTable.repaint();
    }

    @Override
    public void open() {
        setVisible(true);
    }

    // Class to represent a user
    private class User {
        private String name;
        private String email;
        private String phone;

        // Getters and setters
        // ...
    }
}