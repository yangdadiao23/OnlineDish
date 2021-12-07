package com.yc.mpmvcboot.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class Cart {
   private Map<Dish,Integer> map=new HashMap<>();

}
