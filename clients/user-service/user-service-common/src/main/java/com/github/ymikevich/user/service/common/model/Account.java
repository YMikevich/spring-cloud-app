package com.github.ymikevich.user.service.common.model;

public class Account {

    private Long id;
    private String nickname;
    private String email;
    private User user;

    public Account() {
    }

    public Account(Long id, String nickname, String email, User user) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
