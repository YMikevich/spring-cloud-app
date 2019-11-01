package com.github.ymikevich.user.service.common.model;

public class Role {

    private Long id;
    private RoleName name;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(final RoleName name) {
        this.name = name;
    }
}
