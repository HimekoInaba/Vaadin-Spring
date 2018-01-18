package kz.vaadin.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.external.org.slf4j.LoggerFactory;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.model.User;
import kz.vaadin.service.SecurityService;
import kz.vaadin.service.UserService;
import kz.vaadin.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.security.managed.DefaultVaadinManagedSecurity;

import javax.annotation.PostConstruct;


@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends VerticalLayout implements View {

    @Autowired
    VaadinSecurity vaadinSecurity;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    public static final String VIEW_NAME = "/";

    private Label label;
    private TextField username;
    private PasswordField password;
    private Button login;
    private Button register;
    private Label loginFailedLabel;

    User user;

    public LoginView() {
    }

    @PostConstruct
    private void initLayout(){
        label = new Label("Enter username and password below to log in:");
        username = new TextField("Username");
        password = new PasswordField("Password");
        login = new Button("Login");
        register = new Button("Register");
        loginFailedLabel = new Label();

        addComponents(label, username, password, login, register, loginFailedLabel);

        label.addStyleName(ValoTheme.LABEL_H1);
        loginFailedLabel.setSizeUndefined();
        loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        loginFailedLabel.setVisible(false);

        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        setComponentAlignment(username, Alignment.MIDDLE_CENTER);
        setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        setComponentAlignment(login, Alignment.MIDDLE_CENTER);
        setComponentAlignment(register, Alignment.MIDDLE_CENTER);
        setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);

        login.addClickListener(click -> {
            System.out.println("AUTOWIRED EEE " + vaadinSecurity);
            System.out.println(((DefaultVaadinManagedSecurity)vaadinSecurity).hasAuthenticationManager());
            login(username.getValue(), password.getValue());

            getUI().getNavigator().navigateTo(MainUI.USERLISTVIEW);
            /*user = userService.findByUsername(username.getValue());
            if(securityService.authenticate(username.getValue(), password.getValue(), user)) {
                //getUI().getSession().setAttribute("user", user);
                //getUI().getNavigator().navigateTo(MainUI.USERPROFILEVIEW + "/" + user.getId());
                login(username.getValue(), password.getValue());
                getUI().getNavigator().navigateTo(MainUI.USERPROFILEVIEW + "/" + user.getId());
            }
            else {
                Notification.show("Incorrect credentials");
                getUI().getNavigator().navigateTo(MainUI.MAINVIEW);
            }*/
        });

        register.addClickListener(click -> getUI().getNavigator().navigateTo(MainUI.REGISTRATIONVIEW));

        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void login(String username, String password) {
        try {
            System.out.println(vaadinSecurity);
            final Authentication authentication = vaadinSecurity.login(username, password);
        } catch (AuthenticationException ex){
            loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
            loginFailedLabel.setVisible(true);
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
        } finally {
            login.setEnabled(true);
        }
    }
}
