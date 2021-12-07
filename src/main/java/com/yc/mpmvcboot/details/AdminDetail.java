package com.yc.mpmvcboot.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminDetailService")
public class AdminDetail implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        if(s.equals("root")){
            return   new org.springframework.security.core.userdetails.User("root",new BCryptPasswordEncoder().encode("root"),auths);
        }else{
            throw new UsernameNotFoundException("admin not found!");
        }
    }
}
