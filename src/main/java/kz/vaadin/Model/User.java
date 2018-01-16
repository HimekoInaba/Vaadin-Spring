package kz.vaadin.Model;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @SequenceGenerator(name="hibernate_sequence",sequenceName="MY_SEQ_GEN", initialValue=205, allocationSize=12)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="hibernate_sequence")
    private long id;

    private String username;
    private String password;
    private String email;

    @Transient
    private String confirmPassword;

    public User() {
    }

    public boolean authenticate(String username, String password) {
        if (username.equals(getUsername()) && password.equals(getPassword())) {
            return true;
        }
        return false;
    }

    public User(String username, String password, String confirmPassword, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
