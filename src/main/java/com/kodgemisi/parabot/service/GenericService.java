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

package com.kodgemisi.parabot.service;

import com.kodgemisi.parabot.dal.GenericDaoImpl;
import com.kodgemisi.parabot.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GenericService<T extends BaseModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	protected GenericDaoImpl<T> genericDao;

	public Long create(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		return genericDao.create(t);
	}

	public T getById(final Long id) {

		if (id == null) {
			throw new RuntimeException("Id cannot be null");
		}

		return genericDao.getById(id);
	}

	public Long update(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		return genericDao.update(t);
	}

	public void delete(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		genericDao.delete(t);
	}

	public void delete(final Long id) {

		if (id == null) {
			throw new RuntimeException("id cannot be null");
		}

		genericDao.delete(this.getById(id));
	}

	public List<T> getAll() {

		return genericDao.getAll();
	}

	public void hardDelete(final T t) {

		if (t == null) {
			throw new RuntimeException("Model cannot be null");
		}

		genericDao.delete(t);
	}
}