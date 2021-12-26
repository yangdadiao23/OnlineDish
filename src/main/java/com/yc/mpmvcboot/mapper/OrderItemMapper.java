package com.yc.mpmvcboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.mpmvcboot.pojo.OrdersItem;
import org.springframework.stereotype.Component;

@Component
public interface OrderItemMapper extends BaseMapper<OrdersItem> {
    void selectByMap(String uuid);
}
