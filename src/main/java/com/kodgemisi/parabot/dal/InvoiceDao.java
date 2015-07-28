package com.kodgemisi.parabot.dal;

import com.kodgemisi.parabot.model.Debt;
import com.kodgemisi.parabot.model.Invoice;
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
public class InvoiceDao extends GenericDao<Invoice> {

    public List<MonetaryTransaction> getTransactions(Long invoiceId) {
        Criteria c = createCriteria();
        c.add(Restrictions.idEq(invoiceId));
        c.setFetchMode("paybacks", FetchMode.JOIN);

        Invoice invoice = (Invoice) c.uniqueResult();

        return new ArrayList<>(invoice.getTransactions());
    }
}
