package features.book.presentation;

import features.book.dto.BookDTO;
import features.book.model.Book;

import java.util.List;

public interface BookController {
    void setView(BookView view);
    void addBook(BookDTO bookDTO);
    void updateBook(int bookId, BookDTO bookDTO);
    void setRented(int bookId, boolean rented);
    List<Book> getBooks();
}
