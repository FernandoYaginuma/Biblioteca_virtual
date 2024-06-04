package features.auth.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener, LoginInterface {

    // Componentes da tela
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;

    private LoginController loginController = new LoginController(this);

    public LoginScreen() {
        setLocationRelativeTo(null);
        setTitle("Tela de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLayout(new GridLayout(3, 1));

        // Criar e adicionar componentes
        JLabel labelUsuario = new JLabel("Usuário:");
        add(labelUsuario);
        campoUsuario = new JTextField();
        add(campoUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        add(labelSenha);
        campoSenha = new JPasswordField();
        add(campoSenha);

        botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(this);
        add(botaoLogin);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = campoUsuario.getText();
        String password = new String(campoSenha.getPassword());

        loginController.login(email, password);
    }

    @Override
    public void open() {
        setVisible(true);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(LoginScreen.this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

}