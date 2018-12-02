package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class MainController {
    @Value("${app.name}")
    private String appName;

    @GetMapping("/")
    public String index(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("title", appName);
        model.addAttribute("user", user);
        return "index";
    }
}
