package features.book.presentation;

import features.book.datasource.BookDatabase;
import features.book.dto.BookDTO;
import features.book.model.Book;

import java.util.List;

public class BookControllerImpl implements BookController {
    private BooksInterface bookView;
    private final BookDatabase bookDatabase;

    public BookControllerImpl(BookDatabase bookDatabase) {
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
    public void setRented(int bookId, boolean rented) {

        bookDatabase.updateBookRented(bookId, rented);
    }

    @Override
    public List<Book> getBooks() {
        return bookDatabase.getBooks();
    }

    private void showErrorMessage(String msg) {
        if(bookView != null) {
            bookView.showErrorMessage(msg);
        }
    }
}
