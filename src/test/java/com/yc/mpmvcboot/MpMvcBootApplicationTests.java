package com.yc.mpmvcboot;

import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.pojo.Orders;
import com.yc.mpmvcboot.pojo.User;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.OrderServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class MpMvcBootApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private DishServiceImpl dishService;

    @Test
    public  void Test(){
        dishService.getBaseMapper().selectList(null).stream().forEach(System.out::println);
    }

    @Test
    public  void Test2(){
        UUID uuid = UUID.randomUUID();
        Map<Dish,Integer>map=new HashMap<>();
        Dish dish = dishService.getBaseMapper().selectById(4);
        map.put(dish,3);
        float price=3*dish.getPrice();
        System.out.println(uuid);
        Orders order = new Orders();
        order.setUuid(uuid.toString());
        order.setPrice(65);
        order.setUserid(3);
        order.setDate(new Date());
        orderService.getBaseMapper().insert(order);
    }

    @Test
    public  void test(){
        User user=new User();
        user.setUsername("qwer");
        user.setPassword("qwer");
        user.setRole("user");
        int insert = userService.getBaseMapper().insert(user);
        System.out.println(insert);
    }



}
