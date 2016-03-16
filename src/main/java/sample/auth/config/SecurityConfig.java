package sample.auth.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.ldap.core.support.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.ldap.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${myapp.ldap.url}")
    private String ldapUrl;
	
	@Value("${myapp.ldap.ldif}")
    private String ldapLdif;
	
	@Value("${myapp.ldap.user-dn-patterns}")
    private String ldapUserDnPatterns;
	
	@Value("${myapp.ldap.user-search-base}")
    private String ldapUserSearchBase;
	
	@Value("${myapp.ldap.group-search-base}")
    private String ldapGroupSearchBase;
	
	@Value("${myapp.ldap.group-search-filter}")
    private String ldapGroupSearchFilter;

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/*		auth
			.ldapAuthentication()
			.userDnPatterns(ldapUserDnPatterns)
			.groupSearchBase(ldapGroupSearchBase)
			.contextSource()
//			.url(ldapUrl);
			.ldif(ldapLdif);*/

/*
        auth.ldapAuthentication()
                .userDnPatterns(ldapGroupSearchBase)
                .groupSearchBase("ou=groups")
                .contextSource()
                .managerDn("cn=Admin,ou=users,o=system")
                .managerPassword("Actel10n$")
                .url(ldapUrl)
        ;
        */
        auth.ldapAuthentication()
                .userSearchFilter("(cn={0})")
                .userSearchBase("ou=users,o=actelion,dc=corp")
                .groupSearchBase("ou=rundeck,ou=app,ou=groups,o=actelion,dc=corp")
                .groupSearchFilter("(member={0})")
                .groupRoleAttribute("cn")
                //.rolePrefix("ROLE_")
                .contextSource(contextSource());


/*
        LdapContextSource lcs = new LdapContextSource();

        lcs.setBase(ldapUserSearchBase);
        lcs.setUserDn("cn=Admin,ou=users,o=system");
        lcs.setPassword("Actel10n$");
        lcs.setPooled(false);
        lcs.setUrl(ldapUrl);

        DefaultTlsDirContextAuthenticationStrategy strategy = new DefaultTlsDirContextAuthenticationStrategy();
        strategy.setShutdownTlsGracefully(true);
        //strategy.setSslSocketFactory(new CustomSSLSocketFactory());  // <-- not considered at all
        strategy.setHostnameVerifier(new HostnameVerifier(){

            @Override
            public boolean verify(String hostname, SSLSession session){

                return true;
            }
        });

        lcs.setAuthenticationStrategy(strategy);
        lcs.afterPropertiesSet();
        lcs.getContext("cn=Admin,ou=users,o=system", "Actel10n$");

        auth.ldapAuthentication()
                .contextSource(lcs);
                ;
*/



    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public BaseLdapPathContextSource contextSource() throws Exception {

        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldaps://cdpub.actelion.com:636/");

        contextSource.setUserDn("cn=RundeckAdmin,ou=users,o=system");
        contextSource.setPassword("xnRJUJd4aLy4zu");
        contextSource.setReferral("ignore");
        contextSource.afterPropertiesSet();

        return contextSource;
    }
	
}
