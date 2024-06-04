package features.user.dto;

public class UserDTO {
    public String name;
    public String email;
    public String password;
    public Boolean admin;

    public UserDTO(String name, String email, String password, Boolean admin){
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
}
