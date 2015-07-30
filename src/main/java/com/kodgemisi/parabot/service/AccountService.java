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
import com.kodgemisi.parabot.dal.InvoiceDao;
import com.kodgemisi.parabot.dal.MonetaryTransactionDao;
import com.kodgemisi.parabot.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by destan on 23.07.2015.
 */
@Service
public class AccountService extends GenericService<Account> {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private MonetaryTransactionDao transactionDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private UserService userService;

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

    public Account getDefaultAccountOfUser() {
        User user = userService.getCurrentUser();

        if(user == null){
            throw new SessionAuthenticationException("User not logged in!");
        }

        return accountDao.getDefaultAccountOfUser(user.getId());
    }

    public Map<String, BigDecimal> getFinancialInfo() {
        BigDecimal totalIncome = transactionDao.totalIncome();
        BigDecimal totalOutgoing = transactionDao.totalOutgoing();

        //TODO: check for the case which tables are empty
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("expected money from debts", transactionDao.expectedMoneyFromDebts().abs());
        map.put("total debt we have to pay", transactionDao.totalDebt());
        map.put("total income", totalIncome);
        map.put("total future income", invoiceDao.totalInvoiceAmount().subtract(totalIncome));
        map.put("total outgoing", totalOutgoing.abs());
        map.put("money in the case", totalIncome.subtract(totalOutgoing));

        return map;
    }
}
