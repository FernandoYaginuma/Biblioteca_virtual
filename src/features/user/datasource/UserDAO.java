package features.user.datasource;

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
    public void subscribe(UserListener userListener) {
        listeners.add(userListener);
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
                var user = session.get(User.class, userId);
                user.setName(userDTO.name);
                user.setEmail(userDTO.email);
                user.setPassword(userDTO.password);
                user.setIsAdmin(userDTO.admin);
                session.persist(user);
            });
            System.out.println("features.book.model.User edited successfully.");
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

