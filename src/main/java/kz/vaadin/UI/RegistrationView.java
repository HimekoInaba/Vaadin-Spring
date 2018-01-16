package kz.vaadin.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import kz.vaadin.Model.User;
import kz.vaadin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@SpringView(name = RegistrationView.VIEW_NAME)
public class RegistrationView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "/register";

    @Autowired
    UserService userService;

    User user;

    public RegistrationView() {

        Label label = new Label("Enter your information below to register:");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Passoword");
        PasswordField confirmPassword = new PasswordField("Confirm password");
        TextField email = new TextField("E-mail");
        Button register = new Button("Register");

        addComponents(label, username, password, confirmPassword, email, register);

        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        setComponentAlignment(confirmPassword, Alignment.MIDDLE_CENTER);
        setComponentAlignment(email, Alignment.MIDDLE_CENTER);
        setComponentAlignment(register, Alignment.MIDDLE_CENTER);

        register.addClickListener(click -> {
            if (verifyPassword(password.getValue(), confirmPassword.getValue())) {
                userService.add(new User(username.getValue(), password.getValue(), confirmPassword.getValue(), email.getValue()));
                user = userService.findByUsername(username.getValue());
                getUI().getNavigator().navigateTo(MyVaadinUI.USERPROFILEVIEW + "/" + user.getId());
            } else {
                Notification.show("Passwords don't match!");
                getUI().getNavigator().navigateTo(MyVaadinUI.REGISTRATIONVIEW);
            }
        });

        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    public boolean verifyPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        return false;
    }
}
