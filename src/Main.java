import classes.Livro;
import classes.Usuario;
import events.AddBookEvent;
import frames.AddBookFrame;
import frames.MainFrame;
import interfaces.AddBookListener;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main implements AddBookListener {
    public List<Livro> livros = new ArrayList<>();
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
        Livro book = event.getLivro();
        System.out.println("Livro: " + book.getTitle());
    }
}