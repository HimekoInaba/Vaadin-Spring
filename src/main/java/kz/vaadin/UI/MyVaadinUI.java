package kz.vaadin.UI;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.Model.User;
import kz.vaadin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button.ClickEvent;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

    @Autowired
    SpringViewProvider viewProvider;

    public final static String REGISTRATIONVIEW =  RegistrationView.VIEW_NAME;
    public final static String USERPROFILEVIEW =  UserProfileView.VIEW_NAME;
    public final static String MAINVIEW =  LoginView.VIEW_NAME;
    public final static String USERLISTVIEW = UserListView.VIEW_NAME;

    @Override
    protected void init(VaadinRequest request) {

        // Create a navigator to control the views
        Navigator navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addProvider(viewProvider);
        setNavigator(navigator);

        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setWidth("80%");
        setContent(layout);

        getUI().getNavigator().navigateTo(MyVaadinUI.MAINVIEW);
    }
}
