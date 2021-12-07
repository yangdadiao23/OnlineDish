package com.yc.mpmvcboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.mpmvcboot.pojo.Student;
import com.yc.mpmvcboot.service.serviceImpl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 使用security注解
 * 1.在总配置类中加入注解
 * 2.@secured @preAuthority  @postAuthority @preFilter @postFilter
 */
@Controller
public class studentController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @GetMapping("/students")
    public  String students(Model model){
        List<Student> students = studentServiceImpl.list(null);
        model.addAttribute("students",students);
        return  "student";
    }

    @GetMapping("/index")
    public  String index(){
        return "index.html";
    }


    @GetMapping("/get")
    @ResponseBody
    public  Student get(@RequestParam("id")Integer id){
        Student student = studentServiceImpl.getById(1);
        return  student;
    }

    @GetMapping("/update")
    public  String update(Student student){
        boolean b = studentServiceImpl.updateById(student);
        return   "redirect:/students";
    }

    @GetMapping("/delete")
    @ResponseBody
    public  String delete(@RequestParam("id")Integer id){
        boolean b = studentServiceImpl.removeById(id);
        return  String.valueOf(b);
    }


    @GetMapping("/add")
    public  String add(Student student){
        studentServiceImpl.save(student);
        return "redirect:/students";
    }


    @PreAuthorize("hasAuthority('admin')")
    @PostFilter("filterObject.name=='ycc'")
    @GetMapping("/selectByAge")
    @ResponseBody
    public  List<Student> selectByAge(){
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.between("age",18,22);
        List<Student> students = studentServiceImpl.list(wrapper);
          return  students;
    }

}
