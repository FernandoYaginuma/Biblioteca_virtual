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

        headerPanel.add(btnLivros, BorderLayout.WEST);

        JPanel livrosPanel = new JPanel();
        livrosPanel.setLayout(new BoxLayout(livrosPanel, BoxLayout.X_AXIS));

        for (Book book : books ) {
            System.out.println(book.getTitle());

            JPanel livroPanel = new JPanel();
            livroPanel.setLayout(new BoxLayout(livroPanel, BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("Título: " + book.getTitle());
            livroPanel.add(titleLabel);

            JLabel authorLabel = new JLabel("Autor: " + book.getAuthor());
            livroPanel.add(authorLabel);

            JButton removeButton = new JButton("Remover");
            removeButton.addActionListener(e -> {
                main.removeBook(book);
            });
            livroPanel.add(removeButton);

            livrosPanel.add(livroPanel);
        }

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(livrosPanel, BorderLayout.CENTER);
    }

    public void show(Boolean status){

        frame.setVisible(status);
    }

}
