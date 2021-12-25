package com.yc.mpmvcboot.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class Cart {
   private Map<Integer,CartItem> map=new HashMap<>();


   public void addItem(CartItem item) {
      CartItem cartItem = map.get(item.getId());
      if (cartItem == null) {
         map.put(item.getId(), item);
      } else {
         cartItem.setCount(cartItem.getCount() + 1);
         cartItem.setTotalPrice(item.getPrice() * cartItem.getCount());
      }
   }


   public void deleteItem(CartItem item) {
      CartItem cartItem = map.get(item.getId());
      if (cartItem.getCount() >= 1) {
         cartItem.setCount(cartItem.getCount() - 1);
         cartItem.setTotalPrice(item.getPrice() * cartItem.getCount());
      }
      if(cartItem.getCount()<=0){
         map.remove(item.getId());
      }
   }
}
