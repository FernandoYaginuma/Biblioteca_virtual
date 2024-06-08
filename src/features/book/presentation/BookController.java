package features.book.presentation;

import features.book.datasource.BookDatabase;
import features.book.dto.BookDTO;
import features.book.model.Book;

import java.util.List;

public class BookController implements BookControllerInterface {
    private BooksInterface bookView;
    private final BookDatabase bookDatabase;

    public BookController(BookDatabase bookDatabase) {
        this.bookDatabase = bookDatabase;
    }

    @Override
    public void setView(BooksInterface view) {
        this.bookView = view;
    }

    private boolean isBookValid(BookDTO bookDTO){
        if(bookDTO.name.isEmpty()) {
            showErrorMessage("O nome é obrigatório");
            return false;
        }

        if(bookDTO.author.isEmpty()) {
            showErrorMessage("O Autor é obrigatório");
            return false;
        }

        if(bookDTO.category.isEmpty()) {
            showErrorMessage("A category é obrigatório");
            return false;
        }

        if(bookDTO.ISBN.isEmpty()){
            showErrorMessage("O código ISBN é obrigatório");
            return false;
        }
        return true;
    }


    @Override
    public void addBook(BookDTO bookDTO) {
        boolean isValid = this.isBookValid(bookDTO);

        if(!isValid) return;

        bookDatabase.insertBook(bookDTO);
    }

    @Override
    public void updateBook(int bookId, BookDTO bookDTO) {
        boolean isValid = this.isBookValid(bookDTO);

        if(!isValid) return;

        bookDatabase.updateBook(bookId, bookDTO);
    }

    @Override
    public void removeBook(int bookId) {
        bookDatabase.removeBook(bookId);
    }

    @Override
    public void returnBook(int bookId) {
        bookDatabase.returnBook(bookId);
    }

    @Override
    public void rent(int bookId, int userId, int duration) {
        bookDatabase.rent(bookId, userId, duration);
    }

    @Override
    public List<Book> getBooks(String searchTerm) {
        return bookDatabase.getBooks(searchTerm);
    }

    @Override
    public List<Book> getBooksFromUser(int userId, String searchTerm) {
        return bookDatabase.getBooksFromUser(userId, searchTerm);
    }

    private void showErrorMessage(String msg) {
        if(bookView != null) {
            bookView.showErrorMessage(msg);
        }
    }
}
