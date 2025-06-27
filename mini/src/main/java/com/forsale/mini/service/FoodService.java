package com.forsale.mini.service;


import com.forsale.mini.bean.Food;
import com.forsale.mini.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodMapper foodMapper;

    public void addFood(Food food) {
        foodMapper.insertFood(food);
    }

    public List<Food> getAllFoods() {
        return foodMapper.getAllFoods();
    }

    public Food getFoodById(Integer foodId) {
        return foodMapper.getFoodById(foodId);
    }

    public void updateFood(Food food) {
        foodMapper.updateFood(food);
    }

    public void deleteFood(Integer foodId) {
        foodMapper.deleteFood(foodId);
    }
}
