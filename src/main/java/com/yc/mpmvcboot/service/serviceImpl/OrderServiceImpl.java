package com.yc.mpmvcboot.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.mpmvcboot.mapper.OrderMapper;
import com.yc.mpmvcboot.pojo.Orders;
import com.yc.mpmvcboot.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

}
