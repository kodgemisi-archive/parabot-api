package com.kodgemisi.parabot.dal;

import com.kodgemisi.parabot.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by sedat on 24.07.2015.
 */

@Repository
public class UserDao extends GenericDao<User> {

    public User findByUsername(String username){
        Criteria c = createCriteria();
        c.add(Restrictions.eq("username", username));
        return (User) c.uniqueResult();
    }
}
