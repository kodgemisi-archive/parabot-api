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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by destan on 23.07.2015.
 */
@Entity
public class Debt extends BaseModel {
    private String name;
    private String desc;
    private Calendar dueDate;

    @ManyToOne
    private Agent agent;

    @OneToOne
    private MonetaryTransaction debtTransaction;

    @OneToMany
    private Set<MonetaryTransaction> paybacks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public MonetaryTransaction getDebtTransaction() {
        return debtTransaction;
    }

    public void setDebtTransaction(MonetaryTransaction debtTransaction) {
        this.debtTransaction = debtTransaction;
    }

    public Set<MonetaryTransaction> getPaybacks() {
        return paybacks;
    }

    public void setPaybacks(Set<MonetaryTransaction> paybacks) {
        this.paybacks = paybacks;
    }
}
