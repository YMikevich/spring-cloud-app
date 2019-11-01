package com.github.ymikevich.spring.data.jpa.user.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "partner_id")
    private User partner;

    private String name;
    private String email;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "postal_code_id")
    private PostalCode postalCode;

    @ManyToMany
    @JoinTable(
            name = "user_account",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")}
    )
    private List<Account> accounts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private List<Passport> passport = new ArrayList<>();
}
