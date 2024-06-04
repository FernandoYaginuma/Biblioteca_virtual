package features.user.presentation;

import di.ServiceLocator;
import features.book.model.Book;
import features.user.datasource.UserDatabase;
import features.user.dto.UserDTO;
import features.user.model.User;

import javax.swing.*;
import java.util.List;

public class UserController implements  UserControllerInterface {

    private UserDatabase userDatabase;

    public UserController(UserDatabase userDatabase){
        this.userDatabase = userDatabase;
    }


    @Override
    public void addUser(UserDTO userDTO) {

    }

    @Override
    public void updateUser(int bookId, UserDTO userDTO) {

    }

    @Override
    public List<User> getUsers() {
        return userDatabase.getUsers();
    }
}
