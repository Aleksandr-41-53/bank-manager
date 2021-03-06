package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Role;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public String userList(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("title", "Users list");
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUser());
        return "admin/users";
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
        model.addAttribute("title", user.getUsername() + " profile");
        if (userService.updateProfile(password, password2, user)) {
            model.addAttribute("success", "User updated.");
            return "userProfile";
        } else {
            model.addAttribute("errorUpdateProfile",
                    "Profile can not be updated! There are errors in filling out the form!");
            return "userProfile";
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{id}/profile")
    public String profileView(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") User userView,
            Model model
    ) {
        model.addAttribute("title", userView.getUsername() + " profile");
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("userView", userView);
        return "admin/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("edit")
    public String edit(
            @RequestParam("id") User user,
            @RequestParam Map<String, String> form
    ) {
        if (userService.edit(user, form)) {
            return "redirect:" + user.getId() + "/profile?save";
        } else {
            return "redirect:" + user.getId() + "/profile?error";
        }
    }

}
