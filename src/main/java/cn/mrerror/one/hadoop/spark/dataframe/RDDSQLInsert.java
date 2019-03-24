package cn.mrerror.yinuoc.spark.dataframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class RDDSQLInsert {
	
	public static void main(String[] args) {
		 SparkConf conf = new SparkConf().
	        		setAppName("JDBCDataSource").
	        		setMaster("local");  
	        JavaSparkContext sc = new JavaSparkContext(conf);
	        SQLContext sqlContext = new SQLContext(sc);
	        
	        List<Emp>emps = new ArrayList<>();
	        emps.add(new Emp(null, "ename1", 1000, 10));
	        emps.add(new Emp(null, "ename2", 2000, 10));
	        emps.add(new Emp(null, "ename3", 3000, 20));
	        JavaRDD<Emp> empRDD = sc.parallelize(emps);
	        Dataset<Row> empDataFrame = sqlContext.createDataFrame(empRDD, Emp.class);
	        
	        
	        Properties options = new Properties();
	    	options.put("driver", "com.mysql.jdbc.Driver") ;
	    	options.put("user", "root");
	    	options.put("password", "123456");
	    	//追加
	        empDataFrame.write().mode("append").jdbc("jdbc:mysql:///test", "emp", options);
	    	//empDataFrame.write().jdbc("jdbc:mysql:///test", "emp", options);//直接写不需要创表
	        sc.close();
	}

}
