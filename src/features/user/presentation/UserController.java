package features.user.presentation;

import features.user.datasource.UserDatabase;
import features.user.dto.UserDTO;
import features.user.model.User;

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
    public List<User> getUsers() {
        return userDatabase.getUsers();
    }
}
