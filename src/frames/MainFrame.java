package frames;

import jdk.jfr.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class MainFrame extends Event {

    JButton btnLivros = new JButton("Adicionar Livros");
    JFrame frame;

    public MainFrame() {
        criarTela();
    }

    private void criarTela() {
        this.frame = new JFrame("Cadastro de Livros e Usuários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        btnLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "classes.Livro adicionado!");
            }
        });

        JButton btnUsuarios = new JButton("Adicionar Usuários");
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Usuário adicionado!");
            }
        });

        headerPanel.add(btnLivros, BorderLayout.WEST);
        headerPanel.add(btnUsuarios, BorderLayout.EAST);

        JPanel livrosPanel = new JPanel();
        livrosPanel.setLayout(new BoxLayout(livrosPanel, BoxLayout.Y_AXIS));

        atualizarListagem();

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(livrosPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public JButton getAddBookButton(){
        return this.btnLivros;
    }

    public JFrame getFrame(){
        return this.frame;
    }

    private void atualizarListagem() {
    }
}