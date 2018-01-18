package kz.vaadin.view;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.model.User;
import kz.vaadin.service.SecurityService;
import kz.vaadin.service.UserService;
import kz.vaadin.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;


@SpringView(name = RegistrationView.VIEW_NAME)
public class RegistrationView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "/register";

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    User user;

    public RegistrationView() {

        Label label = new Label("Enter your information below to register:");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm password");
        TextField email = new TextField("E-mail");
        Button register = new Button("Register");

        addComponents(label, username, password, confirmPassword, email, register);

        label.addStyleName(ValoTheme.LABEL_H1);

        new Binder<User>().forField(username)
                .withNullRepresentation("")
                .withValidator(s -> s.length() > 8, "Username length must be between at least 8 characters!")
                .withValidator(s -> s.length() < 32,"Username length must not exceed 32 characters!")
                .bind(User::getUsername, User::setUsername);

        new Binder<User>().forField(password)
                .withNullRepresentation("")
                .withValidator(s -> s.length() > 8, "Password length must be between at least 8 characters!")
                .withValidator(s -> s.length() < 32,"Password length must not exceed 32 characters!")
                .bind(User::getPassword, User::setPassword);

        new Binder<User>().forField(confirmPassword)
                .withNullRepresentation("")
                .withValidator(s -> s.length() > 8, "Confirmation password length must be between at least 8 characters!")
                .withValidator(s -> s.length() < 32,"Confirmation password length must not exceed 32 characters!")
                .bind(User::getConfirmPassword, User::setConfirmPassword);

        new Binder<User>().forField(email)
                .withNullRepresentation("")
                .bind(User::getEmail, User::setEmail);

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
                securityService.autologin(user.getUsername(), password.getValue());
                getUI().getNavigator().navigateTo(MainUI.USERPROFILEVIEW + "/" + user.getId());
            } else {
                Notification.show("Passwords don't match!");
                getUI().getNavigator().navigateTo(MainUI.REGISTRATIONVIEW);
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
