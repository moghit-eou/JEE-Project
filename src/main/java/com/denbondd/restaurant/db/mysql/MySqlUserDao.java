package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.Connection_jdbc;
import com.denbondd.restaurant.db.UserDao;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {

    private static User mapUser(ResultSet rs) throws SQLException {

        // this is for retrieving the line from database
        // id login password date create
        int k = 0;
        User user = new User() ;
        user.setId(rs.getInt(++k)) ;
        user.setLogin(rs.getString(++k)) ;
        user.setRoleId(rs.getInt(k += 2)) ; // ( k + 2 ) for skipping the password columns
        user.setCreateDate(rs.getTimestamp(++k)) ;
        return user ;

    }

    @Override
    public boolean isLoginUnique(String login) throws DbException {
        return getUserByLogin(login) == null;
    }

    @Override
    public User getUserByLogin(String login) throws DbException {
        try (Connection connection = (new Connection_jdbc()).getConnection()){
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(SqlUtils.FIND_USER_BY_LOGIN);
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
               // System.out.println("the builder called getting user by login");
                return mapUser(rs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new DbException("Cannot getUserByLogin", ex);
        }
    }

    @Override
    public User logIn(String login, char[] password) throws DbException {
        String pass = new String(password);
        try (Connection connection = (new Connection_jdbc()).getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlUtils.LOG_IN)) {
            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
              //  System.out.println("the builder called logIn");

                return mapUser(rs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new DbException("Cannot logIn", ex);
        }
    }

    @Override
    public User signUp(String login, char[] password) throws DbException   {
        String Pass = new String(password);
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Connection_jdbc obj_connection = new Connection_jdbc();
            con = obj_connection.getConnection();

            con.setAutoCommit(false);
            ps = con.prepareStatement(SqlUtils.SIGN_UP);

            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, Pass);


            if (ps.executeUpdate() == 0) {
                throw new DbException("SignUp failed, no rows attached");
            }

            con.commit();
            return getUserByLogin(login);
        }
        catch (SQLException ex) {
             throw new DbException("Cannot logIn", ex);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DbException("Driver do not exist", ex);
        }
         finally {
            SqlUtils.close(con);
            SqlUtils.close(ps);
        }
    }

    @Override
    public void changePassword(long userId, char[] newPass) throws DbException {
        String hashPass = new String(newPass);
        try (Connection c =  (new Connection_jdbc()).getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_PASSWORD)) {
            c.setAutoCommit(false);

            int k = 0;
            ps.setString(++k, hashPass);
            ps.setLong(++k, userId);
            if (ps.executeUpdate() == 0) {
                throw new DbException("Changing password failed, no rows were changed");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot changePassword", e);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DbException("Driver do not exist", ex);
        }
    }

    @Override
    public List<User> getAllUsers() throws DbException {
        List<User> users = new ArrayList<>();
        try (Connection c =  (new Connection_jdbc()).getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
              //  System.out.println("the builder called listing all users");
                users.add(mapUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllUsers", e);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DbException("Driver do not exist", ex);
        }
    }

    @Override
    public void changeRole(long userId, int roleId) throws DbException {
        try (Connection c =  (new Connection_jdbc()).getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_ROLE);
            int k = 0;
            ps.setLong(++k, roleId);
            ps.setLong(++k, userId);
            if (ps.executeUpdate() == 0) {
                throw new DbException("Changing role failed, no rows were changed");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot changeRole", e);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DbException("Driver do not exist", ex);
        }
    }

    @Override
    public void deleteUser(long userId) throws DbException {
        try (Connection c =  (new Connection_jdbc()).getConnection()){
            c.setAutoCommit(false);
             PreparedStatement ps = c.prepareStatement(SqlUtils.DELETE_USER);
            ps.setLong(1, userId);
            if (ps.executeUpdate() == 0) {
                throw new DbException("Deleting user failed, no rows were changed");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot deleteUser", e);
        }
        catch (ClassNotFoundException ex)
        {
            throw new DbException("Driver do not exist", ex);
        }
    }
}
