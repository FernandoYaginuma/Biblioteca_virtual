package features.auth.presentation;

import di.ServiceLocator;
import features.user.model.User;
import features.user.presentation.UserControllerInterface;

import javax.swing.*;

public class LoginController implements LoginControllerInterface {
    private UserControllerInterface userControllerInterface;

    public LoginController(UserControllerInterface userControllerInterface){
        this.userControllerInterface = userControllerInterface;
    }


    @Override
    public Boolean login(String email, String password, LoginScreen loginScreen) {

        try {
            User user = userControllerInterface.getUserByEmail(email);

            if (user == null) {
                return false;
            }

            if (!user.getPassword().equals(password)) {
                return false;
            }

            loginScreen.setVisible(false);
            SwingUtilities.invokeLater(() -> ServiceLocator.getInstance().getDashboardView().open(true));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
