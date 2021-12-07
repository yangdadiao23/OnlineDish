package com.yc.mpmvcboot;

import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MpMvcBootApplicationTests {




    @Autowired
    private DishServiceImpl dishService;

    @Test
    public  void Test(){
        dishService.getBaseMapper().selectList(null).stream().forEach(System.out::println);
    }



}
