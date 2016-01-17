package com.aquent.crudapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Sign in page
 */
@Controller
public class HomeController {

    /**
     * Sign in!
     *
     * @return Sign In Page
     */
    @RequestMapping
    public String index() {
        return "redirect:/user/signin";
    }

}
