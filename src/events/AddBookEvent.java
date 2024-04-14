package events;

import java.util.EventObject;
import classes.Livro;

public class AddBookEvent extends EventObject {
    private Livro livro;

    public AddBookEvent(Object source, Livro livro) {
        super(source);
        this.livro = livro;
    }

    public Livro getLivro() {
        return livro;
    }
}
