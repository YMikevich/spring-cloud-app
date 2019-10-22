package com.github.ymikevich.service;

import com.github.ymikevich.user.service.common.model.Account;
import com.github.ymikevich.user.service.common.model.Country;
import com.github.ymikevich.user.service.common.model.Role;
import com.github.ymikevich.user.service.common.model.User;
import com.github.ymikevich.user.service.common.model.Visa;
import com.github.ymikevich.user.service.common.service.UserCommonService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserCommonService implements UserCommonService, DatabaseRequestFunctionExecutor {

    //language=SQL
    private static final String SQL_SELECT_ACCOUNTS_BY_USER_ID = "SELECT * FROM account\n" +
            "JOIN user_account ON account.id = user_account.account_id\n" +
            "JOIN app_user ON app_user.id = user_account.user_id " +
            "WHERE app_user.id=?";


    private Connection connection;

    @Override
    public List<Account> findAllAccountsByUserId(Long id) {
        DatabaseRequestFunction<Connection, List<Account>> accountDatabaseRequestFunction = connection -> {
            connection.setAutoCommit(false);

            var preparedStatement = connection.prepareStatement(SQL_SELECT_ACCOUNTS_BY_USER_ID);

            preparedStatement.setInt(1, id.intValue());
            var resultSet = preparedStatement.executeQuery();
            var userAccounts = new ArrayList<Account>();

            while (resultSet.next()) {
                var accountId = resultSet.getInt("account.id");
                var nickname = resultSet.getString("account.name");
                var email = resultSet.getString("account.email");
                var userId = resultSet.getInt("user_account.user_id");
                var account = new Account((long) accountId, nickname, email, (long) userId);
                userAccounts.add(account);
            }

            connection.commit();
            return userAccounts;
        };

        return execute(accountDatabaseRequestFunction);
    }

    @Override
    public List<User> findAllIllegalsFromCountry(Country country) {
        return null;
    }

    @Override
    public List<User> findUsersByRoleAndCountryInPassport(Role role, Country country) {
        return null;
    }

    @Override
    public boolean linkUserWIthTwitterAccount(User user, Account account) {
        return false;
    }

    @Override
    public boolean unlinkUserFromTwitterAccount(User user, Account account) {
        return false;
    }

    @Override
    public boolean createAccountWithId(Long id) {
        return false;
    }

    @Override
    public boolean updateAccountById(Long id) {
        return false;
    }

    @Override
    public boolean deleteCountById(Long id) {
        return false;
    }


    @Override
    public boolean deleteVisaFromUserPassportWhichLivesInAnotherCountry(User user, Visa visa) {
        return false;
    }
}
