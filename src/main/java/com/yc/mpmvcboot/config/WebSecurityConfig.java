package com.yc.mpmvcboot.config;

import com.yc.mpmvcboot.details.AdminDetail;
import com.yc.mpmvcboot.successHandler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig {

       @Configuration
       @Order(1)
    static class AdminWebSecurityConfig extends WebSecurityConfigurerAdapter {

           @Autowired
           private AdminDetail adminDetail;

           @Override
           protected void configure(AuthenticationManagerBuilder auth) throws Exception {
               auth.userDetailsService(adminDetail).passwordEncoder(Password());
           }

           @Override
           protected void configure(HttpSecurity http) throws Exception {
               http.formLogin()
                       .successHandler(new LoginSuccessHandler())
                       .loginPage("/adminLogin.html")
                       .loginProcessingUrl("/admin/login")
//                       .defaultSuccessUrl("/adminIndex").permitAll()
                       .permitAll()
                       .and().authorizeRequests()
                       .antMatchers("/getAllDish", "/deleteDish", "/addDish").hasAuthority("admin")
                       .anyRequest().authenticated()
                       .and().csrf().disable();

               http.exceptionHandling().accessDeniedPage("/403.html");
               http.logout().logoutUrl("/logout").logoutSuccessUrl("/index");


           }

           @Bean
           PasswordEncoder Password() {
               return new BCryptPasswordEncoder();
           }
       }

       @Configuration
       @Order(2)
    static class UserWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(password());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()
                    .loginPage("/userLogin.html")
                    .loginProcessingUrl("/user/login")
                    .defaultSuccessUrl("/").permitAll()
                    .and().authorizeRequests()
                    .antMatchers("/students").hasAuthority("admin")
                    .antMatchers("/get").hasAuthority("user")
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


}
