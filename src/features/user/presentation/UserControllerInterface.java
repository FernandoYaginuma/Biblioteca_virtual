package features.user.presentation;

import features.user.dto.UserDTO;
import features.user.model.User;

import java.util.List;

public interface UserControllerInterface {
    void addUser(UserDTO userDTO);
    void updateUser(int bookId, UserDTO userDTO);
    List<User> getUsers();
}
