package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DishController {

    @Autowired
    private DishServiceImpl dishService;

    @GetMapping("/index")
    public  String index(){
        return "index.html";
    }

    @GetMapping("/getAllDish")
    public String getAll(Model model){
        List<Dish> dishes = dishService.getBaseMapper().selectList(null);
        model.addAttribute("dishes",dishes);
        return "allDishes";
    }


    @GetMapping("/deleteDish/{id}")
    public String deleteDish(@PathVariable("id")Integer id){
        dishService.removeById(id);
        return "redirect:/getAllDish";
    }

    @GetMapping("/updateHtml/{id}")
    public  String updateHtml(@PathVariable("id")Integer id,Model model){
        Dish dish = dishService.getBaseMapper().selectById(id);
        model.addAttribute("dish",dish);
        return  "updateHtml";
    }

    @GetMapping("/updateCheck/{id}")
    public String updateCheck(@PathVariable("id")Integer id,Dish dish){
        dishService.updateById(dish);
        return "redirect:/getAllDish";
    }



    @GetMapping("/addHtml")
    public  String  addHtml(){
        return "addHtml";
    }


    @GetMapping("/addCheck")
    public  String  addCheck(Dish dish){
        dishService.getBaseMapper().insert(dish);
        return "redirect:/getAllDish";
    }




}
