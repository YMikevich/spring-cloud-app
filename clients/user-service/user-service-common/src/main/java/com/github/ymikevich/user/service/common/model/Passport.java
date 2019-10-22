package com.github.ymikevich.user.service.common.model;

import java.util.List;

public class Passport {

    private Long id;
    private Byte[] image;
    private  Country country;
    private String number;
    private List<Visa> visaList;

    public Passport() {
    }

    public Passport(Long id, Byte[] image, Country country, String number, List<Visa> visaList) {
        this.id = id;
        this.image = image;
        this.country = country;
        this.number = number;
        this.visaList = visaList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
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
