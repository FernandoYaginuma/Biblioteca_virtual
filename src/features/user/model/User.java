package features.user.model;

import features.book.dto.BookDTO;
import features.user.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    private String email;

    private String password;

    private boolean admin;

    public User(UserDTO userDTO) {
        this.name = userDTO.name;
        this.email = userDTO.email;
        this.password = userDTO.password;
        this.admin = userDTO.admin;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isAdmin(){
        return this.admin;
    }
}
