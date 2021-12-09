package com.yc.mpmvcboot.pojo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
    private Integer id;
    private  String username;
    private  String password;
    private  String role;
}
