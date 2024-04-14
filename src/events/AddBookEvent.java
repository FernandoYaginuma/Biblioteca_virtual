package events;

import java.util.EventObject;
import classes.Book;

public class AddBookEvent extends EventObject {
    private Book book;

    public AddBookEvent(Object source, Book book) {
        super(source);
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
