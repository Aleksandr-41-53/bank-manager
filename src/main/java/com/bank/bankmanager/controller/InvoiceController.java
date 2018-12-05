package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

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
        model.addAttribute("user", user);
        model.addAttribute("invoices", invoiceService.getInvoicesByUser(client));
        return "userInvoice";
    }

    @PostMapping("{client}")
    public String addInvoice(
            @PathVariable User client,
            @Valid Invoice invoice,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "userInvoice";
        }

        if (!invoiceService.addInvoice(invoice, client))
            model.addAttribute("error", "Invoice exist!");

        return "userInvoice";
    }
}
