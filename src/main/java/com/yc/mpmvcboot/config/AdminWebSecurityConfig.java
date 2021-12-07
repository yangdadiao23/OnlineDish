package com.yc.mpmvcboot.config;

import com.yc.mpmvcboot.details.AdminDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(value = 2)
public class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminDetail adminDetail;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetail).passwordEncoder(Password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/getAllDish").permitAll()
                .and().authorizeRequests()
                .antMatchers("/getAllDish","/deleteDish","/addDish").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().csrf().disable();

        http.exceptionHandling().accessDeniedPage("/403.html");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index");


    }

    @Bean
    PasswordEncoder Password(){
        return  new BCryptPasswordEncoder();
    }
}
