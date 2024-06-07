package features.auth.presentation;

public interface LoginControllerInterface {
    Boolean login(String email, String password, LoginScreen loginScreen);
}
