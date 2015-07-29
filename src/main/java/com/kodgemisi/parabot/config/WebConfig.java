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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author destan
 */
@Configuration
@ComponentScan("com.kodgemisi")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:error.properties")
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        CustomRequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());
        handlerMapping.setUseTrailingSlashMatch(false);
        handlerMapping.setUseSuffixPatternMatch(false);

        return handlerMapping;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql:parabotdb");
        dataSource.setUsername( "parabotuser" );
        dataSource.setPassword( "hede" );
        return dataSource;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);

        return transactionManager;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

        sessionBuilder.scanPackages("com.kodgemisi.parabot.model");
        sessionBuilder.addProperties(getHibernateProperties());

        return sessionBuilder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.enable_lazy_load_no_trans", "true");
        return properties;
    }

}
