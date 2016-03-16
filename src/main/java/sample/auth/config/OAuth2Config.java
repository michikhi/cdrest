package sample.auth.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.security.authentication.*;
import org.springframework.security.ldap.*;
import org.springframework.security.ldap.search.*;
import org.springframework.security.ldap.userdetails.*;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.config.annotation.configurers.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.*;

import java.security.*;
import java.util.*;



@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Value("${myapp.client.name:myapp}")
    private String clientName;
	
	@Value("${myapp.client.secret:myappsecret}")
    private String clientSecret;
	
	@Value("${myapp.client.scope:myapp}")
    private String clientScope;
	
	@Value("${myapp.keystore.name:keystore.jks}")
    private String keystore;
	
	@Value("${myapp.keystore.pass:keystorepass}")
    private String keystorepass;
	
	@Value("${myapp.key.name:myappkey}")
    private String key;
	
	@Value("${myapp.key.pass:keypass}")
    private String keypass;
	
	@Value("${myapp.ldap.url}")
    private String ldapUrl;
	
	@Value("${myapp.ldap.user-dn-patterns}")
    private String ldapUserDnPatterns;
	
	@Value("${myapp.ldap.user-search-base}")
    private String ldapUserSearchBase;
	
	@Value("${myapp.ldap.group-search-base}")
    private String ldapGroupSearchBase;
	
	@Value("${myapp.ldap.group-search-filter}")
    private String ldapGroupSearchFilter;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient(clientName)
			.secret(clientSecret)
			.accessTokenValiditySeconds(300)
			.refreshTokenValiditySeconds(600)
			.authorizedGrantTypes("refresh_token", "password")
			.scopes(clientScope);
	}
	
	/*
	 * JWT token
	 */
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {       
        endpoints.authenticationManager(authenticationManager)
        	//.accessTokenConverter(jwtAccessTokenConverter())
        	.tokenServices(defaultTokenServices())
        	.userDetailsService(ldapUserDetailsManager());
    }
	
	@Autowired
    private ClientDetailsService clientDetailsService;
	
	@Bean
    public DefaultTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
	
	@Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		List list = new ArrayList();
		list.add(new AmbaTokenEnhancer());
		list.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(list);
        return tokenEnhancerChain;
    }
	
	private static class AmbaTokenEnhancer implements TokenEnhancer {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            final DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
            final LdapUserDetails user = (LdapUserDetails) authentication.getPrincipal();
//            result.getAdditionalInformation().put("userId", 9999);
            return result;
        }
    }
	
	@Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
	
	@Bean
    public DefaultSpringSecurityContextSource contextSource() {
		return new DefaultSpringSecurityContextSource(ldapUrl);
    }
	
	@Bean 
	public FilterBasedLdapUserSearch userSearch() {
		return new FilterBasedLdapUserSearch(ldapUserSearchBase, "cn={0}", contextSource());
	}
	
	@Bean
	public DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
		DefaultLdapAuthoritiesPopulator authPopulator = new DefaultLdapAuthoritiesPopulator(contextSource(), ldapGroupSearchBase);
		authPopulator.setGroupSearchFilter(ldapGroupSearchFilter);
		return authPopulator;
	}
	
	@Bean
	public LdapUserDetailsService ldapUserDetailsManager() {
		return new LdapUserDetailsService(userSearch(), ldapAuthoritiesPopulator());
	}
    
    @Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(
				new ClassPathResource(keystore), keystorepass.toCharArray())
				.getKeyPair(key, keypass.toCharArray());
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer)
			throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	
}
