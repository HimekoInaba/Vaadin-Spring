package kz.vaadin.Service;

import kz.vaadin.Model.User;

public interface SecurityService {
    String findLoggedInUsername();
    void autologin(String username, String password);
    boolean authenticate(String username, String password, User user);
}
