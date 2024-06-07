package features.user.presentation;

import features.user.datasource.UserDatabase;
import features.user.dto.UserDTO;
import features.user.model.User;
import infrastructure.DatabaseManager;
import org.hibernate.query.Query;

import java.util.List;

public class UserController implements  UserControllerInterface {

    private UserDatabase userDatabase;

    public UserController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }


    @Override
    public void addUser(UserDTO userDTO) {
        userDatabase.addUser(userDTO);
    }

    @Override
    public void updateUser(int userId, UserDTO userDTO) {
    userDatabase.updateUser(userId, userDTO);
    }

    @Override
    public void removeUser(int userId) {
        userDatabase.removeUser(userId);
    }

    @Override
    public boolean validateEmailUniqueness(String email) {
        return userDatabase.validateEmailUniqueness(email);
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return DatabaseManager.getDatabaseSessionFactory().fromTransaction(session -> {
                Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
                query.setParameter("email", email);

                return query.getSingleResult();
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<User> getUsers() {
        return userDatabase.getUsers();
    }
}
