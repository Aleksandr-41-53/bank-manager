package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import com.bank.bankmanager.service.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final InvoiceService invoiceService;

    public TransactionController(TransactionService transactionService, InvoiceService invoiceService) {
        this.transactionService = transactionService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String index(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        return getTransactionModel(user, user, model);
    }

    @GetMapping("{client}/user")
    public String clientTransaction(
            @AuthenticationPrincipal User user,
            @PathVariable User client,
            Model model
    ) {
        return getTransactionModel(user, client, model);
    }

    private String getTransactionModel(@AuthenticationPrincipal User user, @PathVariable User client, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("senderInvoice", invoiceService.getAllByUser(client));
        model.addAttribute("recipientInvoice", invoiceService.getAll());
        model.addAttribute("debits", transactionService.getAllDebitsByUser(client));
        model.addAttribute("credits", transactionService.getAllCreditsByUser(client));
        return "transaction";
    }

    @GetMapping("{invoice}/invoice")
    public String invoiceTransaction(
            @AuthenticationPrincipal User user,
            @PathVariable Invoice invoice,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("senderInvoice", invoiceService.getAllByUser(user));
        model.addAttribute("recipientInvoice", invoiceService.getAll());
        model.addAttribute("debits", transactionService.getAllDebitsByInvoice(invoice));
        model.addAttribute("credits", transactionService.getAllCreditsByInvoice(invoice));
        return "transaction";
    }

    @GetMapping("all")
    public String all(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactionService.getAll());
        return "transaction";
    }

    // TODO:
    @PostMapping
    public String add(
            @RequestParam("cash") BigDecimal cash,
            @RequestParam("sender") Invoice sender,
            @RequestParam("recipient") Invoice recipient
            ) {
        if (transactionService.add(cash, sender, recipient)) {
            return "redirect:/transaction";
        } else {
            return "redirect:/transaction/user?errorCash";
        }
    }
}
