package com.yc.mpmvcboot.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.mpmvcboot.mapper.StudentMapper;
import com.yc.mpmvcboot.pojo.Student;
import com.yc.mpmvcboot.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
