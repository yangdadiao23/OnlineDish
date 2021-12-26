package com.yc.mpmvcboot.controller;


import com.yc.mpmvcboot.pojo.Orders;
import com.yc.mpmvcboot.pojo.OrdersItem;
import com.yc.mpmvcboot.service.serviceImpl.OrderItemServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderItemServiceImpl orderItemService;

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

    @GetMapping("/getAdminOrderDetails/{uuid}")
    public  String getOrderDetail(@PathVariable("uuid")String uuid, Model model){
        Map<String,Object> map=new HashMap<>();
        map.put("uuid",uuid);
        List<OrdersItem> ordersItems = orderItemService.getBaseMapper().selectByMap(map);
        model.addAttribute("orderItems",ordersItems);
        return "order/adminOrderDetail";
    }
}
