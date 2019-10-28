package com.github.ymikevich.user.service.common.model;

import java.util.List;

public class Passport {

    private Long id;
    private Image image;
    private Country country;
    private String number;
    private List<Visa> visaList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Visa> getVisaList() {
        return visaList;
    }

    public void setVisaList(List<Visa> visaList) {
        this.visaList = visaList;
    }
}
