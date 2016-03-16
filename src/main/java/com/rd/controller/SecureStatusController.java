package com.rd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/secure")
public class SecureStatusController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String status() {
        return "Rest services are secured";
    }

}
