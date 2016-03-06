package com.actelion.cdrest.entities;

import javax.persistence.*;
import java.io.*;

/**
 * Created by mimounchikhi on 05/03/16.
 */
@Entity
public class Role implements Serializable{

    @Id
    private String role;
    private String description;

    public Role(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public Role() {
        super();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
