package features.user.datasource;

import features.book.dto.BookDTO;
import features.book.model.Book;
import features.user.dto.UserDTO;
import features.user.model.User;

import java.util.List;

public interface UserDatabase {
    void addUser(UserDTO userDTO);
    List<User> getUsers();
}
