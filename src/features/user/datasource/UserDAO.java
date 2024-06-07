package features.user.datasource;

import features.book.model.Book;
import features.user.dto.UserDTO;
import features.user.model.User;
import infrastructure.DatabaseManager;
import org.hibernate.query.Query;

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
            System.out.println("features.user.model.User edited successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error editing user: " + e.getMessage());
        }
    }

    @Override
    public void removeUser(int userId) {
        try {
            DatabaseManager.getDatabaseSessionFactory().inTransaction(session -> {
                User user = session.get(User.class, userId);
                if (user != null) {
                    session.delete(user);
                    System.out.println("features.user.model.User deleted successfully.");
                } else {
                    System.out.println("User with ID " + userId + " not found.");
                }
            });
            notifyDataChanged();
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public boolean validateEmailUniqueness(String email) {
        try {
            // Check if a user with the same email already exists
            List<User> users = DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                // Create a query to find users with the given email
                Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
                query.setParameter("email", email);
                return query.getResultList();
            });

            return users.isEmpty();
        } catch (Exception e) {
            // Handle other exceptions during database interaction
            throw new RuntimeException(e);
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

