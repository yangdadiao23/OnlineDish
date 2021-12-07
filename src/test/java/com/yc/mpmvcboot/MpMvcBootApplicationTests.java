package com.yc.mpmvcboot;

import com.yc.mpmvcboot.mapper.StudentMapper;
import com.yc.mpmvcboot.pojo.Student;
import com.yc.mpmvcboot.service.serviceImpl.DishServiceImpl;
import com.yc.mpmvcboot.service.serviceImpl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MpMvcBootApplicationTests {

    @Autowired
    private  StudentMapper studentMapper;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private DishServiceImpl dishService;

    @Test
    public  void Test(){
        dishService.getBaseMapper().selectList(null).stream().forEach(System.out::println);
    }


    @Test
    public  void test()
    {
        List<Student> students = studentMapper.selectList(null);
        students.stream().forEach(System.out::println);
        System.out.println("==================");
        List<Student> studentServices = studentService.getBaseMapper().selectList(null);
        studentServices.stream().forEach(System.out::println);
    }
}
