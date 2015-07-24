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

import com.kodgemisi.parabot.model.Account;
import com.kodgemisi.parabot.model.User;
import com.kodgemisi.parabot.service.AccountService;
import com.kodgemisi.parabot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by destan on 23.07.2015.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Account createAccount() {
        Map<String,String> result = new HashMap<String, String>();
        result.put("here", "zaa");
        logger.info("This is a log");

        Account acc = new Account();

        acc.setName("kodgemisi");
        accountService.create(acc);

        return acc;
    }

    @ResponseBody
    @RequestMapping("/{id}")
    public Account seeAccount(@PathVariable Long id) {
        Account acc = accountService.getById(id);
        Set<User> users = new HashSet<>();
        users.add(userService.getById(1L));
        acc.setUsers(users);
        return acc;
    }

    @ResponseBody
    @RequestMapping
    public List<Account> getAllAccounts() {
        return accountService.getAll();
    }
}
