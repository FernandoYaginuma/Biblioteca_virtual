package di;

import auth.presentation.LoginController;
import auth.presentation.LoginInterface;
import auth.presentation.LoginScreen;
import features.book.datasource.BookDAO;
import features.book.datasource.BookDatabase;
import features.book.datasource.BookSubscriber;
import features.book.presentation.BookController;
import features.book.presentation.BookControllerImpl;
import features.book.presentation.BooksInterface;
import features.book.presentation.BooksScreen;

public class ServiceLocator {

    // Instancia para o singleton
    private static ServiceLocator instance;

    public static ServiceLocator getInstance() {
        if(instance == null) {
            instance = new ServiceLocator();
        }

        return instance;
    }

    private BookDAO bookDAO;

    private BookDAO getBookDao() {
        if(bookDAO == null) {
            bookDAO = new BookDAO();
        }

        return bookDAO;
    }

    public BookDatabase getBookDatabase() {
        return getBookDao();
    }

    public BookSubscriber getBookSubscriber() {
        return getBookDao();
    }

    public BookController getBookController() {
        return new BookControllerImpl(getBookDatabase());
    }

    public BooksInterface getBookView() {
        return new BooksScreen(getBookSubscriber(), getBookController());
    }

    public LoginInterface getLoginView(){
        return new LoginScreen();
    }
}
