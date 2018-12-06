package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final UserService userService;

    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
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
            @RequestParam("order1") String order1,
            @RequestParam("order2") String order2,
            @RequestParam("order3") String order3,
            @RequestParam("order4") String order4,
            @RequestParam("order5") String order5,
            @Valid Invoice invoice,
            BindingResult bindingResult,
            Model model
    ) {
        String number = order1 + order2 + order3 + order4 + order5;

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "redirect:/invoice/" + user.getId();
        }

        if (!invoiceService.addInvoice(invoice, number, user))
            model.addAttribute("error", "Invoice exist!");

        return "redirect:/invoice/" + user.getId();
    }
}
