package com.kodgemisi.parabot.controller;

import com.kodgemisi.parabot.model.Debt;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import com.kodgemisi.parabot.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sedat on 28.07.2015.
 */

@RestController
@RequestMapping("/debts")
public class DebtController extends GenericCrudController<Debt> {

    @Autowired
    private DebtService debtService;

    @RequestMapping("/{id}/transactions/paybacks")
    private List<MonetaryTransaction> transactions(@PathVariable("id") Long id) {
        return debtService.getPaybackTransactions(id);
    }

    @RequestMapping(value = "/{id}/transactions", method = RequestMethod.POST)
    private MonetaryTransaction addTransaction(@PathVariable("id") Long id, MonetaryTransaction transaction) {
        return debtService.addTransaction(id, transaction);
    }
}