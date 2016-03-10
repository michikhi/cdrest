package com.actelion.cdrest.entities;

import javax.persistence.*;
import java.io.*;
import java.util.*;

/**
 * Created by mimounchikhi on 05/03/16.
 */

@Entity
@Table(name = "users")
public class User implements Serializable{

    @Id
    private String username;
    private String password;
    private boolean activated;

    public User(String username, String password, boolean activated) {
        this.username = username;
        this.password = password;
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @ManyToMany
    @JoinTable(name = "USERS_ROLES")
    private Collection<Role> roles;

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public User() {
        super();
    }


}
