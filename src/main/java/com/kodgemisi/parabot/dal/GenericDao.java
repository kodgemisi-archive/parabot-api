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

import java.util.List;

/**
 * Created by destan on 23.07.2015.
 */
public interface GenericDao<T extends BaseModel> {
    public Long create(final T t);

    public T getById(final Long id);

    public Long update(final T t);

    public void delete(final T t);

    void delete(Long id, Class clazz);

    public List<T> getAll();

    public void hardDelete(final T t);
}
