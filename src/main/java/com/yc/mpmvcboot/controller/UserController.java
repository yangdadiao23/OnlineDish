package com.yc.mpmvcboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.pojo.User;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {


    private  final  static  Integer pageSize=4;
    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private UserServiceImpl userService;

//    @GetMapping("/user/getAllDish")
//    public  String userGetAllDish(Model model){
//        List<Dish> dishes = dishService.getBaseMapper().selectList(null);
//        model.addAttribute("dishes",dishes);
//        return "userDishes";
//    }

    @GetMapping("/user/getDishByPage/{pageNum}")
    public String getDishByPage(Model model,@PathVariable("pageNum")Integer  pageNum ){
        if(pageNum<=1)pageNum=1;
        Page<Dish> page = dishService.page(new Page<>(pageNum, pageSize));
        long pages = page.getPages();
        int pagesNew=(int) pages;
        if(pageNum>=pagesNew)pageNum=(int)pages;
        List<Dish> dishes = page.getRecords();
        model.addAttribute("dishes",dishes);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("pages",pages);
        return "userDishes";
    }

    @RequestMapping(value = "/user/registerHtml",method = {RequestMethod.GET,RequestMethod.POST})
    public String userRegisterHtml(){
        return "registerHtml";
    }

    @PostMapping("/user/register")
    public  String userRegister(User user, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        map.put("username",user.getUsername());
        List<User> users = userService.getBaseMapper().selectByMap(map);
        user.setRole("user");
        if(users.size()==0){
            userService.getBaseMapper().insert(user);
            request.getSession().setAttribute("message","注册成功！");
            return "redirect:/index";
        }else{
            request.setAttribute("message","用户名已存在！");
            return  "forward:/user/registerHtml";
        }
    }

    @GetMapping("/userSearchByName/{pageNum}")
    public  String searchByName(String dishName,Model model,@PathVariable("pageNum")Integer pageNum){
        QueryWrapper<Dish> objectQueryWrapper = new QueryWrapper<>();
        dishName=dishName.isEmpty()? (String) model.getAttribute("dishName") :dishName;
        objectQueryWrapper.like("name",dishName);
        Page<Dish> page = dishService.page(new Page<>(pageNum, pageSize), objectQueryWrapper);
        List<Dish> dishes = page.getRecords();
        long pages = page.getPages();
        long dishNum = dishes.size();
        model.addAttribute("dishes",dishes);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("pages",pages);
        model.addAttribute("dishNum",dishNum);
        model.addAttribute("dishName",dishName);
        return "userSearchDishes";
    }



}
