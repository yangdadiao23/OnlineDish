package com.yc.mpmvcboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private  String uuid;
    private  float price;
    private  Integer userid;
    private Date date;
}
