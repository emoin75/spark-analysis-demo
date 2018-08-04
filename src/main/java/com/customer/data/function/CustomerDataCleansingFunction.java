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

@Named
public class CustomerDataCleansingFunction implements Function<Row, Row>, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(CustomerDataCleansingFunction.class);

	@Override
	public Row call(Row row) throws Exception {
		try {

			if (Objects.nonNull(row)) {
				return RowFactory.create(StringUtils.trimToEmpty(row.getString(0)),
						StringUtils.trimToEmpty(row.getString(1)), StringUtils.trimToEmpty(row.getString(2)),
						StringUtils.trimToEmpty(row.getString(3)), StringUtils.trimToEmpty(row.getString(4)),
						StringUtils.trimToEmpty(row.getString(5)), StringUtils.trimToEmpty(row.getString(6)),
						StringUtils.trimToEmpty(row.getString(7)), StringUtils.trimToEmpty(row.getString(8)),
						StringUtils.trimToEmpty(row.getString(9)), StringUtils.trimToEmpty(row.getString(10)),
						StringUtils.trimToEmpty(row.getString(11)), StringUtils.trimToEmpty(row.getString(12)),
						StringUtils.trimToEmpty(row.getString(13)), StringUtils.trimToEmpty(row.getString(14)),
						StringUtils.trimToEmpty(row.getString(15)), StringUtils.trimToEmpty(row.getString(16)),
						StringUtils.trimToEmpty(row.getString(17)), StringUtils.trimToEmpty(row.getString(18)),
						StringUtils.trimToEmpty(row.getString(19)), StringUtils.trimToEmpty(row.getString(20)),
						StringUtils.trimToEmpty(row.getString(21)), StringUtils.trimToEmpty(row.getString(22)),
						StringUtils.trimToEmpty(row.getString(23)), StringUtils.trimToEmpty(row.getString(24)),
						StringUtils.trimToEmpty(row.getString(25)), StringUtils.trimToEmpty(row.getString(26)),
						StringUtils.trimToEmpty(row.getString(27)));

			}
		} catch (Exception e) {
			logger.error("Exception while cleansing customer data. Row value: " + row + "\n " + e);
		}
		return RowFactory.create("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "");
	}
}
