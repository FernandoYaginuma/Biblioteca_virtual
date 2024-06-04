package features.auth.presentation;

import di.ServiceLocator;

import javax.swing.*;

public class LoginController implements LoginControllerInterface {
    private LoginInterface loginInterface;
    private LoginScreen loginScreen;

    public LoginController(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
    }

    private void showErrorMessage(String msg) {
        if(loginInterface != null) {
            loginInterface.showErrorMessage(msg);
        }
    }

    @Override
    public Boolean login(String email, String password) {

        loginScreen.setVisible(false);

        SwingUtilities.invokeLater(() -> {
            ServiceLocator.getInstance().getDashboardView().open(true);
        });

        return true;
    }
}
