package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public String userList(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("title", "Users list");
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUser());
        return "user";
    }

    @GetMapping("profile")
    public String profile(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("title", user.getUsername() + " profile");
        model.addAttribute("user", user);
        return "userProfile";
    }

    @PostMapping("profile")
    public String userProfile(
            @AuthenticationPrincipal User user,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            Model model
    ) {
        if (userService.updateProfile(password, password2, user)) {
            model.addAttribute("success", "User updated.");
            return "userProfile";
        } else {
            model.addAttribute("errorUpdateProfile",
                    "Profile can not be updated! There are errors in filling out the form!");
            return "userProfile";
        }

    }
}
