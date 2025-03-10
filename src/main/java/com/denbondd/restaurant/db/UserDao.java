package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface UserDao {

    User logIn(String login, char[] password) throws DbException;

    User signUp(String login, char[] password) throws DbException;

    boolean isLoginUnique(String login) throws DbException;

    User getUserByLogin(String login) throws DbException;

    void changePassword(long userId, char[] newPass) throws DbException;

    List<User> getAllUsers() throws DbException;

    void changeRole(long userId, int roleId) throws DbException;

    void deleteUser(long userId) throws DbException;
}
