package com.yc.mpmvcboot.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Dish {
   private long id;
   private  String name;
   private  int num;
   private  float price;
   private  String photo;
}
