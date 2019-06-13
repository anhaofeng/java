package com.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class a {
    @GetMapping(value = "/login")
    String login(){
    return "Dashboard";
    }
}
