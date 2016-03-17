package com.rd.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;
import org.springframework.ldap.*;
import org.springframework.ldap.core.*;
import org.springframework.ldap.query.*;
import org.springframework.security.authentication.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@EnableResourceServer
@RestController
public class UserController {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		try {
			LdapQuery q = LdapQueryBuilder.query().where("objectclass").is("actUser").and("cn").is("chikhmi1");
			ldapTemplate.authenticate(q, "toto");


		} catch (AuthenticationException | EmptyResultDataAccessException e) {
			e.printStackTrace();
			throw new BadCredentialsException(e.getMessage());
		}


		return user;
	}
	
}
