package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String index(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        return getUserInvoiceModel(user, user, model);
    }

    @GetMapping("{client}/user")
    public String userInvoices(
            @AuthenticationPrincipal User user,
            @PathVariable User client,
            Model model
    ) {
        return getUserInvoiceModel(user, client, model);
    }

    private String getUserInvoiceModel(@AuthenticationPrincipal User user, @PathVariable User client, Model model) {
        model.addAttribute("title", "Invoices " + client.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("clientId", client.getId());
        model.addAttribute("rn3", invoiceService.getGenerateNumberFor3Order());
        model.addAttribute("rn5", invoiceService.getGenerateNumberFor5Order());
        model.addAttribute("invoices", invoiceService.getAllByUser(client));
        return "userInvoice";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public String all(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("title", "List Invoices");
        model.addAttribute("invoices", invoiceService.getAll());
        return "userInvoice";
    }

    @PostMapping
    public String addInvoice(
            @RequestParam("client") User user,
            @RequestParam Map<String, String> form
    ) {
        if (invoiceService.addInvoice(user, form)) {
            return "redirect:/invoice";
        } else {
            return "redirect:/invoice?errorAdd";
        }
    }
}
