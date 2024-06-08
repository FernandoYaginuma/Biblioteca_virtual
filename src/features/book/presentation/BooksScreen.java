package features.book.presentation;

import custom.NonEditableTableModel;
import di.ServiceLocator;
import features.book.datasource.BookListener;
import features.book.datasource.BookSubscriber;
import features.book.dto.BookDTO;
import features.book.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BooksScreen extends JFrame implements BooksInterface, BookListener {
    private DefaultTableModel table;
    private final BookControllerInterface bookControllerInterface;

    private JButton doneButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton addButton;
    private JTextField searchField;
    private Boolean isAdmin = false;
    private Boolean isJustUserBooks = false;
    private int userId;

    public BooksScreen(BookSubscriber bookSubscriber, BookControllerInterface bookControllerInterface) {
        setLocationRelativeTo(null);
        setTitle("Biblioteca virtual");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bookSubscriber.subscribe(this);
        this.bookControllerInterface = bookControllerInterface;
        bookControllerInterface.setView(this);

        initializeUI();
    }

    private void goBack(){
        setVisible(false);
        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getDashboardView().open(this.isAdmin, userId);
        });
    }

    private int getDurationFromUser() {
        int duration = 0;
        String durationString = JOptionPane.showInputDialog(BooksScreen.this, "Informe a duração do aluguel (dias):");
        if (durationString != null) {
            try {
                duration = Integer.parseInt(durationString);
            } catch (NumberFormatException e) {
                // Handle invalid input (not an integer)
                JOptionPane.showMessageDialog(BooksScreen.this, "A duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        return duration;
    }


    private void initializeUI() {
        setLayout(new BorderLayout());
        // Painel superior com botão Voltar


        // Table to display books
        table = new NonEditableTableModel(new Object[]{"ID", "Nome", "Autor", "Categoria", "Status", "Tempo", "ISBN"}, 0);
        JTable bookTable = new JTable(table);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0)); // Set leading alignment and no gaps

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0)); // Set leading alignment and no gaps

        JButton backButton = new JButton("< Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BooksScreen.this.goBack();
            }
        });
        buttonPanel.add(backButton);

        JTextField searchField = new JTextField();
        Dimension size = new Dimension(630, 30);
        searchField.setPreferredSize(size);
        buttonPanel.add(searchField);

        this.searchField = searchField;

        JButton doneButton = new JButton("Alterar status");
        doneButton.setEnabled(false);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(BooksScreen.this, "Selecione um livro para marcar como alugado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int bookId = (int) table.getValueAt(selectedRow, 0);
                String rentedString = (String) table.getValueAt(selectedRow, 4);
                boolean isRented = rentedString == "Alugado";

                if(isRented){
                    bookControllerInterface.returnBook(bookId);
                    updateData();
                    return;
                }

                int duration = getDurationFromUser();

                if (duration > 0) {
                    bookControllerInterface.rent(bookId, userId, duration);

                    updateData();
                    if(isAdmin){
                        JOptionPane.showMessageDialog(BooksScreen.this, "Status do livro alterado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(BooksScreen.this, "Livro alugado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(BooksScreen.this, "Por favor, insira uma duração válida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton editButton = new JButton("Editar livro");
        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();

                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(BooksScreen.this, "Selecione um livro para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                editBook(selectedRow);
            }
        });

        JButton removeButton = new JButton("Excluir livro");
        removeButton.setEnabled(false);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookTable.getSelectedRow();

                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(BooksScreen.this, "Selecione um livro para excluir.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                removeBook(selectedRow);
            }
        });

        JButton addButton = new JButton("Adicionar livro");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchterm = searchField.getText();
                loadBooks(searchterm);
            }
        });

        buttonPanel.add(searchButton, BorderLayout.EAST);
        footerPanel.add(doneButton, BorderLayout.EAST);
        footerPanel.add(editButton, BorderLayout.EAST); // Add to the rightmost edge
        footerPanel.add(removeButton, BorderLayout.EAST); // Add to the rightmost edge
        footerPanel.add(addButton, BorderLayout.EAST);

        this.doneButton = doneButton;
        this.editButton = editButton;
        this.removeButton = removeButton;
        this.addButton = addButton;

        add(buttonPanel, BorderLayout.NORTH);
        add(footerPanel, BorderLayout.SOUTH);

        bookTable.getSelectionModel().addListSelectionListener(event -> {
            doneButton.setEnabled(bookTable.getSelectedRow() != -1);
            editButton.setEnabled(isAdmin && bookTable.getSelectedRow() != -1);
            removeButton.setEnabled(isAdmin && bookTable.getSelectedRow() != -1);
        });
    }

    private void loadBooks(String searchTerm) {
        // Clear existing rows from the table
        table.setRowCount(0);

        // Populate the table with books from the database
        List<Book> books = this.isJustUserBooks ? bookControllerInterface.getBooksFromUser(this.userId, searchTerm) : bookControllerInterface.getBooks(searchTerm);
        for (Book book : books) {
            String rentedString = book.isRented() ? "Alugado" : "Disponível";
            String rentDurationString = book.getRentDuration() + " dia" + ( book.getRentDuration() > 1 ? "s" : "");

            table.addRow(new Object[]{book.getId(), book.getName(), book.getAuthor(), book.getCategory(), rentedString, rentDurationString, book.getISBN()});
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

            bookControllerInterface.addBook(bookDTO);
        }
    }

    public void removeBook(int rowIndex) {
        int bookId = (int) table.getValueAt(rowIndex, 0);

        // Criar JDialog de confirmação
        JDialog confirmationDialog = new JDialog((JFrame) this, "Confirmar exclusão");
        confirmationDialog.setLayout(new BoxLayout(confirmationDialog.getContentPane(), BoxLayout.Y_AXIS));

        JLabel confirmationMessage = new JLabel("Tem certeza que deseja excluir este livro?");
        confirmationDialog.add(confirmationMessage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton yesButton = new JButton("Sim");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookControllerInterface.removeBook(bookId);

                // Fechar JDialog
                confirmationDialog.dispose();
            }
        });
        buttonPanel.add(yesButton);

        JButton noButton = new JButton("Não");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechar JDialog sem excluir
                confirmationDialog.dispose();
            }
        });
        buttonPanel.add(noButton);

        confirmationDialog.add(Box.createVerticalGlue());
        confirmationDialog.add(buttonPanel);

        // Exibir JDialog
        confirmationDialog.pack();
        confirmationDialog.setLocationRelativeTo(this);
        confirmationDialog.setVisible(true);
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

            bookControllerInterface.updateBook(bookId, bookDTO);
        }
    }

    private void updateControls(){
        String changeStatusUserText = isJustUserBooks ? "Devolver" : "Alugar / Devolver";
        this.doneButton.setText(this.isAdmin ? "Alterar status" : changeStatusUserText);
        this.editButton.setVisible(this.isAdmin);
        this.removeButton.setVisible(this.isAdmin);
        this.addButton.setVisible(this.isAdmin);
    }

    @Override
    public void open(Boolean isAdmin, int userId, boolean isJustUserBooks) {
        this.isAdmin = isAdmin;
        this.userId = userId;
        this.isJustUserBooks = isJustUserBooks;
        loadBooks("");
        updateControls();
        setVisible(true);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(BooksScreen.this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateData() {
        loadBooks("");
    }
}