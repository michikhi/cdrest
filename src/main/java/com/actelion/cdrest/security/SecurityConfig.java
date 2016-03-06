package com.actelion.cdrest.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

import javax.sql.*;

/**
 * Created by mimounchikhi on 05/03/16.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    /*
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth) throws Exception{

        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER");
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");

    }
    */
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception{

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, true from users where username = ?")
                .authoritiesByUsernameQuery("select users_username as principal, roles_role as role from users_roles where users_username = ?")
                .rolePrefix("ROLE_");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Design pattern builder
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/css/**","/js/**","/images/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/index.html")
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .permitAll();
    }
}
