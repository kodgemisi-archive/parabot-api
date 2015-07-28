package com.kodgemisi.parabot.dal;

import com.kodgemisi.parabot.model.Invoice;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sedat on 24.07.2015.
 */

@Repository
public class InvoiceDao extends GenericDao<Invoice> {

    public List<MonetaryTransaction> getTransactions(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(MonetaryTransaction.class);
        c.add(Restrictions.eq("invoice.id", id));
        return c.list();
    }
}
