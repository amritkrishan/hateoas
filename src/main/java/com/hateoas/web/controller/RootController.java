package com.hateoas.web.controller;

import com.hateoas.web.resource.RootResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public RootResource root() {
        return new RootResource();
    }

}
