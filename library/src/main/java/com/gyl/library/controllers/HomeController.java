package com.gyl.library.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ModelAndView home(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

}