package com.pain.flame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/10/10.
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String index1(Model model) {
        model.addAttribute("name", "flame");
        return "index";
    }

    @GetMapping("/index")
    @ResponseBody
    public String index1() {
        throw new RuntimeException("hello exception");
    }
}
