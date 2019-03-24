package cn.mrerror.yinuoc.spark.dataframe;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class RDDSQLSelect {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("JDBCDataSource").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		List<Emp> emps = new ArrayList<>();
		emps.add(new Emp(1, "ename1", 1000, 10));
		emps.add(new Emp(2, "ename2", 2000, 10));
		emps.add(new Emp(3, "ename3", 3000, 20));
		JavaRDD<Emp> empRDD = sc.parallelize(emps);
		Dataset<Row> empDataFrame = sqlContext.createDataFrame(empRDD, Emp.class);
					 empDataFrame.show();
					 empDataFrame.select
				        (
				          new Column("empno"),
				          new Column("ename"),
				          new Column("deptno")
				        ).show();
					 empDataFrame.filter("empno>2").show();
	}

}
