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

package com.kodgemisi.parabot.config;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.method.HandlerMethodSelector;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by destan on 29.07.2015.
 */
@Component
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected void detectHandlerMethods(final Object handler) {
        Class<?> handlerType =
                (handler instanceof String ? getApplicationContext().getType((String) handler) : handler.getClass());

        // Avoid repeated calls to getMappingForMethod which would rebuild RequestMappingInfo instances
        final Map<Method, RequestMappingInfo> mappings = new IdentityHashMap<Method, RequestMappingInfo>();
        final Class<?> userType = ClassUtils.getUserClass(handlerType);

        final Map<RequestMappingInfo, Method> handlerMethods = new LinkedHashMap<RequestMappingInfo, Method>();
        final List<Method> toBeDeleted = new ArrayList<>();

        Set<Method> methods = HandlerMethodSelector.selectMethods(userType, new MethodFilter() {
            @Override
            public boolean matches(Method method) {
                RequestMappingInfo mapping = getMappingForMethod(method, userType);
                if (mapping != null) {
                    mappings.put(method, mapping);

                    if (mapping != null) {
                        mappings.put(method, mapping);

                        if (handlerMethods.get(mapping) != null) {
                            Method existingMethod = handlerMethods.get(mapping);

                            int existingMethodsOrderValue = 0;
                            int currentMethodsOrderValue = 0;

                            Order existingMethodsOrder = AnnotationUtils.findAnnotation(existingMethod, Order.class);
                            if (existingMethodsOrder != null) {
                                existingMethodsOrderValue = existingMethodsOrder.value();
                            }

                            Order currentMethodsOrder = AnnotationUtils.findAnnotation(method, Order.class);
                            if (currentMethodsOrder != null) {
                                currentMethodsOrderValue = currentMethodsOrder.value();
                            }

                            // override method if current order is higher
                            if (currentMethodsOrderValue < existingMethodsOrderValue) {
                                toBeDeleted.add(existingMethod);// mark previous method as to-be-deleted
                                handlerMethods.put(mapping, method);
                            } else {
                                toBeDeleted.add(method);
                            }
                        } else {
                            handlerMethods.put(mapping, method);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });

        methods.removeAll(toBeDeleted);

        for (Method method : methods) {
            registerHandlerMethod(handler, method, mappings.get(method));
        }
    }

}
