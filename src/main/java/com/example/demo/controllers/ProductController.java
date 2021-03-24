package com.example.demo.controllers;

import com.example.demo.services.UserViewImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    private final UserViewImpl userView;

    public ProductController(UserViewImpl userView) {
        this.userView = userView;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView message() {
        userView.addUserView("1");
        return new ModelAndView("interaction", "navMessages", userView.getUserViewByUserId("1").getNavigationMessage().split("\n"));
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ModelAndView answer(@ModelAttribute("answer") String answer) {
        userView.interact(answer, "1");
        String[] navMessages = userView.getUserViewByUserId("1").getNavigationMessage().split("\n");
        return new ModelAndView("interaction", "navMessages", navMessages);
    }
}
