package features.book.datasource;

import features.book.dto.BookDTO;
import features.book.model.Book;

import java.util.List;

public interface BookDatabase {
    void insertBook(BookDTO bookDTO);
    void updateBook(int bookId, BookDTO bookDTO);

    void removeBook(int bookId);
    List<Book> getBooks(String searchTerm);
    List<Book> getBooksFromUser(int userId, String searchTerm);

    void rent(int bookId, int userId, int duration);
    void returnBook(int bookId);
}
