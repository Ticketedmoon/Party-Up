package com.partyup.webapplication.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EntryController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @GetMapping(value = "/**")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("react/index");
        modelAndView.addObject("profile", activeProfile);
        return modelAndView;
    }

}
