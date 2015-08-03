package com.kodgemisi.parabot.service;

import com.kodgemisi.parabot.dal.DebtDao;
import com.kodgemisi.parabot.dal.MonetaryTransactionDao;
import com.kodgemisi.parabot.model.Account;
import com.kodgemisi.parabot.model.Debt;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sedat on 28.07.2015.
 */

@Service
public class DebtService extends GenericService<Debt> {

    @Autowired
    private MonetaryTransactionDao transactionDao;

    @Autowired
    private DebtDao debtDao;

    @Autowired
    private AccountService accountService;

    public Long create(Debt debt) {
        MonetaryTransaction transaction = debt.getDebtTransaction();
        Account account = accountService.getDefaultAccountOfUser();
        transaction.setAccount(account);
        transaction.setTransactionType(MonetaryTransaction.TransactionType.DEBT);
        transactionDao.create(transaction);
        return genericDao.create(debt);
    }

    public List<MonetaryTransaction> getPaybackTransactions(Long id) {
        return debtDao.getPaybackTransactions(id);
    }

    public MonetaryTransaction addTransaction(Long id, MonetaryTransaction transaction) {
        Account account = accountService.getDefaultAccountOfUser();
        transaction.setAccount(account);

        transactionDao.create(transaction);
        Debt debt = debtDao.getById(id);
        debt.getPaybacks().add(transaction);
        return transaction;
    }
}
