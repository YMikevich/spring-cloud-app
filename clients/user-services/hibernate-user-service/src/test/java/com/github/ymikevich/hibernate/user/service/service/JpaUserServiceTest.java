package com.github.ymikevich.hibernate.user.service.service;

import com.github.ymikevich.hibernate.user.service.assist.EntityManagerProvider;
import com.github.ymikevich.hibernate.user.service.model.Account;
import com.github.ymikevich.hibernate.user.service.model.Country;
import com.github.ymikevich.hibernate.user.service.model.Image;
import com.github.ymikevich.hibernate.user.service.model.Passport;
import com.github.ymikevich.hibernate.user.service.model.Role;
import com.github.ymikevich.hibernate.user.service.model.User;
import com.github.ymikevich.hibernate.user.service.model.Visa;
import com.github.ymikevich.user.service.common.model.Gender;
import com.github.ymikevich.user.service.common.model.RoleName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class JpaUserServiceTest {

    private Long user1Id;
    private Long user2Id;
    private Long account1Id;
    private Long account2Id;
    private Long account3Id;
    private Long passportId;
    private Long visa2Id;
    private Long countryId;
    private Long savedAccountId;
    private EntityManager em = EntityManagerProvider.getEntityManager();
    private JpaUserService service = new JpaUserService();

    @Before
    public void setUpTestDb() {
        var em = EntityManagerProvider.getEntityManager();

        em.getTransaction().begin();
        var countryUsa = new Country();
        countryUsa.setName("USA");
        countryUsa.setIsoCode("ISOCODE");
        em.persist(countryUsa);
        countryId = countryUsa.getId();

        var countryBelarus = new Country();
        countryBelarus.setName("Belarus");
        countryBelarus.setIsoCode("ISOCODE2");
        em.persist(countryBelarus);

        var readerRole = new Role();
        readerRole.setName(RoleName.READER);
        em.persist(readerRole);

        var user1 = new User();
        user1.setRole(readerRole);
        user1.setCountry(countryUsa);
        user1.setCreatedAt(LocalDateTime.now());
        user1.setModifiedAt(LocalDateTime.now());
        user1.setName("Sanya");
        user1.setGender(Gender.MAN);
        em.persist(user1);
        user1Id = user1.getId();

        var user2 = new User();
        user2.setRole(readerRole);
        user2.setCountry(countryBelarus);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setModifiedAt(LocalDateTime.now());
        user2.setName("Dima");
        user2.setGender(Gender.MAN);
        em.persist(user2);
        user2Id = user2.getId();

        user1.setPartner(user2);
        user2.setPartner(user1);
        em.merge(user1);
        em.merge(user2);

        var account1 = new Account();
        account1.setNickname("user1");
        account1.setEmail("email@mail");
        em.persist(account1);
        account1Id = account1.getId();

        var account2 = new Account();
        account2.setNickname("user3");
        account2.setEmail("e@mail");
        em.persist(account2);
        account2Id = account2.getId();

        var account3 = new Account();
        account3.setNickname("user2");
        account3.setEmail("email@mail.ru");
        em.persist(account3);
        account3Id = account3.getId();

        user1.getAccounts().add(account1);
        user1.getAccounts().add(account3);
        em.merge(user1);

        var image = new Image();
        image.setFileType("pdf");
        em.persist(image);

        var passport = new Passport();
        passport.setNumber("KH123456");
        passport.setUser(user1);
        em.persist(passport);
        passportId = passport.getId();

        user1.getPassport().add(passport);
        em.merge(user1);

        var visa1 = new Visa();
        visa1.setCountry(countryUsa);
        visa1.setPassport(passport);
        em.persist(visa1);

        var visa2 = new Visa();
        visa2.setCountry(countryBelarus);
        visa2.setPassport(passport);
        em.persist(visa2);
        visa2Id = visa2.getId();
        em.getTransaction().commit();
    }

    @After
    public void rollback() {
        em = EntityManagerProvider.getEntityManager();
        var deleteVisas = em.createQuery("DELETE FROM Visa");
        var deletePassports = em.createQuery("DELETE FROM Passport ");
        var deleteImages = em.createQuery("DELETE FROM Image ");
        var deleteRoles = em.createQuery("DELETE FROM Role ");
        var deleteCountries = em.createQuery("DELETE FROM Country ");

        em.getTransaction().begin();
        var user1 = em.find(User.class, user1Id);
        var user2 = em.find(User.class, user2Id);
        user1.setPartner(null);
        user2.setPartner(null);
        em.merge(user1);
        em.merge(user2);

        deleteVisas.executeUpdate();
        deletePassports.executeUpdate();
        deleteImages.executeUpdate();

        ofNullable(em.find(Account.class, account1Id)).ifPresent(em::remove);
        ofNullable(em.find(Account.class, account2Id)).ifPresent(em::remove);
        ofNullable(em.find(Account.class, account3Id)).ifPresent(em::remove);
        ofNullable(savedAccountId).ifPresent(savedAccountId -> em.remove(em.find(Account.class, savedAccountId)));

        em.remove(user1);
        em.remove(user2);
        em.flush();

        deleteRoles.executeUpdate();
        deleteCountries.executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void findAllAccountsByUserId() {
        //when
        var accounts = service.findAllAccountsByUserId(user1Id);

        //then
        assertEquals(accounts.size(), 2);
    }

    @Test
    public void findAllIllegalsFromCountry() {
        //given
        var countryName = "USA";

        //when
        var illegals = service.findAllIllegalsFromCountry(countryName);

        //then
        for (com.github.ymikevich.user.service.common.model.User user : illegals) {
            assertNull(user.getPassport());
            assertNotEquals(user.getRole().getName().name(), "LOH");
        }
    }

    @Test
    public void linkUserWithTwitterAccount() {
        //when
        service.linkUserWithTwitterAccount(user1Id, account2Id);

        //then
        var accounts = service.findAllAccountsByUserId(user1Id);
        assertEquals(accounts.size(), 3);
    }

    @Test
    public void unlinkUserFromTwitterAccount() {
        //when
        service.unlinkUserFromTwitterAccount(user1Id, account3Id);
        var accounts = service.findAllAccountsByUserId(user1Id);

        //then
        assertEquals(accounts.size(), 1);
    }

    @Test
    public void saveAccount() {
        //given
        var account = new com.github.ymikevich.user.service.common.model.Account();
        account.setNickname("Ship");

        //when
        var savedAccount = service.saveAccount(account);
        savedAccountId = savedAccount.getId();

        //then
        assertNotNull(savedAccount);
        assertEquals(savedAccount.getNickname(), account.getNickname());
    }

    @Test
    public void updateAccountById() {
        //given
        var account = new com.github.ymikevich.user.service.common.model.Account();
        account.setNickname("Bred");
        account.setEmail("hello@gmail.com");
        //todo delete user field in account common service

        //when
        var savedAccount = service.updateAccountById(account1Id, account);

        //then
        assertEquals(savedAccount.getNickname(), account.getNickname());
        assertEquals(savedAccount.getEmail(), account.getEmail());
    }

    @Test
    public void deleteAccountById() {
        //when
        service.deleteAccountById(account1Id);
        var em = EntityManagerProvider.getEntityManager();
        var deletedAccount = em.find(Account.class, account1Id);

        //then
        assertNull(deletedAccount);
    }

    @Test
    public void findUsersByRoleAndCountryInPassport() {
        //given
        var role = new com.github.ymikevich.user.service.common.model.Role();
        role.setName(RoleName.READER);

        //when
        var users = service.findUsersByRoleAndCountryInPassport(role, countryId);

        //then
        assertEquals(users.size(), 1);
    }

    @Test
    public void deleteVisaFromUserPassportWhichLivesInAnotherCountry() {
        //when
        service.deleteVisaFromUserPassportWhichLivesInAnotherCountry(user1Id, visa2Id);
        var em = EntityManagerProvider.getEntityManager();
        em.getTransaction().begin();
        var passport = em.find(Passport.class, passportId);
        var userVisas = passport.getVisaList();
        em.getTransaction().commit();

        //then
        assertEquals(1, userVisas.size());
        assertNotEquals(userVisas.get(0).getId(), visa2Id);
    }
}