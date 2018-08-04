package com.customer.data.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * Instantiate spring context
 *
 * Application code implementing the business logic will be spring managed beans. Spark job will be
 * main thread instantiating the business logic wrapped in the spring bean
 *
 */
public class SpringContextFactory {

    private static final SpringContextFactory INSTANCE = new SpringContextFactory();

    private final ApplicationContext applicationContext;

    private SpringContextFactory() {
        this.applicationContext = new AnnotationConfigApplicationContext(CustomerDataSpringConfig.class);
    }

    public ApplicationContext getContext() {
        return applicationContext;
    }

    public static SpringContextFactory getInstance() {
        return INSTANCE;
    }

}


