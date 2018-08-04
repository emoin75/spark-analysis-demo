package com.customer.data.spark.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customer.data.config.SpringContextFactory;


public class CustomerDataAnalysisSparkJob {

    private static Logger logger = LoggerFactory.getLogger(CustomerDataAnalysisSparkJob.class);

    private CustomerDataAnalysisSparkJob() { }

    public static void main(String[] args) {
        CustomerDataSparkJobImpl utility = SpringContextFactory.getInstance().
                getContext().getBean(CustomerDataSparkJobImpl.class);
        utility.execute();
    }

}

