package kz.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.model.User;
import kz.vaadin.ui.MainUI;

@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "/admin";

    public AdminView() {
        Label greetingLabel = new Label("Welcome to admin page!");
        Button logout = new Button("Logout");

        addComponents(greetingLabel, logout);
        greetingLabel.addStyleName(ValoTheme.LABEL_H1);

        setComponentAlignment(logout, Alignment.TOP_RIGHT);

        logout.addClickListener(click -> {
            getUI().getNavigator().navigateTo(MainUI.MAINVIEW);
            MainUI.getCurrent().getSession().close();
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(getUI().getSession().getAttribute("user") == null)
            getUI().getNavigator().navigateTo(MainUI.NOTLOGGEDINVEW);
        User user = (User) getUI().getSession().getAttribute("user");
        //Set<Roles> sessionRoles = (Set<Roles>) getUI().getSession().getAttribute("roles");

    }
}
