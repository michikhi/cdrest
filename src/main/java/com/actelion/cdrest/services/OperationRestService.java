package com.actelion.cdrest.services;

import com.actelion.cdrest.dao.*;
import com.actelion.cdrest.entities.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

/**
 * Created by mimounchikhi on 01/03/16.
 */
@RestController
public class OperationRestService {

    @Autowired
    private OperationRepository operationRepository;

    @Secured(value={"ROLE_ADMIN"})
    @RequestMapping(value = "/operations", method = RequestMethod.POST)
    public Object saveOperation(@RequestBody @Valid Operation operation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("errors",true);
            for(FieldError fieldError:bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return errors;
        }
        return operationRepository.save(operation);
    } //RequestBody for JSON format send

    @Secured(value={"ROLE_ADMIN","ROLE_USER"})
    @RequestMapping(value = "/operations")
    public Page<Operation> listOperations(int page, int size) {
        return operationRepository.findAll(new PageRequest(page,size));
    }


}
