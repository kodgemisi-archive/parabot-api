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

import com.kodgemisi.parabot.model.BaseModel;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;

/**
 * Created by destan on 23.07.2015.
 */
public class GenericDao<T extends BaseModel> {


    @Autowired
    protected SessionFactory sessionFactory;

    final protected Class<T> type;

    /**
     * Sets generic type class for this Dao. By setting this we will be able to
     * call methods that use Criteria without explicit class parameters. <br>
     * <br>
     * Note that this method is not optional! You have to use this.
     *
     * <br>
     * <br>
     *
     * <strong>If not used:</strong> <br>
     * <code>
     * 	session.createCriteria(clazz); // clazz should come as method parameter
     * </code>
     * <br>
     * <br>
     *
     * <strong>If used:</strong> <br>
     * <code>
     * 	session.createCriteria(this.type); // no need for clazz parameter in method
     * </code>
     */
    @SuppressWarnings("unchecked")
    public GenericDao() {
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Long create(final T t) {
        final Session session = sessionFactory.getCurrentSession();

        final Calendar now = Calendar.getInstance();
        t.setUpdateDate(now);

        return (Long) session.save(t);
    }

    @SuppressWarnings("unchecked")
    public T getById(final Long id) {

        final Session session = sessionFactory.getCurrentSession();

        return (T) session.get(type, id);
    }

    public Long update(final T t) {

        final Session session = sessionFactory.getCurrentSession();

        final Calendar now = Calendar.getInstance();
        t.setUpdateDate(now);

        return (Long) session.merge(t);
    }

    public void delete(final T t) {

        final Session session = sessionFactory.getCurrentSession();

        t.setDeleted(true);

        session.merge(t);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {

        final Session session = sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(type);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setFetchMode("*", FetchMode.JOIN);

        return criteria.list();
    }

    public void hardDelete(final T t) {

        final Session session = sessionFactory.getCurrentSession();
        session.delete(t);
    }

    public void delete(Long id, Class clazz) {
        final Session session = sessionFactory.getCurrentSession();
        this.delete((T)session.load(clazz, id));
    }
}
