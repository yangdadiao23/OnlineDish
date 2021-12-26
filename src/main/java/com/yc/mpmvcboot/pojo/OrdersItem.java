package com.yc.mpmvcboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersItem {
    private  Integer id;
    private  String name;
    private  float price;
    private  Integer count;
    private  float totalPrice;
    private  String uuid;
}
