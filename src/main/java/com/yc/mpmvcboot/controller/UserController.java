package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private DishServiceImpl dishService;

    @GetMapping("/user/getAllDish")
    public  String userGetAllDish(Model model){
        List<Dish> dishes = dishService.getBaseMapper().selectList(null);
        model.addAttribute("dishes",dishes);
        return "userDishes";
    }



}
