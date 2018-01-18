package kz.vaadin.service;

import kz.vaadin.model.User;

public interface SecurityService {
    String findLoggedInUsername();
    void autologin(String username, String password);
    boolean authenticate(String username, String password, User user);
}
