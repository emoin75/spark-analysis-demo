package com.customer.data.spark.impl;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.customer.data.helper.SparkHelper;

@Named
public class CustomerDataSparkJobImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(CustomerDataSparkJobImpl.class);

	private SparkHelper sparkHelper;

	private String appName;

	private static final String FILE_TYPE_CSV = "csv";

	private static final String HEADER = "header";

	private static final String DELIMITER = "delimiter";

	@Value("${sample.property.name}")
	String samplePropertyName;

	@Inject
	public CustomerDataSparkJobImpl(@Value("${app.name}") String appName, SparkHelper sparkHelper) {
		this.sparkHelper = sparkHelper;
		this.appName = appName;
	}

	public void execute() {

		logger.info("Customer data analysis app execution initiated...");

		// initiate spark session
		SparkSession sparkSession = sparkHelper.instantiateSession(appName);
		try {
			processData(sparkSession);
		} catch (Exception e) {
			logger.error("Error in processing migration app.", e);
		} finally {
			// clear up spark related commands
			sparkHelper.closeSession(sparkSession);
			logger.info("Customer data analysis app job completed.");
			System.exit(0);
		}
	}

	private void processData(SparkSession sparkSession) {
		DataFrameReader sparkDataDrameReader = sparkSession.read().format(FILE_TYPE_CSV).option(HEADER, true)
				.option(DELIMITER, ",");

		logger.info("Executing.....");
	}
	
//	private JavaRDD<Row> getCustDataRDD(DataFrameReader sparkDataDrameReader){
//		
//	}
}
