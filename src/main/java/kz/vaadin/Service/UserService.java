package kz.vaadin.Service;

import kz.vaadin.Model.User;
import kz.vaadin.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UsersRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(long id){
        return userRepository.findById(id);
    }

    public void add(User user) {
        userRepository.save(user);
    }
}
