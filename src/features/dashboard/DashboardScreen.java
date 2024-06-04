package features.dashboard;

import di.ServiceLocator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DashboardScreen extends JFrame implements DashboardInterface {

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
            ServiceLocator.getInstance().getBookView().open(true);
        });
    }

    private void openUsers(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getUserView().open();
        });
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        // Painel superior com botão Voltar
        JLabel welcomeLabel = new JLabel("Bem-vindo ao sistema da Biblioteca Virtual :)");

        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Set leading alignment and no gaps


        JButton manageBooksButton = new JButton("Gerenciar Livros");
        JButton manageUsersButton = new JButton("Gerenciar Usuários");

        manageBooksButton.addActionListener(e -> {
           this.openBooks();
        });

        manageUsersButton.addActionListener(e -> {
           this.openUsers();
        });

        buttonPanel.add(manageBooksButton);
        buttonPanel.add(manageUsersButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void open(Boolean isAdmin) {
        setVisible(true);
    }
}