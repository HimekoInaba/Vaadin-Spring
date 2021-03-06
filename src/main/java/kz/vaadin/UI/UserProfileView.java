package kz.vaadin.UI;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.Model.User;
import kz.vaadin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.ui.Label;


@SpringView(name = UserProfileView.VIEW_NAME)
public class UserProfileView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "/profile";

    @Autowired
    UserService userService;

    private User user;
    private long id;

    public UserProfileView(){
    }

    public void initializeForms(){
        user = userService.findById(id);

        Button logout = new Button("Logout");
        Button userList = new Button("List of all users");
        Button adminPanel = new Button("Admin panel");
        Label greetingField = new Label("Welcome to your profile!");
        Label userInformation = new Label("User information:");
        Label currentSessionUsername = new Label("Username " + user.getUsername());
        Label email = new Label("Email: " + user.getEmail());

        addComponents(greetingField, logout, userInformation, currentSessionUsername, email, userList, adminPanel);

        setComponentAlignment(greetingField, Alignment.TOP_LEFT);
        setComponentAlignment(userInformation, Alignment.TOP_LEFT);
        setComponentAlignment(email, Alignment.TOP_LEFT);
        setComponentAlignment(currentSessionUsername, Alignment.TOP_LEFT);
        setComponentAlignment(logout, Alignment.TOP_RIGHT);
        setComponentAlignment(userList, Alignment.BOTTOM_CENTER);
        setComponentAlignment(adminPanel, Alignment.BOTTOM_CENTER);

        greetingField.addStyleName(ValoTheme.LABEL_H1);
        userInformation.addStyleName(ValoTheme.LABEL_H2);
        email.addStyleName(ValoTheme.LABEL_H2);
        currentSessionUsername.addStyleName(ValoTheme.LABEL_H2);

        logout.addClickListener(click ->{
            getUI().getNavigator().navigateTo(MyVaadinUI.MAINVIEW);
            VaadinSession vSession = getUI().getSession();
            vSession.close();
        });

        adminPanel.addClickListener(click -> getUI().getNavigator().navigateTo(MyVaadinUI.ADMINVIEW));
        userList.addClickListener(click -> getUI().getNavigator().navigateTo(MyVaadinUI.USERLISTVIEW));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null){
            // split at "/", add each part as a label
            String[] msgs = event.getParameters().split("/");
            for (String msg : msgs) {
                id = (long) Integer.parseInt(msg);
            }
        }
        initializeForms();
    }

}
