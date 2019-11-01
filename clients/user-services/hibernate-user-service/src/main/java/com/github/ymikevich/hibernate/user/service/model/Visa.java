package com.github.ymikevich.hibernate.user.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(final Passport passport) {
        this.passport = passport;
    }
}
