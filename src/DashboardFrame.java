import jdk.jfr.Event;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

            JLabel titleLabel = new JLabel("Título:");
            livroPanel.add(titleLabel);

            JLabel titleTxt = new JLabel(book.getTitle());
            livroPanel.add(titleTxt);

            JLabel authorLabel = new JLabel("Autor:");
            authorLabel.setBorder(new EmptyBorder(8,0,0,0));
            livroPanel.add(authorLabel);

            JLabel authortxt = new JLabel(book.getAuthor());
            livroPanel.add(authortxt);

            JButton removeButton = new JButton("Remover");
            removeButton.addActionListener(e -> {
                main.removeBook(book);
            });
            livroPanel.add(removeButton);
            livroPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
            livrosPanel.add(livroPanel);
        }

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(livrosPanel, BorderLayout.CENTER);
    }

    public void show(Boolean status){

        frame.setVisible(status);
    }

}
