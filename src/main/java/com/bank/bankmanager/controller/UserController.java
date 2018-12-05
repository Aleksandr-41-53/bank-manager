package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Role;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
// @PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;
    private final InvoiceService invoiceService;

    public UserController(UserService userService, InvoiceService invoiceService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
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

    @GetMapping("{client}")
    public String userInvoices(
            @AuthenticationPrincipal User user,
            @PathVariable User client,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("invoices", invoiceService.getInvoicesByUser(client));
        return "userInvoice";
    }

    @GetMapping("{user}/profile")
    public String userProfile(
            @AuthenticationPrincipal User userAuth,
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("user", userAuth);
        model.addAttribute("client", userService.findByUsername(user.getUsername()));
        return "userProfile";
    }
}
