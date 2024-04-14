import jdk.jfr.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DashboardFrame extends Event {
    private Main main;
    private ArrayList<Book> books;

    JButton btnLivros = new JButton("Adicionar Livros");
    JFrame frame;

    public DashboardFrame(Main main) {
        this.main = main;
        this.books = this.main.getBooks();
        criarTela();
    }

    private void criarTela() {
        this.frame = new JFrame("Cadastro de Livros e Usuários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());

        btnLivros.addActionListener((e) -> {
            main.openAddBookFrame();
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

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(livrosPanel, BorderLayout.CENTER);
    }

    public void show(Boolean status){

        frame.setVisible(status);
    }

    public void updateBooks(ArrayList<Book> newbooks) {
        
        for (Book book : newbooks ) {
            System.out.println(book.getTitle());

            JLabel label = new JLabel(book.getTitle());
            frame.add(label);
        }

        this.books = newbooks;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
