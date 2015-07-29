package com.kodgemisi.parabot.controller;

import com.kodgemisi.parabot.model.Invoice;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import com.kodgemisi.parabot.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public MonetaryTransaction addTransaction(@PathVariable("id") Long id, MonetaryTransaction transaction) {
        return invoiceService.addTransaction(id, transaction);
    }
}
