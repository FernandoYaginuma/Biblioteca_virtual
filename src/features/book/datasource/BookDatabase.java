package features.book.datasource;

import features.book.dto.BookDTO;
import features.book.model.Book;

import java.util.List;

public interface BookDatabase {
    void insertBook(BookDTO bookDTO);
    void updateBook(int bookId, BookDTO bookDTO);

    void removeBook(int bookId);
    List<Book> getBooks(String searchTerm);
    void updateBookRented(int bookId, boolean rented);
}
