package cn.mrerror.yinuoc.spark.dataframe;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class RDD {
	
	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf();
    	
    	sparkConf.setMaster("local");
    	sparkConf.setAppName("sadsfv");
    	
    	JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
		
    	List<Emp> emps = new ArrayList<>();
    	
    	emps.add(new Emp(1, "zhangsan", 1000, 10));
    	emps.add(new Emp(2, "liis", 3000, 10));
    	emps.add(new Emp(3, "wangwu", 1000, 20));
    	
    	JavaRDD<Emp> empRDD = javaSparkContext.parallelize(emps);
    	
    	SQLContext sqlContext = new SQLContext(javaSparkContext);
    	
    	Dataset<Row> dataFrame = sqlContext.createDataFrame(empRDD, Emp.class);
    	dataFrame.registerTempTable("emp");
    	
    	sqlContext.sql("select * from emp").show();
	}

}
