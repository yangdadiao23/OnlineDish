package com.yc.mpmvcboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private  Integer id;
    private  String name;
    private  Integer count;
    private  float price;
    private  float totalPrice;
}
