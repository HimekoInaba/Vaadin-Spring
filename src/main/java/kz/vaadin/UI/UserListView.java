package kz.vaadin.UI;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import kz.vaadin.Model.User;
import kz.vaadin.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = UserListView.VIEW_NAME)
public class UserListView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "/userlist";

    @Autowired
    UsersRepository usersRepository;

    User user;

    public UserListView(){



    }

    @PostConstruct
    public void initializeForms(){

        Label label = new Label("List of all users");
        List<User> users;
        users = usersRepository.findAll();
        Grid<User> grid = new Grid<>();

        grid.setItems(users);
        grid.addColumn(User::getId).setCaption("Id");
        grid.addColumn(User::getUsername).setCaption("Username");
        grid.addColumn(User::getEmail).setCaption("Email");

        addComponents(label, grid);
        setComponentAlignment(label,Alignment.MIDDLE_CENTER);
        setComponentAlignment(grid, Alignment.MIDDLE_CENTER);

        label.addStyleName(ValoTheme.LABEL_H1);
    }
}
