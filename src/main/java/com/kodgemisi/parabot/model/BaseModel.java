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

package com.kodgemisi.parabot.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by destan on 23.07.2015.
 */
@MappedSuperclass
public abstract class BaseModel implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private Calendar creationDate;
    private Calendar updateDate;
    private Calendar deletionDate;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public Calendar getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Calendar deletionDate) {
        this.deletionDate = deletionDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BaseModel() {
        creationDate = Calendar.getInstance();
        deleted = false;

    }
}
