package com.yc.mpmvcboot.config;


import com.yc.mpmvcboot.successHandler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private  UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(new LoginSuccessHandler())
                .loginPage("/userLogin.html")
                .loginProcessingUrl("/user/login").
                permitAll()
                .and().authorizeRequests()
                .antMatchers("/user/registerHtml").permitAll()
                .antMatchers("/getAllDish","//deleteDish/{id}","/updateHtml/{id}","/updateCheck/{id}","/addHtml","/addCheck").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().csrf().disable();

        http.exceptionHandling().accessDeniedPage("/403.html");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index");
    }



    @Bean
    PasswordEncoder password(){
        return  new BCryptPasswordEncoder();
    }


}
