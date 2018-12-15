package com.bank.bankmanager.controller;

import com.bank.bankmanager.domain.Invoice;
import com.bank.bankmanager.domain.Transaction;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.service.InvoiceService;
import com.bank.bankmanager.service.TransactionService;
import com.bank.bankmanager.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final InvoiceService invoiceService;
    private final UserService userService;

    public TransactionController(
            TransactionService transactionService,
            InvoiceService invoiceService,
            UserService userService
    ) {
        this.transactionService = transactionService;
        this.invoiceService = invoiceService;
        this.userService = userService;
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
        model.addAttribute("title", "Transaction " + client.getUsername());
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
        model.addAttribute("title", "Transactions by account number");
        model.addAttribute("user", user);
        model.addAttribute("senderInvoice", invoiceService.getAllByUser(user));
        model.addAttribute("recipientInvoice", invoiceService.getAll());
        model.addAttribute("debits", transactionService.getAllDebitsByInvoice(invoice));
        model.addAttribute("credits", transactionService.getAllCreditsByInvoice(invoice));
        return "transaction";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public String all(
            @RequestParam(value = "searchUser", defaultValue = "") User searchUser,
            @RequestParam(value = "from", defaultValue = "") Invoice invoiceSender,
            @RequestParam(value = "to", defaultValue = "") Invoice invoiceRecipient,
            @RequestParam(value = "dateOn", defaultValue = "") String dateOn,
            @RequestParam(value = "dateOff", defaultValue = "") String dateOff,
            @AuthenticationPrincipal User user,
            Model model
    ) {
        // Create sender & recipient search data
        List<Long> fromId;
        List<Long> toId;
        if (searchUser != null) {
            fromId = transactionService.getDistinctByInvoiceSenderIdByUserOrderByASC(searchUser);
            toId = transactionService.getDistinctByInvoiceRecipientIdByUserOrderByASC(searchUser);
        } else {
            fromId = transactionService.getDistinctInvoiceSenderAll();
            toId = transactionService.getDistinctInvoiceRecipientAll();
        }

        // Data for form
        model.addAttribute("searchUser", searchUser);
        model.addAttribute("searchFrom", invoiceSender);
        model.addAttribute("searchTo", invoiceRecipient);
        model.addAttribute("dateOn", dateOn);
        model.addAttribute("dateOff", dateOff);

        // body transaction
        model.addAttribute("title", "All Transactions");
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("fromId", fromId);
        model.addAttribute("toId", toId);
        model.addAttribute("transactions", transactionService.searchTransaction(searchUser, invoiceSender, invoiceRecipient, dateOn, dateOff));
        return "admin/transactions";
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
            return "redirect:/transaction?errorCash";
        }
    }
}
