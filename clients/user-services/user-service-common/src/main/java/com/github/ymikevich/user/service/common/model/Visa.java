package com.github.ymikevich.user.service.common.model;

public class Visa {

    private Long id;
    private String type;
    private Country country;

    public Visa() {
    }

    public Visa(Long id, String type, Country country) {
        this.id = id;
        this.type = type;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
