package features.book.presentation;

import features.book.dto.BookDTO;
import features.book.model.Book;

import java.util.List;

public interface BookControllerInterface {
    void setView(BooksInterface view);
    void addBook(BookDTO bookDTO);
    void updateBook(int bookId, BookDTO bookDTO);

    void removeBook(int bookId);
    void setRented(int bookId, boolean rented);
    List<Book> getBooks(String searchTerm);
}
