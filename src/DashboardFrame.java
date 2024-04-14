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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        for (Book book : books ) {
            JLabel label = new JLabel(book.getTitle());
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        headerPanel.add(btnLivros, BorderLayout.WEST);
        headerPanel.add(btnUsuarios, BorderLayout.EAST);

        JPanel livrosPanel = new JPanel();
        livrosPanel.setLayout(new BoxLayout(livrosPanel, BoxLayout.Y_AXIS));

        updateBooks();

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(livrosPanel, BorderLayout.CENTER);
        frame.add(scrollPane);
    }

    public void show(Boolean status){

        frame.setVisible(status);
    }

    private void updateBooks() {
        this.books = main.getBooks();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
