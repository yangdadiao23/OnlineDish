package com.yc.mpmvcboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.mpmvcboot.pojo.Dish;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.QiniuServiceImpl;
import com.yc.mpmvcboot.utils.PhotoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class DishController {
   private  final  static  Integer pageSize=4;

   private  final static  String filePath="E:/mp-mvc-boot/src/main/resources/static/photo/";

   @Autowired
   private QiniuServiceImpl qiniuService;

    @Autowired
    private DishServiceImpl dishService;

    @GetMapping("/index")
    public  String index(){
        return "index.html";
    }

    @GetMapping("/getAllDish")
    public String getAll(Model model){
        List<Dish> dishes = dishService.getBaseMapper().selectList(null);
        model.addAttribute("dishes",dishes);
        return "allDishes";
    }

    @GetMapping("/admin/getDishByPage/{pageNum}")
    public String getDishByPage(Model model,@PathVariable("pageNum")Integer  pageNum ){
        if(pageNum<=1)pageNum=1;
        Page<Dish> page = dishService.page(new Page<>(pageNum, pageSize));
        long pages = page.getPages();
        int pagesNew=(int) pages;
        if(pageNum>=pagesNew)pageNum=(int)pages;
        List<Dish> dishes = page.getRecords();
        int dishNum=dishes.size();
        model.addAttribute("dishes",dishes);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("pages",pages);
        model.addAttribute("dishNum",dishNum);
        return "allDishes";
    }

//    @GetMapping("/getDishByPage/{pageNum}")
//    public String getDishByPage(Model model,@PathVariable("pageNum")Integer  pageNum ){
//        Page<Dish> page = dishService.page(new Page<>(pageNum, pageSize));
//        long size = page.getSize();
//        List<Dish> dishes = page.getRecords();
//        model.addAttribute("dishes",dishes);
//        model.addAttribute("pageSize",pageSize);
//        return "allDishes";
//    }

    @GetMapping("/deleteDish/{id}/{dishNum}/{pageNum}")
    public String deleteDish(@PathVariable("id")Integer id,@PathVariable("dishNum")Integer dishNum,@PathVariable("pageNum")Integer pageNum){
        Dish dish = dishService.getBaseMapper().selectById(id);
        String name=dish.getPhoto();
        dishService.removeById(id);
        if(dishNum==1)pageNum=pageNum-1;
        File file = new File(filePath + name);
        file.delete();
        return "redirect:/admin/getDishByPage/"+pageNum.toString();
    }

    @GetMapping("/updateHtml/{id}/{pageNum}")
    public  String updateHtml(@PathVariable("id")Integer id,@PathVariable("pageNum")Integer pageNum,Model model){
        Dish dish = dishService.getBaseMapper().selectById(id);
        model.addAttribute("dish",dish);
        model.addAttribute("pageNum",pageNum);
        return  "updateHtml";
    }

    @GetMapping("/updateCheck/{id}/{pageNum}")
    public String updateCheck(@PathVariable("id")Integer id,@PathVariable("pageNum")Integer pageNum,Dish dish){
        dishService.updateById(dish);
        return "redirect:/admin/getDishByPage/"+pageNum.toString();
    }

    @GetMapping("/addHtml")
    public  String  addHtml(){
        return "addHtml";
    }

    @GetMapping("/addPhotoHtml")
    public  String addPhotoHtml(){
        return  "addPhotoHtml";
    }

    @GetMapping("/addCheck/{fileName}")
    public  String  addCheck(@PathVariable("fileName") String fileName,Dish dish) throws IOException {
        log.info("-------------");
        log.info(fileName);
        log.info("----------------");
        String[] split = fileName.split("\\.");
        String last=split[split.length-1];
        log.info(last);
        dish.setPhoto(dish.getName()+"."+last);
        dishService.getBaseMapper().insert(dish);
        String srcFile ="E:/mp-mvc-boot/src/main/resources/static/photo/"+fileName;
        String destFile="E:/mp-mvc-boot/src/main/resources/static/photo/"+dish.getName()+"."+last;
        PhotoUtils.resizeImage(srcFile,destFile,200,160,true);
        File file = new File(srcFile);
        file.delete();
        Page<Dish> page = dishService.page(new Page<>(1, pageSize));
        long pages = page.getPages();
        Integer pageNum=(int) pages;
        return "redirect:/admin/getDishByPage/"+pageNum.toString();
    }

    @GetMapping("/searchByName/{pageNum}")
    public  String searchByName(String dishName,Model model,@PathVariable("pageNum")Integer pageNum){
        QueryWrapper<Dish> objectQueryWrapper = new QueryWrapper<>();
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
        return "admin/searchDishes";
    }




}
