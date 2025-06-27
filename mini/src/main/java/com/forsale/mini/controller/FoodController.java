package com.forsale.mini.controller;
import com.forsale.mini.bean.Food;
import com.forsale.mini.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final static Logger log = LoggerFactory.getLogger(FoodController.class);
    @Autowired
    private FoodService foodService;

    @PostMapping("/add")
    public void addFood(@RequestBody Food food) {
        foodService.addFood(food);
    }

    @GetMapping("/getAll")
    public List<Food> getAllFoods() {

        log.error("我是 alls "+foodService.getAllFoods());
        return foodService.getAllFoods();
    }

    @GetMapping("/{foodId}")
    public Food getFoodById(@PathVariable Integer foodId) {
        return foodService.getFoodById(foodId);
    }

    @PutMapping("/update")
    public void updateFood(@RequestBody Food food) {
        foodService.updateFood(food);
    }

    @DeleteMapping("/delete/{foodId}")
    public void deleteFood(@PathVariable Integer foodId) {
        foodService.deleteFood(foodId);
    }
}
