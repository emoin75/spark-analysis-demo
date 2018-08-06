# spark-analysis-demo
This project is a spark job which reads file, transforms and analyses data and prints as file. 

## Play Book

Pre-requisite: maven, git, spark-2.3.1

Clone the project: `git clone https://github.com/sarojkmb/spark-analysis-demo.git`

Go to the project folder

Change the log4.xml file info Line#12, so that the log file will be generated at the right place

Build project ``mvn clean install``

You will find the shaded jar: ex- customer-data-analysis-00.01.00.00-SNAPSHOT-shaded.jar

Edit the spark-submit.sh file as per your project location.

Then either run ./spark-submit.sh or copy it and run in the command-line/terminal

For windows users: You might have to use back slash instead of front slash in scripts.


This is my addition - 1, corrected after review.

This is my addition - 2 this is yusuf
