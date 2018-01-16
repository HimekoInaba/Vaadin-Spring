package kz.vaadin.Repository;

import kz.vaadin.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User findByUsername(String username);
    User findById(long id);
}
