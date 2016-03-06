package com.actelion.cdrest.services;

import com.actelion.cdrest.dao.*;
import com.actelion.cdrest.entities.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by mimounchikhi on 01/03/16.
 */
@RestController
@Secured(value={"ROLE_ADMIN"})
public class UserRestService {

    //@Autowired
    //SecurityContext securityContext; // another way to get SecurityContext
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/getLoggedUser")
    public Map<String,Object> getLoggedUser(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContext.getAuthentication().getName();
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()) {
            roles.add(ga.getAuthority());
        }
        Map<String,Object> params = new HashMap<>();
        params.put("username",username);
        params.put("roles",roles);

        return params;
    }

    @RequestMapping(value = "/addUser")
    public User save(User u) {
        return userRepository.save(u);
    }

    @RequestMapping(value = "/findUsers")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/addRole")
    public Role save(Role r) {
        return roleRepository.save(r);
    }

    @RequestMapping(value = "/findRoles")
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @RequestMapping(value = "/addRoleToUser")
    public User addRoleToUser(String username, String role) {
        User u = userRepository.findOne(username);
        Role r = roleRepository.findOne(role);
        u.getRoles().add(r);
        userRepository.save(u);
        return u;
    }

}
