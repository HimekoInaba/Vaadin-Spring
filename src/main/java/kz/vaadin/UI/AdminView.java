package kz.vaadin.UI;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {


    public static final String VIEW_NAME = "/admin";

    public AdminView(){
        Label greetingLabel = new Label("Welcome to admin page!");

        addComponent(greetingLabel);
        greetingLabel.addStyleName(ValoTheme.LABEL_H1);
    }
}
