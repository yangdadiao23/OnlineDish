package com.yc.mpmvcboot.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.mpmvcboot.mapper.OrderItemMapper;
import com.yc.mpmvcboot.pojo.OrdersItem;
import com.yc.mpmvcboot.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrdersItem> implements OrderItemService {
}
