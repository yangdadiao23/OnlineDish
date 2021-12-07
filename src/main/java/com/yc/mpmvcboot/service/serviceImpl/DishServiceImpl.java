package com.yc.mpmvcboot.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.mpmvcboot.mapper.DishMapper;
import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
