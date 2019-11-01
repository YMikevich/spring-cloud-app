package com.github.ymikevich.spring.data.jpa.user.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String number;

    @OneToMany(mappedBy = "passport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visa> visaList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
