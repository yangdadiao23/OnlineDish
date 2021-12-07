package com.yc.mpmvcboot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

@Data
@ToString
public class Student {

    private  long id;
    private  String name;
    private  int age;

    @TableField(fill = INSERT)
    private java.util.Date insertTime;


    @TableField(fill = INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    private  int deleted;

}
