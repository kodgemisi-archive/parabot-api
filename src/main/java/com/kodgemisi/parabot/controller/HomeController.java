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

import com.kodgemisi.parabot.model.User;
import com.kodgemisi.parabot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author destan
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/test")
    public User test(){

        Map<String,String> result = new HashMap<String, String>();
        result.put("here", "zaa");
        logger.info("This is a log");

        User u = new User();
        u.setUsername("xxx");

        userService.create(u);

        return u;
    }

    @ResponseBody
    @RequestMapping("/test2/{id}")
    public User test2(@PathVariable("id") String id){
        User u = userService.getById(Long.parseLong(id));
        u.setAccounts(null);
        return u;
    }
}
