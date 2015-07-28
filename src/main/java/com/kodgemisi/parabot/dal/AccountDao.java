package com.kodgemisi.parabot.dal;

import com.kodgemisi.parabot.model.Account;
import com.kodgemisi.parabot.model.Agent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sedat on 24.07.2015.
 */

@Repository
public class AccountDao extends GenericDao<Account> {

    public List<Agent> getAgents(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Agent.class);
        c.add(Restrictions.eq("ownerAccount.id", id));
        return c.list();
    }

    public List getAgents(Long id, Class<? extends Agent> type) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(type);
        c.add(Restrictions.eq("ownerAccount.id", id));
        return c.list();
    }

    public Account getDefaultAccountOfUser(Long id) {
        Criteria c = createCriteria();
        c.createAlias("users", "u");
        c.add(Restrictions.eq("u.id", id));

        List<Account> accounts = c.list();

        return accounts.size() > 0 ? accounts.get(0) : null;
    }
}
