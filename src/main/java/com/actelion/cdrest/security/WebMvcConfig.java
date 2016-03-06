package com.actelion.cdrest.security;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by mimounchikhi on 05/03/16.
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");//if login.html, it looks at folder static
        registry.addViewController("/logout").setViewName("login");
    }
}
