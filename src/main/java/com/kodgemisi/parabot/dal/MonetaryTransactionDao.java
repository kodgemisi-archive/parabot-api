/*
 * Copyright (C) 7, 2015 Kod Gemisi Ltd.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kodgemisi.parabot.dal;

import com.kodgemisi.parabot.model.MonetaryTransaction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by sedat on 24.07.2015.
 */

@Repository
public class MonetaryTransactionDao extends GenericDao<MonetaryTransaction> {
    BigDecimal zero = new BigDecimal(0);
    private final static String AMOUNT = "amount";
    private final static String TRANSACTION_TYPE = "transactionType";

    public BigDecimal expectedMoneyFromDebts() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.sum(AMOUNT));

        Criterion transactionTypeDebt = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.DEBT);
        Criterion transactionTypeAmountGiven = Restrictions.lt(AMOUNT, zero);
        LogicalExpression givenDebt = Restrictions.and(transactionTypeDebt, transactionTypeAmountGiven);

        Criterion transactionTypePayback = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.PAYBACK);
        Criterion transactionTypeAmountTaken = Restrictions.gt(AMOUNT, zero);
        LogicalExpression takenPayback = Restrictions.and(transactionTypePayback, transactionTypeAmountTaken);

        criteria.add(Restrictions.or(givenDebt, takenPayback));

        //SELECT sum(amount) FROM transaction WHERE (type=debt AND amount<0) OR (type=payback AND amount>0)

        return (BigDecimal) criteria.uniqueResult();
    }

    public BigDecimal totalDebt() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.sum(AMOUNT));

        Criterion transactionTypeDebt = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.DEBT);
        Criterion transactionTypeAmountTaken = Restrictions.gt(AMOUNT, zero);
        LogicalExpression takenDebt = Restrictions.and(transactionTypeDebt, transactionTypeAmountTaken);

        Criterion transactionTypePayback = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.PAYBACK);
        Criterion transactionTypeAmountGiven = Restrictions.lt(AMOUNT, zero);
        LogicalExpression givenPayback = Restrictions.and(transactionTypePayback, transactionTypeAmountGiven);

        criteria.add(Restrictions.or(takenDebt, givenPayback));

        return (BigDecimal) criteria.uniqueResult();
    }

    public BigDecimal totalIncome() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.sum(AMOUNT));

        Criterion incomeFromInvoices = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.INCOME);
        Criterion dirtyMoney = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.FILTHY_LUCRE);

        criteria.add(Restrictions.or(incomeFromInvoices, dirtyMoney));

        return (BigDecimal) criteria.uniqueResult();
    }

    public BigDecimal totalOutgoing() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.sum(AMOUNT));

        Criterion wage = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.WAGE);
        Criterion rent = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.RENT);
        Criterion common = Restrictions.eq(TRANSACTION_TYPE, MonetaryTransaction.TransactionType.COMMON);

        criteria.add(Restrictions.or(wage, rent, common));

        return (BigDecimal) criteria.uniqueResult();
    }
}
