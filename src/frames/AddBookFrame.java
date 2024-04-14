package frames;

import classes.Book;
import events.AddBookEvent;
import interfaces.AddBookListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddBookFrame {

    private ArrayList<AddBookListener> listeners = new ArrayList<>();

    public AddBookFrame() {
        createAndShowGUI();
    }

    private void triggerAddBookEvent(Book book) {

        AddBookEvent evento = new AddBookEvent(this, book);
        for (AddBookListener listener : listeners) {
            listener.addBook(evento);
        }
    }

    public void addBookListener(AddBookListener listener) {
        listeners.add(listener);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Adicionar classes.Livro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblImageUrl = new JLabel("URL da Imagem:");
        JTextField txtImageUrl = new JTextField();

        JLabel lblTitle = new JLabel("Título:");
        JTextField txtTitle = new JTextField();

        JLabel lblAuthor = new JLabel("Autor:");
        JTextField txtAuthor = new JTextField();

        JButton btnSave = new JButton("Salvar");

        btnSave.addActionListener((e -> {
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String imageUrl = txtImageUrl.getText();

            if(title.isEmpty() || author.isEmpty() || imageUrl.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos");
                return;
            }

            Book book = new Book(title, imageUrl, author);

            this.triggerAddBookEvent(book);
            frame.dispose();
        }));

        panel.add(lblImageUrl);
        panel.add(txtImageUrl);
        panel.add(lblTitle);
        panel.add(txtTitle);
        panel.add(lblAuthor);
        panel.add(txtAuthor);
        panel.add(new JLabel());
        panel.add(btnSave);

        frame.add(panel);
        frame.setVisible(true);
    }

}
