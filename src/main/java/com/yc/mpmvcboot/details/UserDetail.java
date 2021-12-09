package com.yc.mpmvcboot.details;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.mpmvcboot.pojo.User;
import com.yc.mpmvcboot.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserDetail implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username", s);
        User user = userService.getOne(userQueryWrapper);
        String role = user.getRole();
        List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        if(user==null){
             throw new UsernameNotFoundException("user not found!");
        }else{
           return   new org.springframework.security.core.userdetails.User(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPassword()),auths);
        }
    }
}
