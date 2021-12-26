package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.*;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.OrderItemServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.OrderServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private OrderItemServiceImpl orderItemService;


    @GetMapping("/orderDish/{totalPrice}")
    public  String orderDish(HttpServletRequest request, @PathVariable("totalPrice")float price){
        String name = request.getUserPrincipal().getName();
        Map<String,Object> map=new HashMap<>();
        map.put("username",name);
        List<User> users = userService.getBaseMapper().selectByMap(map);
        User user=users.get(0);
        Integer userid=user.getId();
        String uuid=UUID.randomUUID().toString();
        SimpleDateFormat f = new SimpleDateFormat( "yyyy 年 MM 月 dd 日  HH 点 mm 分 ss 秒");
        Date date = new Date();
        Orders orders = new Orders(uuid,price,userid,f.format(date));
        orderService.getBaseMapper().insert(orders);
        request.setAttribute("order",orders);
        request.setAttribute("name",user.getUsername());
        request.setAttribute("uuid",uuid);
        Map<Integer, CartItem> cartMap = (Map<Integer, CartItem>) request.getSession().getAttribute("map");
        for(CartItem cartItem:cartMap.values()){
            Integer id = cartItem.getId();
            Integer count = cartItem.getCount();
            float totalPrice = cartItem.getTotalPrice();
            Dish dish = dishService.getBaseMapper().selectById(id);
            int curCount=dish.getNum();
            if(curCount-count<0){
                dish.setNum(0);
            }else{
                dish.setNum(curCount-count);
            }
            dishService.getBaseMapper().updateById(dish);
            OrdersItem ordersItem = new OrdersItem(id,cartItem.getName(),dish.getPrice(),count,totalPrice,uuid);
            orderItemService.getBaseMapper().insert(ordersItem);
        }
        return "order/order";
    }


    @GetMapping("/getOrders")
    public  String getOrders(HttpServletRequest request){
        String name = request.getUserPrincipal().getName();
        Map<String,Object> map=new HashMap<>();
        map.put("username",name);
        List<User> users = userService.getBaseMapper().selectByMap(map);
        User user=users.get(0);
        Integer userid=user.getId();
        Map<String,Object>orderMap=new HashMap<>();
        orderMap.put("userid",userid);
        List<Orders> orders = orderService.getBaseMapper().selectByMap(orderMap);
        request.setAttribute("orders",orders);
        return  "order/showOrder";
    }

    @GetMapping("/getOrderDetails/{uuid}")
    public  String getOrderDetail(@PathVariable("uuid")String uuid, Model model){
        Map<String,Object>map=new HashMap<>();
        map.put("uuid",uuid);
        List<OrdersItem> ordersItems = orderItemService.getBaseMapper().selectByMap(map);
        model.addAttribute("orderItems",ordersItems);
        return "order/orderDetail";
    }
}
