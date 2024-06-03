package auth.presentation;

public class LoginController implements LoginControllerInterface {
    private LoginInterface loginInterface;

    private void showErrorMessage(String msg) {
        if(loginInterface != null) {
            loginInterface.showErrorMessage(msg);
        }
    }

    @Override
    public void login(String email, String password) {

    }
}
