package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("{client}")
    public String userInvoices(
            @AuthenticationPrincipal User user,
            @PathVariable User client,
            Model model
    ) {
        Random random = new Random();

        model.addAttribute("user", user);
        model.addAttribute("clientId", client.getId());
        model.addAttribute("rn3", random.nextInt(10));
        model.addAttribute("rn5", random.nextInt(9000000) + 1000000);
        model.addAttribute("invoices", invoiceService.getInvoicesByUser(client));
        return "userInvoice";
    }

    @PostMapping
    public String addInvoice(
            @RequestParam("client") User user,
            @RequestParam Map<String, String> form
    ) {
        invoiceService.addInvoice(user, form);
        return "redirect:/invoice/" + user.getId();
    }
}
