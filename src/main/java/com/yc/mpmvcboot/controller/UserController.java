package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.pojo.User;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user/getAllDish")
    public  String userGetAllDish(Model model){
        List<Dish> dishes = dishService.getBaseMapper().selectList(null);
        model.addAttribute("dishes",dishes);
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



}
