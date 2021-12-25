package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Cart;
import com.yc.mpmvcboot.pojo.CartItem;
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

    private final Cart cart=new Cart();

    @GetMapping("/addCart/{id}")
    public  String addCart(@PathVariable Integer id,Model model){
        Dish dish = dishService.getBaseMapper().selectById(id);
        CartItem cartItem = new CartItem(id,dish.getName(),1,dish.getPrice(),dish.getPrice());
        cart.addItem(cartItem);
        return "forward:/user/getAllDish";
    }

    @GetMapping("/showCart")
    public  String showCart(Model model){
        Map<Integer, CartItem> map = cart.getMap();
        model.addAttribute("map",map);
        int totalPrice=0;
        for(CartItem cartItem:map.values()){
            totalPrice+=cartItem.getTotalPrice();
        }
        model.addAttribute("totalPrice",totalPrice);
        return "cart/cart";
//        model.addAttribute("map",map);
//        float totalPrice=0;
//        for(Dish dish:map.keySet()){
//            float price=dish.getPrice()*map.get(dish);
//            totalPrice+=price;
//        }
//        model.addAttribute("totalPrice",totalPrice);

    }

    @GetMapping("/deleteCart")
    public  String deleteCart(){
        cart.getMap().clear();
        return "forward:/showCart";
    }

    @GetMapping("/addOne/{id}")
    public  String addOne(@PathVariable Integer id){
        Map<Integer, CartItem> map = cart.getMap();
        CartItem cartItem = map.get(id);
        cart.addItem(cartItem);
        return "forward:/showCart";
    }

    @GetMapping("/deleteOne/{id}")
    public  String deleteOne(@PathVariable Integer id){
        Map<Integer, CartItem> map = cart.getMap();
        CartItem cartItem = map.get(id);
        cart.deleteItem(cartItem);
        return "forward:/showCart";
    }

}
