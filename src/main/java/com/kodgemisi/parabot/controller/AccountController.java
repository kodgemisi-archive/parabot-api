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

package com.kodgemisi.parabot.controller;

import com.kodgemisi.parabot.model.*;
import com.kodgemisi.parabot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController extends GenericCrudController<Account>{

    @Autowired
    private AccountService accountService;

    @RequestMapping("/{id}/agents")
    public List<Agent> agents(@PathVariable("id") Long id){
        return accountService.getAgents(id);
    }

    @RequestMapping(value = "/{id}/commercials", method = RequestMethod.POST)
    public Agent addCommercial(@PathVariable("id") Long id, Commercial agent){
        return accountService.addAgent(id, agent);
    }

    @RequestMapping(value = "/{id}/clients", method = RequestMethod.POST)
    public Agent addClient(@PathVariable("id") Long id, Client agent){
        return accountService.addAgent(id, agent);
    }

    @RequestMapping(value = "/{id}/employees", method = RequestMethod.POST)
    public Agent addEmployee(@PathVariable("id") Long id, Employee agent){
        return accountService.addAgent(id, agent);
    }

    @RequestMapping(value = "/{id}/commercials")
    public List<Commercial> getCommercials(@PathVariable("id") Long id){
        return accountService.getCommercials(id);
    }

    @RequestMapping(value = "/{id}/clients")
    public List<Client> getClients(@PathVariable("id") Long id){
        return accountService.getClients(id);
    }

    @RequestMapping(value = "/{id}/employees")
    public List<Employee> getEmployees(@PathVariable("id") Long id){
        return accountService.getEmployees(id);
    }

}
