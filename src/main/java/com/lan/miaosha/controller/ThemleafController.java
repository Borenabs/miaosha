package com.lan.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThemleafController {

    @RequestMapping("/themleafTest")
    public String  themleafTest(Model model){
        model.addAttribute("name" , "wakaka");
        return "themleafTest";
    }
}
