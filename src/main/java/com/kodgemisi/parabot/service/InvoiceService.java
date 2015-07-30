package com.kodgemisi.parabot.service;

import com.kodgemisi.parabot.dal.InvoiceDao;
import com.kodgemisi.parabot.dal.MonetaryTransactionDao;
import com.kodgemisi.parabot.model.Account;
import com.kodgemisi.parabot.model.Agent;
import com.kodgemisi.parabot.model.Invoice;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sedat on 27.07.2015.
 */

@Service
public class InvoiceService extends GenericService<Invoice> {
    @Autowired
    private MonetaryTransactionDao transactionDao;

    @Autowired
    private InvoiceDao invoiceDao;

    public List<MonetaryTransaction> getTransactions(Long id) {
        return invoiceDao.getTransactions(id);
    }

    public MonetaryTransaction addTransaction(Long id, MonetaryTransaction transaction) {
        transaction.setTransactionType(MonetaryTransaction.TransactionType.INCOME);
        transactionDao.create(transaction);
        Invoice invoice = invoiceDao.getById(id);
        invoice.getTransactions().add(transaction);
        return transaction;
    }
}
