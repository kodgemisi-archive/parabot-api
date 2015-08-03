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

import com.kodgemisi.parabot.model.BaseModel;
import com.kodgemisi.parabot.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by destan on 24.07.2015.
 */
public abstract class GenericCrudController <T extends BaseModel> {

    @Autowired
    private GenericService<T> genericService;

    protected Logger logger;

    public GenericCrudController() {
        Class<?> type = this.getClass().getSuperclass();
        logger = LoggerFactory.getLogger(type);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @Order(Ordered.LOWEST_PRECEDENCE)
    protected T create(@RequestBody T t){ //TODO: when validation added, check aspect
        genericService.create(t);
        return t;
    }

    @ResponseBody
    @RequestMapping("/{id}")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public T read(@PathVariable("id") Long id){
        return genericService.getById(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public T update(@RequestBody T t){
        genericService.update(t);
        return t;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void delete(@PathVariable("id") Long id){
        genericService.delete(id);
    }

    @ResponseBody
    @RequestMapping
    @Order(Ordered.LOWEST_PRECEDENCE)
    public List<T> list(){
        return genericService.getAll();
    }

//    @ResponseBody
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void hardDelete(@PathVariable("id") Long id){
//        genericService.hardDelete(id);
//    }

}
