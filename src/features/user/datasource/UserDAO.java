package features.user.datasource;

import features.book.dto.BookDTO;
import features.book.model.Book;
import features.user.dto.UserDTO;
import features.user.model.User;
import infrastructure.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserDatabase, UserSubscriber {
    private final List<UserListener> listeners;

    public UserDAO() {
        this(new ArrayList<>());
    }

    public UserDAO(List<UserListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void subscribe(UserListener bookListener) {
        listeners.add(bookListener);
    }

    private void notifyDataChanged() {
        for(UserListener listener : listeners) {
            listener.updateData();
        }
    }

    @Override
    public void addUser(UserDTO userDTO) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                var book = new User(userDTO);
                session.persist(book);
            });
            System.out.println("features.book.model.Book inserted successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        try {
            result = DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                return session.createSelectionQuery("from Users", User.class)
                        .getResultList();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

