package com.customer.data.helper;

import java.io.Serializable;

import javax.inject.Named;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

@Named
public class SparkHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    private SparkSession sparkSession;

    private static JavaSparkContext javaSparkContext;

    public SparkSession instantiateSession(final String appName) {
        if (sparkSession == null) {
            sparkSession = SparkSession.builder()
                    .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                    .config("spark.rpc.askTimeout", "800s")
                    .config("spark.rpc.lookupTimeout", "800s")
                    .appName(appName)
                    .getOrCreate();
        }
        return sparkSession;
    }

    public void closeSession(SparkSession sparkSession){
        getJavaSparkContext(sparkSession).close();
        sparkSession.stop();
    }

    public JavaSparkContext getJavaSparkContext(SparkSession sparkSession) {
        if (javaSparkContext == null) {
            javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());
        }
        return javaSparkContext;
    }
}


