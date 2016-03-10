package com.actelion.cdrest.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.core.annotation.*;
import org.springframework.security.access.expression.method.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.provider.expression.*;

/**
 * Created by mimounchikhi on 05/03/16.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;
    /*
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth) throws Exception{

        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER");
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");

    }
    */
//    @Autowired
//    public void globalConfig(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception{
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username as principal, password as credentials, true from users where username = ?")
//                .authoritiesByUsernameQuery("select users_username as principal, roles_role as role from users_roles where users_username = ?")
//                .rolePrefix("ROLE_");
//
//    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
        //        .passwordEncoder(passwordEncoder())
        ;

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

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }

    }
}
