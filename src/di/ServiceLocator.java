package di;

import features.auth.presentation.LoginInterface;
import features.auth.presentation.LoginScreen;
import features.book.datasource.BookDAO;
import features.book.datasource.BookDatabase;
import features.book.datasource.BookSubscriber;
import features.book.presentation.BookControllerInterface;
import features.book.presentation.BookController;
import features.book.presentation.BooksInterface;
import features.book.presentation.BooksScreen;
import features.dashboard.DashboardInterface;
import features.dashboard.DashboardScreen;
import features.user.presentation.UserInterface;
import features.user.presentation.UserScreen;

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

    public BookControllerInterface getBookController() {
        return new BookController(getBookDatabase());
    }

    public BooksInterface getBookView() {
        return new BooksScreen(getBookSubscriber(), getBookController());
    }

    public LoginInterface getLoginView(){
        return new LoginScreen();
    }


    public UserInterface getUserView(){
        return new UserScreen();
    }

    public DashboardInterface getDashboardView(){
        return new DashboardScreen();
    }
}
