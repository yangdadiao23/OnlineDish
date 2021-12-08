package com.yc.mpmvcboot.controller;


import com.yc.mpmvcboot.pojo.Orders;
import com.yc.mpmvcboot.service.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/adminIndex")
    public  String adminIndex(){
        return "admin/adminIndex";
    }

    @GetMapping("/allOrders")
    public  String getAllOrder(Model model){
        List<Orders> orders = orderService.getBaseMapper().selectList(null);
        model.addAttribute("orders",orders);
        return "admin/AdminOrders";
    }
}
