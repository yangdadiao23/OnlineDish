package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Cart;
import com.yc.mpmvcboot.pojo.CartItem;
import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CartController {
    @Autowired
    private DishServiceImpl dishService;

    private final Cart cart=new Cart();

    @GetMapping("/addCart/{id}/{pageNum}")
    public  String addCart(@PathVariable("id") Integer id, @PathVariable("pageNum")Integer pageNum,Model model){
        Dish dish = dishService.getBaseMapper().selectById(id);
        CartItem cartItem = new CartItem(id,dish.getName(),1,dish.getPrice(),dish.getPrice());
        cart.addItem(cartItem);
//        int curNum=dish.getNum();
//        if(curNum<=0){
//            dish.setNum(0);
//        }else{
//            dish.setNum(curNum-1);
//        }
//        dishService.updateById(dish);
        return "forward:/user/getDishByPage/"+pageNum.toString();
    }

    @GetMapping("/addSearchCart/{id}/{pageNum}")
    public  String addSearchCart(@PathVariable("id") Integer id, @PathVariable("pageNum")Integer pageNum,Model model,String dishName){
        Dish dish = dishService.getBaseMapper().selectById(id);
        CartItem cartItem = new CartItem(id,dish.getName(),1,dish.getPrice(),dish.getPrice());
        cart.addItem(cartItem);
//        int curNum=dish.getNum();
//        if(curNum<=0){
//            dish.setNum(0);
//        }else{
//            dish.setNum(curNum-1);
//        }
//        dishService.updateById(dish);
        model.addAttribute("dishName",dishName);
        return "redirect:/userSearchByName/"+pageNum.toString()+"?dishName="+dishName;
    }

    @GetMapping("/showCart/{pageNum}")
    public  String showCart(Model model, HttpSession httpSession,@PathVariable("pageNum")Integer pageNum){
        Map<Integer, CartItem> map = cart.getMap();
        model.addAttribute("map",map);
        int totalPrice=0;
        for(CartItem cartItem:map.values()){
            totalPrice+=cartItem.getTotalPrice();
        }
        model.addAttribute("totalPrice",totalPrice);
        httpSession.setAttribute("map",map);
        model.addAttribute("pageNum",pageNum);
        return "cart/cart";
//        model.addAttribute("map",map);
//        float totalPrice=0;
//        for(Dish dish:map.keySet()){
//            float price=dish.getPrice()*map.get(dish);
//            totalPrice+=price;
//        }
//        model.addAttribute("totalPrice",totalPrice);

    }

    @GetMapping("/deleteCart/{pageNum}")
    public  String deleteCart(@PathVariable("pageNum")Integer pageNum){
        cart.getMap().clear();
        return "forward:/showCart/"+pageNum.toString();
    }

    @GetMapping("/addOne/{id}/{pageNum}")
    public  String addOne(@PathVariable Integer id,@PathVariable("pageNum")Integer pageNum){
        Map<Integer, CartItem> map = cart.getMap();
        CartItem cartItem = map.get(id);
//        Dish dish = dishService.getBaseMapper().selectById(id);
//        dish.setNum(dish.getNum()-1);
        cart.addItem(cartItem);
        return "forward:/showCart/"+pageNum.toString();
    }

    @GetMapping("/deleteOne/{id}/{pageNum}")
    public  String deleteOne(@PathVariable Integer id,@PathVariable("pageNum")Integer pageNum){
        Map<Integer, CartItem> map = cart.getMap();
        CartItem cartItem = map.get(id);
//        Dish dish = dishService.getBaseMapper().selectById(id);
//        dish.setNum(dish.getNum()+1);
        cart.deleteItem(cartItem);
        return "forward:/showCart/"+pageNum.toString();
    }

}
