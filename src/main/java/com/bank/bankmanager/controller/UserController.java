package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUser());
        return "user";
    }

    @GetMapping("{user}/profile")
    public String userProfile(
            @AuthenticationPrincipal User userAuth,
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("user", userAuth);
        model.addAttribute("client", userService.getByUsername(user.getUsername()));
        return "userProfile";
    }
}
