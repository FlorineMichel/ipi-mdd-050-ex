package com.ipiecoles.java.mdd050.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/sayhello")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET,produces = "text/plain")
    public String Hello(){
        return "Hello World !";
    }
}
