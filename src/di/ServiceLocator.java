package di;

import features.auth.presentation.LoginController;
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
import features.user.datasource.UserDAO;
import features.user.datasource.UserDatabase;
import features.user.datasource.UserSubscriber;
import features.user.presentation.UserControllerInterface;
import features.user.presentation.UserInterface;
import features.user.presentation.UserController;
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
    private UserDAO userDAO;

    private BookDAO getBookDao() {
        if(bookDAO == null) {
            bookDAO = new BookDAO();
        }

        return bookDAO;
    }

    private UserDAO getUserDao() {
        if(userDAO == null) {
            userDAO = new UserDAO();
        }

        return userDAO;
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

    public LoginController getloginController(){
        return new LoginController(getUserController());
    }


    public LoginInterface getLoginView(){
        return new LoginScreen(getloginController());
    }

    public UserDatabase getUserDatabase() {
        return getUserDao();
    }

    public UserSubscriber getUserSubscriber() {
        return getUserDao();
    }

    public UserControllerInterface getUserController() {
        return new UserController(getUserDatabase());
    }
    public UserInterface getUserView(){
        return new UserScreen(getUserSubscriber(), getUserController());
    }

    public DashboardInterface getDashboardView(){
        return new DashboardScreen();
    }
}
