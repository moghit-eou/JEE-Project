package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.Connection_jdbc;
import com.denbondd.restaurant.db.DishDao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlDishDao implements DishDao {

    public static Dish mapDish(ResultSet rs) throws SQLException {
        Dish dish = new Dish();
        dish.setId(rs.getLong("id"));
        dish.setName(rs.getString("name"));
        dish.setCategoryId(rs.getLong("category_id"));
        dish.setPrice(rs.getLong("price"));
        dish.setWeight(rs.getLong("weight"));
        dish.setDescription(rs.getString("description"));
        return dish;
    }

    @Override
    public Dish getDishById(long id) throws DbException {
        try (Connection c = ( new Connection_jdbc( )).getConnection() ) {
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISH_BY_ID);
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDish(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getDishById", e);
        }
    }
//
//    @Override
//    public List<Dish> getAllDishes() throws DbException {
//        List<Dish> dishes = new ArrayList<>();
//        try (Connection c = ( new Connection_jdbc( )).getConnection()
//              )
//        {
//            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_DISHES);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                dishes.add(mapDish(rs));
//            }
//            return dishes;
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new DbException("Cannot getAllDishes", e);
//        }
//    }

    @Override
    public List<Dish> getSortedDishesFromCategoryOnPage(int categoryId, String sortBy, int dishesInPage, int pageNum) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ( new Connection_jdbc( )).getConnection()
             )
        {

            PreparedStatement ps = c.prepareStatement(
                    SqlUtils.GET_SORTED_DISHES_FROM_CATEGORY + sortBy + " LIMIT " + pageNum * dishesInPage + ", " + dishesInPage);

            ps.setLong(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getSortedDishesFromCategory" + categoryId, e);
        }
    }

    @Override
    public List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ( new Connection_jdbc( )).getConnection())
        {

            PreparedStatement ps = c.prepareStatement(
                    SqlUtils.GET_SORTED_DISHES + sortBy + " LIMIT " + pageNum * dishesInPage + ", " + dishesInPage);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getSortedDishes", e);
        }
    }

    @Override
    public int getDishesNumber() throws DbException {
        try (Connection c = ( new Connection_jdbc()).getConnection() )

        {
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_COUNT);
            ResultSet rs = ps.executeQuery();



            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getDishesNumber", e);
        }
    }


    @Override
    public int getDishesNumberInCategory(int categoryId) throws DbException {
        try (Connection c = ( new Connection_jdbc()).getConnection()
        )
        {
            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_COUNT_IN_CATEGORY);
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DbException("Cannot getDishesNumberInCategory", e);
        }
    }

//    @Override
//    public Map<Integer, Pair<String, Integer>> getDishesOrderCount() throws DbException {
//        Map<Integer, Pair<String, Integer>> answ = new HashMap<>();
//        try (Connection c = ( new Connection_jdbc()).getConnection()
//            )
//        {
//            // c.setAutoCommit(false);
//            PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISH_ORDERS_COUNT);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                int orders = rs.getInt("orders");
//                Pair<String, Integer> pair = new Pair<>(name, orders);
//                answ.put(id, pair);
//            }
//            return answ;
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new DbException("Cannot getDishesOrderCount", e);
//        }
//    }
}
