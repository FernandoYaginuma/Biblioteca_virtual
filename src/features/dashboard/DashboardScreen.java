package features.dashboard;

import di.ServiceLocator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DashboardScreen extends JFrame implements DashboardInterface {


    private JButton manageUsersButton;
    private JButton manageBooksButton;
    private JButton userBooksButton;

    private Boolean isAdmin = false;
    private int userId;

    public DashboardScreen() {
        setLocationRelativeTo(null);
        setTitle("Dashboard");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeUI();
    }

    private void openBooks(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getBookView().open(this.isAdmin, this.userId, false);
        });
    }

    private void openUserBooks(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getBookView().open(this.isAdmin, this.userId, true);
        });
    }

    private void openUsers(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getUserView().open(this.userId);
        });
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        // Painel superior com botão Voltar
        JLabel welcomeLabel = new JLabel("Bem-vindo ao sistema da Biblioteca Virtual :)");

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());


        JButton exitButton = new JButton("Sair");
        exitButton.addActionListener(e -> {
            this.logout();
        });

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(exitButton, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Set leading alignment and no gaps


        JButton manageBooksButton = new JButton("Gerenciar Livros");
        JButton userBooksButton = new JButton("Meus livros");
        JButton manageUsersButton = new JButton("Gerenciar Usuários");

        manageBooksButton.addActionListener(e -> {
           this.openBooks();
        });

        manageUsersButton.addActionListener(e -> {
           this.openUsers();
        });

        userBooksButton.addActionListener(e -> {
            this.openUserBooks();
        });

        this.manageBooksButton = manageBooksButton;
        this.manageUsersButton = manageUsersButton;
        this.userBooksButton = userBooksButton;

        buttonPanel.add(manageBooksButton);
        buttonPanel.add(manageUsersButton);
        buttonPanel.add(userBooksButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void open(Boolean isAdmin, int userId) {
        this.isAdmin = isAdmin;
        this.userId = userId;
        this.manageUsersButton.setVisible(isAdmin);
        this.manageBooksButton.setText(isAdmin ? "Gerenciar Livros" : "Alugar livros");
        this.userBooksButton.setVisible(!isAdmin);
        setVisible(true);
    }

    private void logout(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getLoginView().open();
        });
    }
}