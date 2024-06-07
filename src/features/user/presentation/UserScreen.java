package features.user.presentation;
import custom.NonEditableTableModel;
import di.ServiceLocator;
import features.book.dto.BookDTO;
import features.user.datasource.UserListener;
import features.user.datasource.UserSubscriber;
import features.user.dto.UserDTO;
import features.user.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.*;

public class UserScreen extends JFrame implements ActionListener, UserInterface, UserListener {
    private DefaultTableModel table;
    private UserControllerInterface userControllerInterface;

    private Boolean isAdmin = false;

    public UserScreen(UserSubscriber userSubscriber, UserControllerInterface userControllerInterface) {
        this.userControllerInterface = userControllerInterface;
        setLocationRelativeTo(null);
        setTitle("Gerenciar usuários");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userSubscriber.subscribe(this);

        initializeUI();

        loadUsers();
    }

    private void goBack(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getDashboardView().open(this.isAdmin);
        });
    }


    private void initializeUI() {
        setLayout(new BorderLayout());

        table = new NonEditableTableModel(new Object[]{"ID", "Nome", "Email", "Cargo"}, 0);
        JTable userTable = new JTable(table);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

        JButton backButton = new JButton("< Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserScreen.this.goBack();
            }
        });
        buttonPanel.add(backButton);

        JTextField searchField = new JTextField();
        Dimension size = new Dimension(452, 30);
        searchField.setPreferredSize(size);
        buttonPanel.add(searchField);

        JButton editButton = new JButton("Editar usuário");
        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();

                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(UserScreen.this, "Selecione um livro para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                editUser(selectedRow);
            }
        });
        buttonPanel.add(editButton, BorderLayout.EAST); // Add to the rightmost edge

        JButton addButton = new JButton("Adicionar usuário");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        buttonPanel.add(addButton, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.NORTH);

        userTable.getSelectionModel().addListSelectionListener(event -> {
            editButton.setEnabled(isAdmin && userTable.getSelectedRow() != -1);
        });
    }

    private void loadUsers() {
        table.setRowCount(0);

        List<User> users = userControllerInterface.getUsers();

        for (User user : users) {
            String role = user.isAdmin() ? "Administrador" : "Usuário";
            table.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), role});
        }
    }

    private void addUser() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField= new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel passLabel = new JLabel("Senha:");
        JTextField passField = new JTextField();
        panel.add(passLabel);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar usuário", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailLabel.getText();
            String password = passLabel.getText();
            Boolean admin = false;

            UserDTO userDTO = new UserDTO(name, email, password, admin);

            userControllerInterface.addUser(userDTO);
        }
    }

    public void editUser(int rowIndex) {
        int userId = (int) table.getValueAt(rowIndex, 0);
        String currentName = (String) table.getValueAt(rowIndex, 1);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        nameField.setText(currentName);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField= new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel passLabel = new JLabel("Senha:");
        JTextField passField = new JTextField();
        panel.add(passLabel);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailLabel.getText();
            String password = passLabel.getText();
            Boolean admin = false;

            UserDTO userDTO = new UserDTO(name, email, password, admin);

            userControllerInterface.updateUser(userId, userDTO);
        }
    }

    @Override
    public void open() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void updateData() {
        loadUsers();
    }
}