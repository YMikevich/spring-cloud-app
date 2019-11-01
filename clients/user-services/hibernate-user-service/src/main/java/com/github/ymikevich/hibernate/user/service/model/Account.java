package com.github.ymikevich.hibernate.user.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_account",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }
}
