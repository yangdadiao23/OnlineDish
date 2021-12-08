package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Orders;
import com.yc.mpmvcboot.pojo.User;
import com.yc.mpmvcboot.service.serviceImpl.OrderServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.UserServiceImpl;
import org.omg.CORBA.IRObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/orderDish/{totalPrice}")
    public  String orderDish(HttpServletRequest request, @PathVariable("totalPrice")float price){
        String name = request.getUserPrincipal().getName();
        Map<String,Object> map=new HashMap<>();
        map.put("username",name);
        List<User> users = userService.getBaseMapper().selectByMap(map);
        User user=users.get(0);
        Integer userid=user.getId();
        Orders orders = new Orders(UUID.randomUUID().toString(),price,userid,new Date());
        request.setAttribute("order",orders);
        request.setAttribute("name",user.getUsername());
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





}
