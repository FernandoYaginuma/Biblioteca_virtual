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
                var user = new User(userDTO);
                session.persist(user);
            });
            notifyDataChanged();
            System.out.println("User added");
        } catch (Exception e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(int userId, UserDTO userDTO) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
//                var user = session.get(Book.class, userId);
//                user.setName(bookDTO.name);
//                user.setAuthor(bookDTO.author);
//                user.setCategory(bookDTO.category);
//                user.setISBN(bookDTO.ISBN);
//                session.persist(book);
            });
            System.out.println("features.book.model.Book edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        try {
            result = DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                return session.createSelectionQuery("from User", User.class).getResultList();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

