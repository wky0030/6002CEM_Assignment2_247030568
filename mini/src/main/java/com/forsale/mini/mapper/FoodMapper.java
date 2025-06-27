package com.forsale.mini.mapper;


import com.forsale.mini.bean.Food;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoodMapper {

    // Insert a new food record
    @Insert("INSERT INTO food (food_name, food_price, food_sales, food_img, window_id, dscription) " +
            "VALUES (#{foodName}, #{foodPrice}, #{foodSales}, #{foodImg}, #{windowId}, #{description})")
    void insertFood(Food food);

    // Get all food records
    @Select("SELECT * FROM food")
    List<Food> getAllFoods();

    // Get a food record by ID
    @Select("SELECT * FROM food WHERE food_id = #{foodId}")
    Food getFoodById(Integer foodId);

    // Update a food record
    @Update("UPDATE food SET food_name = #{foodName}, food_price = #{foodPrice}, food_sales = #{foodSales}, " +
            "food_img = #{foodImg}, window_id = #{windowId}, dscription = #{description} WHERE food_id = #{foodId}")
    void updateFood(Food food);

    // Delete a food record by ID
    @Delete("DELETE FROM food WHERE food_id = #{foodId}")
    void deleteFood(Integer foodId);
}
