package com.kodgemisi.parabot.dal;

import com.kodgemisi.parabot.model.Debt;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sedat on 24.07.2015.
 */

@Repository
public class DebtDao extends GenericDao<Debt> {

    public List<MonetaryTransaction> getPaybackTransactions(Long debtId) {
        Criteria c = createCriteria();
        c.add(Restrictions.idEq(debtId));
        c.setFetchMode("paybacks", FetchMode.JOIN);

        Debt debt = (Debt) c.uniqueResult();

        return new ArrayList<>(debt.getPaybacks());
    }
}