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

		OperationRepository operationRepository = ctx.getBean(OperationRepository.class);
        operationRepository.save(new Operation(1L,"test1",new Date()));
        operationRepository.save(new Operation(2L,"test2",new Date()));
        operationRepository.save(new Operation(3L,"test3",new Date()));
        operationRepository.save(new Operation(4L,"test4",new Date()));

        List<Operation> list = operationRepository.findAll();
        list.forEach(e->System.out.println(e.getId()));
	}
}
