package kz.vaadin.UI;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.Model.User;
import kz.vaadin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends VerticalLayout implements View {

    @Autowired
    UserService userService;

    public static final String VIEW_NAME = "/";

    User user;

    public LoginView() {

        Label label = new Label("Enter username and password below to log in:");
        label.addStyleName(ValoTheme.LABEL_H1);
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        Button login = new Button("Login");
        Button register = new Button("Register");

        addComponents(label, username, password, login, register);

        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        setComponentAlignment(login, Alignment.MIDDLE_CENTER);
        setComponentAlignment(register, Alignment.MIDDLE_CENTER);

        login.addClickListener(click -> {
            user = userService.findByUsername(username.getValue());
            if(user.authenticate(username.getValue(), password.getValue())) {
                getUI().getNavigator().navigateTo(MyVaadinUI.USERPROFILEVIEW + "/" + user.getId());
            }
            else {
                Notification.show("Incorrect credentials");
                getUI().getNavigator().navigateTo(MyVaadinUI.MAINVIEW);
            }
        });

        register.addClickListener(click -> {
            getUI().getNavigator().navigateTo(MyVaadinUI.REGISTRATIONVIEW);
        });

        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

}
