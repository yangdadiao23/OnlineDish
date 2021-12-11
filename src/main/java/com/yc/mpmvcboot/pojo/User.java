package com.yc.mpmvcboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private  String username;
    private  String password;
    private  String role;
}
