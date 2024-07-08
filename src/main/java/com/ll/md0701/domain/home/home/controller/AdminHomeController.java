package com.ll.md0701.domain.home.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdminHomeController {
    @GetMapping("")
    public String showMain() {
        return "templates/domain/home/home/adm/main";
    }

    @GetMapping("/home/about")
    public String showAbout() {
        return "templates/domain/home/home/adm/about";
    }
}