package com.kodgemisi.parabot.controller;

import com.kodgemisi.parabot.model.Invoice;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import com.kodgemisi.parabot.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by sedat on 27.07.2015.
 */

@RestController
@RequestMapping("/invoices")
public class InvoiceController extends GenericCrudController<Invoice> {

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping("/{id}/transactions")
    public List<MonetaryTransaction> transactions(@PathVariable("id") Long id) {
        return invoiceService.getTransactions(id);
    }

    @RequestMapping(value = "/{id}/transactions", method = RequestMethod.POST)
    public MonetaryTransaction addTransaction(@PathVariable("id") Long id, @Valid @RequestBody MonetaryTransaction transaction) {
        transaction.setTransactionType(MonetaryTransaction.TransactionType.INCOME);
        return invoiceService.addTransaction(id, transaction);
    }
}
