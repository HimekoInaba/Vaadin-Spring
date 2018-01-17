package kz.vaadin.Service;


import kz.vaadin.Model.User;

public interface UserService {
    User findByUsername(String username);
    User findById(long id);
    void add(User user);
}
