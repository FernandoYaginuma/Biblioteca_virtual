import javax.swing.*;
import java.awt.*;

public class AddBookFrame {
    private Main main;
    private JFrame frame;

    public AddBookFrame(Main main) {
        this.main = main;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Adicionar classes.Livro");
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

        JLabel lblCategory = new JLabel("Categoria:");
        JTextField txtCategory = new JTextField();

        JLabel lblISBN = new JLabel("ISBN:");
        JTextField txtISBN = new JTextField();

        JButton btnSave = new JButton("Salvar");

        btnSave.addActionListener((e -> {
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String imageUrl = txtImageUrl.getText();
            String category = txtCategory.getText();
            String ISBN = txtISBN.getText();

            if(title.isEmpty() || author.isEmpty() || imageUrl.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos");
                return;
            }

            Book book = new Book(title, imageUrl, author, category, ISBN);

            main.addBook(book);
            txtTitle.setText("");
            txtAuthor.setText("");
            txtImageUrl.setText("");
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
    }

    public void show(Boolean status){
        this.frame.setVisible(status);
    }

}
