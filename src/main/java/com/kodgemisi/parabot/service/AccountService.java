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

import com.kodgemisi.parabot.dal.AccountDao;
import com.kodgemisi.parabot.dal.AgentDao;
import com.kodgemisi.parabot.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by destan on 23.07.2015.
 */
@Service
public class AccountService extends GenericService<Account> {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AgentDao agentDao;

    public List<Agent> getAgents(Long id) {
        return accountDao.getAgents(id);
    }

    public Agent addAgent(Long id, Agent agent) {
        agentDao.create(agent);
        Account account = accountDao.getById(id);
        agent.setOwnerAccount(account);
        return agent;
    }

    public List<Commercial> getCommercials(Long id) {
        return accountDao.getAgents(id, Commercial.class);
    }

    public List<Client> getClients(Long id) {
        return accountDao.getAgents(id, Client.class);
    }

    public List<Employee> getEmployees(Long id) {
        return accountDao.getAgents(id, Employee.class);
    }
}
