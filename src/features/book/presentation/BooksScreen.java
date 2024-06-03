package features.book.presentation;

import custom.ui.ButtonEditor;
import features.book.datasource.BookListener;
import features.book.datasource.BookSubscriber;
import features.book.dto.BookDTO;
import features.book.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BooksScreen extends JFrame implements BooksInterface, BookListener {
    private DefaultTableModel table;
    private final BookController bookController;

    public BooksScreen(BookSubscriber bookSubscriber, BookController bookController) {
        setTitle("Biblioteca virtual");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bookSubscriber.subscribe(this);
        this.bookController = bookController;
        bookController.setView(this);

        initializeUI();

        // Load books from the database and display them
        loadBooks();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Table to display books
        table = new NonEditableTableModel(new Object[]{"ID", "Nome", "Autor", "Categoria", "Status", "ISBN", "Ação"}, 0);
        JTable bookTable = new JTable(table);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        TableColumn actionColumn = bookTable.getColumnModel().getColumn(6);

        actionColumn.setCellRenderer(new TableButtonEdit(this));
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
//
//        actionColumn.setCellRenderer(new TableButtonEdit(this));
//        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Button to add a new book
        JButton addButton = new JButton("Adicionar livro");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        add(addButton, BorderLayout.NORTH);

        // Button to mark book as done
        JButton doneButton = new JButton("Alterar status");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    int bookId = (int) table.getValueAt(selectedRow, 0);
                    String rentedString = (String) table.getValueAt(selectedRow, 4);
                    boolean isRented = rentedString == "Alugado";

                    bookController.setRented(bookId, !isRented);
                } else {
                    JOptionPane.showMessageDialog(BooksScreen.this, "Please select a book to mark as done.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(doneButton, BorderLayout.WEST);
    }

    private void loadBooks() {
        // Clear existing rows from the table
        table.setRowCount(0);

        // Populate the table with books from the database
        List<Book> books = bookController.getBooks();
        for (Book book : books) {
            String rentedString = book.isRented() ? "Alugado" : "Disponível";
            table.addRow(new Object[]{book.getId(), book.getName(), book.getAuthor(), book.getCategory(), rentedString, book.getISBN()});
        }
    }

    private void addBook() {
        // Criando painel para os campos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Adicionando campos de entrada
        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel authorLabel = new JLabel("Autor:");
        JTextField authorField = new JTextField();
        panel.add(authorLabel);
        panel.add(authorField);

        JLabel categoryLabel = new JLabel("Categoria:");
        JTextField categoryField = new JTextField();
        panel.add(categoryLabel);
        panel.add(categoryField);

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
        panel.add(isbnLabel);
        panel.add(isbnField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Livro", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            String isbn = isbnField.getText();

            BookDTO bookDTO = new BookDTO(name, author, category, isbn);

            bookController.addBook(bookDTO);
        }
    }

    public void editBook(int rowIndex) {
        int bookId = (int) table.getValueAt(rowIndex, 0);
        String currentName = (String) table.getValueAt(rowIndex, 1);
        String currentAuthor = (String) table.getValueAt(rowIndex, 2);
        String currentCategory = (String) table.getValueAt(rowIndex, 3);
        String currentISBN = (String) table.getValueAt(rowIndex, 5);

        // Criando painel para os campos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        // Adicionando campos de entrada
        JLabel nameLabel = new JLabel("Nome:");
        JTextField nameField = new JTextField();
        nameField.setText(currentName);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel authorLabel = new JLabel("Autor:");
        JTextField authorField = new JTextField();
        authorField.setText(currentAuthor);
        panel.add(authorLabel);
        panel.add(authorField);

        JLabel categoryLabel = new JLabel("Categoria:");
        JTextField categoryField = new JTextField();
        categoryField.setText(currentCategory);
        panel.add(categoryLabel);
        panel.add(categoryField);

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
        isbnField.setText(currentISBN);
        panel.add(isbnLabel);
        panel.add(isbnField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Livro", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            String isbn = isbnField.getText();

            BookDTO bookDTO = new BookDTO(name, author, category, isbn);

            bookController.updateBook(bookId, bookDTO);
        }
    }

    @Override
    public void open() {
        setVisible(true);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(BooksScreen.this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateData() {
        loadBooks();
    }
}