package com.customer.data.spark.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.customer.data.function.CustomerDataCleansingFunction;
import com.customer.data.function.SourceRowToCustomerDataFunction;
import com.customer.data.helper.SparkHelper;
import com.customer.data.model.CustomerData;

import scala.Tuple2;

@Named
public class CustomerDataSparkJobImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(CustomerDataSparkJobImpl.class);

	private SparkHelper sparkHelper;

	private String appName;

	private static final String FILE_TYPE_CSV = "csv";

	private static final String HEADER = "header";

	private static final String DELIMITER = "delimiter";

	@Autowired
	private CustomerDataCleansingFunction customerDataCleansingFunction;

	@Autowired
	private SourceRowToCustomerDataFunction sourceRowToCustomerDataFunction;

	@Value("${sample.property.name}")
	private String samplePropertyName;

	@Value("${cust.data.file.path}")
	private String customerDataFilePath;
	
	@Value("${cust.usage.file.name}")
	private String usageOutputFileName;

	@Inject
	public CustomerDataSparkJobImpl(@Value("${app.name}") String appName, SparkHelper sparkHelper) {
		this.sparkHelper = sparkHelper;
		this.appName = appName;
	}

	public void execute() {

		logger.info("Customer data analysis app execution initiated...");
		logger.info("Customer data file path is: " + customerDataFilePath);

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
		logger.info("Execution started.....");
		// Create spark dataframe reader
		DataFrameReader sparkDataDrameReader = sparkSession.read().format(FILE_TYPE_CSV).option(HEADER, true)
				.option(DELIMITER, ",");

		// Get CustomerData java RDD
		JavaRDD<Row> cleanedCustomerRDD = getCustomerDataRDD(sparkDataDrameReader);

		//TODO - remove this print line
		logger.info("Count of cleanedCustomerRDD: " + cleanedCustomerRDD.count());

		JavaRDD<CustomerData> customerDataRDD = cleanedCustomerRDD.map(sourceRowToCustomerDataFunction);

		JavaPairRDD<String, Integer> usageByPhoneNum = customerDataRDD.mapToPair(
				t -> new Tuple2<String, Integer>(t.getPhoneNumber(), Integer.parseInt(t.getUsageInSessionMB())));

		JavaPairRDD<String, Integer> usageAccumulated = usageByPhoneNum.reduceByKey((a, b) -> a + b);

		//TODO - remove this print line
		usageAccumulated.foreach(t -> logger.info("Phone number: " + t._1 + ", Total usage: " + t._2));
		logger.info("Output file location: "+usageOutputFileName);
		usageAccumulated.coalesce(1).saveAsTextFile(usageOutputFileName);
		logger.info("Execution ended!!!!!");
	}

	private JavaRDD<Row> getCustomerDataRDD(DataFrameReader sparkDataDrameReader) {
		return sparkDataDrameReader.load(customerDataFilePath)
				.select("col0", "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10",
						"col11", "col12", "col13", "col14", "col15", "col16", "col17", "col18", "col19", "col20",
						"col21", "col22", "col23", "col24", "col25", "col26", "col27")
				.toJavaRDD().map(customerDataCleansingFunction);

		/**
		 * The above step can be done in below step by step way for better understanding
		 */
		/*
		 * Dataset<Row> customerDataSetRaw =
		 * sparkDataDrameReader.load(customerDataFilePath);
		 * 
		 * 
		 * Dataset<Row> customerDataSetSelected = customerDataSetRaw.select("col0",
		 * "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9",
		 * "col10", "col11", "col12", "col13", "col14", "col15", "col16", "col17",
		 * "col18", "col19", "col20", "col21", "col22", "col23", "col24", "col25",
		 * "col26", "col27");
		 * 
		 * 
		 * JavaRDD<Row> customerJavaRDD = customerDataSetSelected.toJavaRDD();
		 * 
		 * JavaRDD<Row> cleanedCustomerRDD =
		 * customerJavaRDD.map(customerDataCleansingFunction);
		 * 
		 * return cleanedCustomerRDD;
		 * 
		 */
	}

	private static List<StructField> getCustomerdataSchema() {
		List<StructField> fields = new ArrayList<>();
		fields.add(DataTypes.createStructField("SESSION_ID", DataTypes.StringType, false));
		fields.add(DataTypes.createStructField("PHONE_NO", DataTypes.StringType, true));
		fields.add(DataTypes.createStructField("USAGE_IN_MB", DataTypes.StringType, true));
		return fields;
	}

}
