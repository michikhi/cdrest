package com.actelion.cdrest.entities;

import org.springframework.format.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

/**
 * Created by mimounchikhi on 01/03/16.
 */
@Entity
public class Operation {

    @Id @GeneratedValue
    private Long id;
    @NotNull
    @Size(min=2,max=5)
    private String task;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Operation() {
        super();
    }

    public Operation(Long id, String task, Date date) {
            super();
        this.id = id;
        this.task = task;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
