package com.actelion.cdrest.oauth.service;

import com.actelion.cdrest.dao.*;
import com.actelion.cdrest.entities.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;


@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();


        com.actelion.cdrest.entities.User userFromDatabase = null;
        userFromDatabase = userRepository.findOne(lowercaseLogin);

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        } else if (!userFromDatabase.isActivated()) {
            try {
                throw new Exception("User " + lowercaseLogin + " is not activated");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role authority : userFromDatabase.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRole());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(), userFromDatabase.getPassword(), grantedAuthorities);

    }

}
