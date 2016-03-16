package com.rd.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/status")
public class StatusController {


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String status() {
        return "Server is up.";
    }

}
