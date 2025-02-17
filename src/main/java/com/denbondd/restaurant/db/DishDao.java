package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface DishDao {

    Dish getDishById(long id) throws DbException;

   // List<Dish> getAllDishes() throws DbException;

    List<Dish> getSortedDishesFromCategoryOnPage(int categoryId, String sortBy, int dishesInPage, int pageNum) throws DbException;


    List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException;

    int getDishesNumber() throws DbException;

    int getDishesNumberInCategory(int categoryId) throws DbException;

   // Map<Integer, Pair<String, Integer>> getDishesOrderCount() throws DbException;
}
