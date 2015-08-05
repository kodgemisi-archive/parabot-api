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

package com.kodgemisi.parabot.controller.aspect;

import com.kodgemisi.parabot.config.CustomAuthenticationSuccessHandler;
import com.kodgemisi.parabot.model.Account;
import com.kodgemisi.parabot.model.Agent;
import com.kodgemisi.parabot.model.MonetaryTransaction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class AddAccountToNewModelsAspect {

    @Before(value = "execution(public * create(..) ) && args(com.kodgemisi.parabot.model.BaseModel+)")
    public void pushClientData(JoinPoint joinPoint) {

        Account account = getCurrentAccount();
        if(account == null){
            return;
        }

        Object model = joinPoint.getArgs()[0];

        if(model instanceof MonetaryTransaction){
            MonetaryTransaction monetaryTransaction = (MonetaryTransaction) model;
            monetaryTransaction.setAccount(account);
        }
        else if(model instanceof Agent){
            Agent agent = (Agent) model;
            agent.setOwnerAccount(account);
        }

    }

    private Account getCurrentAccount() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        Account account = (Account) session.getAttribute(CustomAuthenticationSuccessHandler.CURRENT_ACCOUNT_SESSION_KEY);
        return account;
    }

}