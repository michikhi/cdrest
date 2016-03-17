package com.rd.config;

import org.springframework.context.annotation.*;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.*;
import org.springframework.security.access.expression.method.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.authentication.configurers.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.ldap.*;
import org.springframework.security.oauth2.provider.expression.*;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {

        web
                .ignoring()
                .antMatchers("/h2console/**")
                .antMatchers("/api/register")
                .antMatchers("/api/activate")
                .antMatchers("/api/lostpassword")
                .antMatchers("/api/resetpassword")
                .antMatchers("/api/hello");

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
    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {

            auth.ldapAuthentication()
                    .userSearchFilter("(cn={0})")
                    .userSearchBase("ou=users,o=actelion,dc=corp")
                    .groupSearchBase("ou=rundeck,ou=app,ou=groups,o=actelion,dc=corp")
                    .groupSearchFilter("(member={0})")
                    .groupRoleAttribute("cn")
                    //.rolePrefix("ROLE_")
                    .contextSource(contextSource());

        }

        @Bean
        public BaseLdapPathContextSource contextSource() throws Exception {
            DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldaps://localhost:636/");

            contextSource.setUserDn("cn=RundeckAdmin,ou=users,o=system");
            contextSource.setPassword("xxxx");
            contextSource.setReferral("ignore");
            contextSource.afterPropertiesSet();

            return contextSource;
        }

        @Bean
        public LdapTemplate ldapTemplate() throws Exception {
            return new LdapTemplate(contextSource());
        }
    }

}

