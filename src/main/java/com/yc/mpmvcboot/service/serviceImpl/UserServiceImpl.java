package com.yc.mpmvcboot.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.mpmvcboot.mapper.UserMapper;
import com.yc.mpmvcboot.pojo.User;
import com.yc.mpmvcboot.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
