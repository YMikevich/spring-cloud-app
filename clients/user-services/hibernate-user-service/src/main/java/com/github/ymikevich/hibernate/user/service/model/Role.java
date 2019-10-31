package com.github.ymikevich.hibernate.user.service.model;

import com.github.ymikevich.user.service.common.model.RoleName;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
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
