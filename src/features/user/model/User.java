package features.user.model;

import features.book.dto.BookDTO;
import features.user.dto.UserDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    public User(UserDTO userDTO) {
        this.name = userDTO.name;
    }

}
