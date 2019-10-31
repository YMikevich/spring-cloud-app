package com.github.ymikevich.hibernate.user.service.service;

import com.github.ymikevich.hibernate.user.service.assist.EntityManagerProvider;
import com.github.ymikevich.hibernate.user.service.converter.EntityConverter;
import com.github.ymikevich.hibernate.user.service.model.Account;
import com.github.ymikevich.hibernate.user.service.model.Country;
import com.github.ymikevich.hibernate.user.service.model.Passport;
import com.github.ymikevich.hibernate.user.service.model.Role;
import com.github.ymikevich.hibernate.user.service.model.User;
import com.github.ymikevich.hibernate.user.service.model.Visa;
import com.github.ymikevich.user.service.common.model.RoleName;
import com.github.ymikevich.user.service.common.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;

public class JpaUserService implements UserService {

    private final EntityConverter entityConverter = new EntityConverter();
    private final EntityManager entityManager = EntityManagerProvider.getEntityManager();

    @Override
    public List<com.github.ymikevich.user.service.common.model.Account> findAllAccountsByUserId(final Long id) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Account.class);
        var userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("id"), id));
        Join<User, Account> accounts = userRoot.join("accounts");
        var query = entityManager.createQuery(criteriaQuery.select(accounts));
        return entityConverter.convertList(query.getResultList(), com.github.ymikevich.user.service.common.model.Account.class);
    }

    @Override
    public List<com.github.ymikevich.user.service.common.model.User> findAllIllegalsFromCountry(final String countryName) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);

        var userRoot = criteriaQuery.from(User.class);
        Join<User, Passport> passportJoin = userRoot.join("passport", JoinType.LEFT);
        Join<User, Country> countryJoin = userRoot.join("country", JoinType.LEFT);
        Join<User, User> partnerJoin = userRoot.join("partner", JoinType.LEFT);
        Join<User, Role> roleJoin = userRoot.join("role", JoinType.LEFT);
        Join<Passport, Visa> visaJoin = passportJoin.join("visaList", JoinType.LEFT);

        var passportIsNull = criteriaBuilder.isNull(passportJoin.get("id"));
        var visaIsNull = criteriaBuilder.isNull(visaJoin.get("id"));
        var userCountryNotEqualsVisaCountry = criteriaBuilder.notEqual(visaJoin.get("country"), userRoot.get("country"));
        var gendersIsEqual = criteriaBuilder.equal(partnerJoin.get("gender"), userRoot.get("gender"));
        var roleNotLoh = criteriaBuilder.notEqual(roleJoin.get("name"), RoleName.LOH);
        var countryToSearch = criteriaBuilder.equal(countryJoin.get("name"), countryName);

        var finalPredicate = criteriaBuilder.and(criteriaBuilder.or(passportIsNull,
                criteriaBuilder.and(visaIsNull, userCountryNotEqualsVisaCountry)),
                criteriaBuilder.and(gendersIsEqual, roleNotLoh, countryToSearch));

        var query = entityManager.createQuery(criteriaQuery.select(userRoot).where(finalPredicate));
        return entityConverter.convertList(query.getResultList(),
                com.github.ymikevich.user.service.common.model.User.class);
    }

    @Override
    public List<com.github.ymikevich.user.service.common.model.User> findUsersByRoleAndCountryInPassport(
            final com.github.ymikevich.user.service.common.model.Role role, final Long countryId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var userRoot = criteriaQuery.from(User.class);

        Join<User, Country> countryJoin = userRoot.join("country");
        Join<User, Role> roleJoin = userRoot.join("role");
        var roleEquals = criteriaBuilder.equal(roleJoin.get("name"), role.getName());
        var countryEquals = criteriaBuilder.equal(countryJoin.get("id"), countryId.intValue());
        var finalPredicate = criteriaBuilder.and(roleEquals, countryEquals);
        criteriaQuery.select(userRoot).where(finalPredicate);

        var query = entityManager.createQuery(criteriaQuery);
        return entityConverter.convertList(query.getResultList(),
                com.github.ymikevich.user.service.common.model.User.class);
    }

    @Override
    public void linkUserWithTwitterAccount(final Long userId, final Long accountId) {
        entityManager.getTransaction().begin();
        var user = entityManager.find(User.class, userId);
        var account = entityManager.find(Account.class, accountId);

        user.getAccounts().add(account);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void unlinkUserFromTwitterAccount(final Long userId, final Long accountId) {
        entityManager.getTransaction().begin();
        var user = entityManager.find(User.class, userId);
        var account = entityManager.find(Account.class, accountId);

        user.getAccounts().remove(account);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public com.github.ymikevich.user.service.common.model.Account saveAccount(final com.github.ymikevich.user.service.common.model.Account account) {
        entityManager.getTransaction().begin();
        var accountToPersist = entityConverter.convert(account, Account.class);
        entityManager.persist(accountToPersist);
        entityManager.getTransaction().commit();
        return entityConverter.convert(accountToPersist, com.github.ymikevich.user.service.common.model.Account.class);
    }

    @Override
    public com.github.ymikevich.user.service.common.model.Account updateAccountById(final Long id, final com.github.ymikevich.user.service.common.model.Account account) {
        entityManager.getTransaction().begin();
        var accountToUpdate = entityManager.find(Account.class, id);
        accountToUpdate.setEmail(account.getEmail());
        accountToUpdate.setNickname(account.getNickname());
        entityManager.getTransaction().commit();
        return entityConverter.convert(accountToUpdate, com.github.ymikevich.user.service.common.model.Account.class);
    }

    @Override
    public void deleteAccountById(final Long id) {
        entityManager.getTransaction().begin();
        var accountToDelete = entityManager.find(Account.class, id);
        entityManager.remove(accountToDelete);
        entityManager.getTransaction().commit();
    }


    @Override
    public void deleteVisaFromUserPassportWhichLivesInAnotherCountry(final Long userId, final Long visaId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaDelete = criteriaBuilder.createCriteriaDelete(Visa.class);
        var visaRoot = criteriaDelete.from(Visa.class);

        entityManager.getTransaction().begin();
        var subQuery = criteriaDelete.subquery(Visa.class);
        var visaSubRoot = subQuery.from(Visa.class);
        subQuery.select(visaSubRoot);
        Join<Visa, Passport> passportJoin = visaSubRoot.join("passport");
        Join<Passport, User> userJoin = passportJoin.join("user");
        var userCountryNotEqualsVisaCountry = criteriaBuilder.notEqual(userJoin.get("country"), visaSubRoot.get("country"));
        var visaIdEquals = criteriaBuilder.equal(visaSubRoot.get("id"), visaId);
        var userIdEquals = criteriaBuilder.equal(userJoin.get("id"), userId);
        var finalPredicate = criteriaBuilder.and(userCountryNotEqualsVisaCountry, visaIdEquals, userIdEquals);
        subQuery.where(finalPredicate);

        entityManager.createQuery(criteriaDelete.where(visaRoot.in(subQuery))).executeUpdate();
        entityManager.getTransaction().commit();
    }
}
