import classes.Book;
import classes.Usuario;
import events.AddBookEvent;
import frames.AddBookFrame;
import frames.MainFrame;
import interfaces.AddBookListener;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main implements AddBookListener {
    public List<Book> books = new ArrayList<>();
    public List<Usuario> usuarios = new ArrayList<>();
    private MainFrame mainFrame;
    private AddBookFrame addBookFrame;

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    private void init() {
        this.mainFrame = new MainFrame();
        this.mainFrame.getAddBookButton().addActionListener(openAddBookScreenAction);

        this.addBookFrame = new AddBookFrame();
        this.addBookFrame.addBookListener(this);
    }


    private ActionListener openAddBookScreenAction = (e) -> {

        AddBookFrame addBookFrame = new AddBookFrame();
    };

    private ActionListener addBookAction = (e) -> {
        AddBookFrame addBookFrame = new AddBookFrame();
    };

    @Override
    public void addBook(AddBookEvent event) {
        Book book = event.getBook();
        books.add(book);
        System.out.println("Livro adicionado: " + book.getTitle());
    }
}