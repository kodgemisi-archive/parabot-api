package com.kodgemisi.parabot.controller;

import com.kodgemisi.parabot.model.Debt;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import com.kodgemisi.parabot.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by sedat on 28.07.2015.
 */

@RestController
@RequestMapping("/debts")
public class DebtController extends GenericCrudController<Debt> {

    @Autowired
    private DebtService debtService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Debt create(@RequestBody @Valid Debt debt, BindingResult bindingResult){

        // FIXME this is for development, should be refactored!
        MonetaryTransaction monetaryTransaction = debt.getDebtTransaction() == null ? new MonetaryTransaction() : debt.getDebtTransaction();

        debtService.create(debt);
        return debt;
    }

//    @ResponseBody
//    @RequestMapping(method = RequestMethod.POST)
//    @OverrideRequestMapping
//    public Debt create(@RequestBody @Valid Debt debt, BindingResult bindingResult, Authentication authentication){
//
//        // FIXME this is for development, should be refactored!
//        MonetaryTransaction monetaryTransaction = debt.getDebtTransaction() == null ? new MonetaryTransaction() : debt.getDebtTransaction();
//
//        debtService.create(debt, monetaryTransaction);
//        return debt;
//    }

    @RequestMapping("/{id}/transactions/paybacks")
    public List<MonetaryTransaction> transactions(@PathVariable("id") Long id) {
        return debtService.getPaybackTransactions(id);
    }

    @RequestMapping(value = "/{id}/transactions", method = RequestMethod.POST)
    public MonetaryTransaction addTransaction(@PathVariable("id") Long id, @RequestBody MonetaryTransaction transaction) {
        transaction.setTransactionType(MonetaryTransaction.TransactionType.PAYBACK);
        return debtService.addTransaction(id, transaction);
    }
}