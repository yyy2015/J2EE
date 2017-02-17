package com.mark.java.entity;

import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by yyy on 2017/2/17.
 */
@Entity
@javax.persistence.Table
public class User implements Serializable{
    @Id
    @GeneratedValue
    private  int id;

    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
