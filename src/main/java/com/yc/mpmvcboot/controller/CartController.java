package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Cart;
import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class CartController {
    @Autowired
    private DishServiceImpl dishService;

    private Cart cart=new Cart();

    @GetMapping("/addCart/{id}")
    public  String addCart(@PathVariable Integer id){
        Dish dish = dishService.getBaseMapper().selectById(id);
        Map<Dish, Integer> map = cart.getMap();
        map.put(dish,map.getOrDefault(dish,0)+1);
        return "forward:/user/getAllDish";
    }

    @GetMapping("/showCart")
    public  String showCart(Model model){
        Map<Dish, Integer> map = cart.getMap();
        model.addAttribute("map",map);
        float totalPrice=0;
        for(Dish dish:map.keySet()){
            float price=dish.getPrice()*map.get(dish);
            totalPrice+=price;
        }
        model.addAttribute("totalPrice",totalPrice);
        return "cart/cart";
    }

    @GetMapping("/deleteCart")
    public  String deleteCart(){
        cart.getMap().clear();
        return "forward:/showCart";
    }
}
