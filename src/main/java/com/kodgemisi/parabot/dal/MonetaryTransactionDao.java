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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by sedat on 24.07.2015.
 */

@Repository
public class MonetaryTransactionDao extends GenericDao<MonetaryTransaction> {
    public BigDecimal expectedMoneyFromDebts() {
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(MonetaryTransaction.class);

        criteria.setProjection(Projections.sum("amount"));

        Criterion transactionTypeDebt = Restrictions.eq("transactionType", MonetaryTransaction.TransactionType.DEBT);
        Criterion transactionTypeAmountGiven = Restrictions.lt("amount", new BigDecimal(0));
        LogicalExpression givenDebt = Restrictions.and(transactionTypeDebt, transactionTypeAmountGiven);

        Criterion transactionTypePayback = Restrictions.eq("transactionType", MonetaryTransaction.TransactionType.PAYBACK);
        Criterion transactionTypeAmountTaken = Restrictions.gt("amount", new BigDecimal(0));
        LogicalExpression takenPayback = Restrictions.and(transactionTypePayback, transactionTypeAmountTaken);

        criteria.add(Restrictions.or(givenDebt, takenPayback));


        //SELECT sum(amount) FROM transaction WHERE (type=debt AND amount<0) OR (type=payback AND amount>0)

        return (BigDecimal) criteria.uniqueResult();
    }
}
