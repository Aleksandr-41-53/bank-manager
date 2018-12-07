package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import com.bank.bankmanager.service.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final InvoiceService invoiceService;

    public TransactionController(TransactionService transactionService, InvoiceService invoiceService) {
        this.transactionService = transactionService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("{clientId}/user")
    public String clientTransaction(
            @AuthenticationPrincipal User user,
            @PathVariable Long clientId,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("client", clientId);
        model.addAttribute("senderInvoice", invoiceService.getInvoicesByUser(user));
        model.addAttribute("recipientInvoice", invoiceService.getAll());
        model.addAttribute("transactions", transactionService.getAll());
        return "transaction";
    }

    // TODO:
    @PostMapping
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam("cash") BigDecimal cash,
            @RequestParam("sender") Invoice sender,
            @RequestParam("recipient") Invoice recipient
            ) {
        transactionService.add(cash, sender, recipient);
        return "redirect:/transaction/" + user.getId() + "/user";
    }
}
