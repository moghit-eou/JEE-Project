package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.CategoryDao;
import com.denbondd.restaurant.db.Connection_jdbc;
import com.denbondd.restaurant.db.entity.Category;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryDao implements CategoryDao {

    private static Category mapCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        return category;
    }

    @Override
    public List<Category> getAllCategories() throws DbException {
        List<Category> categories = new ArrayList<>();
        try (Connection c = ( new Connection_jdbc()).getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_CATEGORIES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            return categories;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getAllCategories", e);
        }
    }
}
