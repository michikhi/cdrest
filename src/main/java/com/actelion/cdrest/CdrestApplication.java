package com.actelion.cdrest;

import com.actelion.cdrest.dao.*;
import com.actelion.cdrest.entities.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;

import java.util.*;

@SpringBootApplication
public class CdrestApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(CdrestApplication.class, args);

        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
            Role roleAdmin = new Role("USER","USER");
            Role roleUser = new Role("ADMIN","ADMIN");
            Role rolePassword = new Role("PASSWORD","PASSWORD");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        roleRepository.save(rolePassword);

            Collection<Role> rolesAdmin = new ArrayList<>();
            rolesAdmin.add(roleAdmin);
            rolesAdmin.add(roleUser);
            Collection<Role> rolesUser = new ArrayList<>();
            rolesAdmin.add(roleUser);

            UserRepository userRepository = ctx.getBean(UserRepository.class);
            User admin = new User("admin","admin",true);
            admin.setRoles(rolesAdmin);
            User user = new User("user","user",true);
            admin.setRoles(rolesUser);
        userRepository.save(admin);
        userRepository.save(user);

        OperationRepository operationRepository = ctx.getBean(OperationRepository.class);
        operationRepository.save(new Operation(1L,"test1",new Date()));
        operationRepository.save(new Operation(2L,"test2",new Date()));
        operationRepository.save(new Operation(3L,"test3",new Date()));
        operationRepository.save(new Operation(4L,"test4",new Date()));

        List<Operation> list = operationRepository.findAll();
        list.forEach(e->System.out.println(e.getId()));
	}
}
