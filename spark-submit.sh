/Users/saroj/Workspace/software/spark-2.3.1-bin-hadoop2.7/bin/spark-submit \
--master local[1] \
--class com.customer.data.spark.impl.CustomerDataAnalysisSparkJob \
--conf \
    "spark.driver.extraJavaOptions= \
    -Dlog4j.configuration=file:/Users/saroj/Workspace/projects/customer-data-analysis/src/main/resources/log4j.xml \
    -Dcust.data.file.path=/Users/saroj/Workspace/projects/customer-data-analysis/src/test/resources/sample/sample1000.csv \
    -Dcust.usage.file.name=/Users/saroj/Workspace/projects/output/usageByPhoneNum-1.txt" \
target/customer-data-analysis-00.01.00.00-SNAPSHOT-shaded.jar