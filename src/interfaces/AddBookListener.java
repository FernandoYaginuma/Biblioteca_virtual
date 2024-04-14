package interfaces;

import events.AddBookEvent;

public interface AddBookListener {

    void addBook(AddBookEvent event);
}
