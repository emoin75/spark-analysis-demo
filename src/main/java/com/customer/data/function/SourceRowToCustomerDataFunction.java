package com.customer.data.function;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customer.data.model.CustomerData;

@Named
public class SourceRowToCustomerDataFunction implements Function<Row, CustomerData>, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(SourceRowToCustomerDataFunction.class);

	@Override
	public CustomerData call(Row row) throws Exception {

		CustomerData customerData = new CustomerData();

		customerData.setSessionId(row.getAs(0));
		customerData.setPhoneNumber(row.getString(1));
		customerData.setUsageInSessionMB(row.getAs(4));
		
		return customerData;
	}
}
