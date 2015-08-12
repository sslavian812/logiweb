package ru.tsystems.shalamov.entities;

import ru.tsystems.shalamov.entities.statuses.UserAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by viacheslav on 24.07.2015.
 */
@Entity
@Table(name = "users", schema = "", catalog = "logiweb")
public class User implements Serializable  {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAuthority authority;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(UserAuthority authority) {
        this.authority = authority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}