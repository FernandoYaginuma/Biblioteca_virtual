package features.user.presentation;

import di.ServiceLocator;

import javax.swing.*;

public class UserController implements  UserControllerInterface{
    private UserScreen userScreen;

    UserController(UserScreen userScreen){
        this.userScreen = userScreen;
    }


    @Override
    public void addUser(String name) {

    }

    @Override
    public void updateUser(int bookId, String name) {

    }
}
