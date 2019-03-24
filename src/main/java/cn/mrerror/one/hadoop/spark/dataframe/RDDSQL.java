package cn.mrerror.yinuoc.spark.dataframe;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class RDDSQL {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("JDBCDataSource").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		Map<String, String> options = new HashMap<String, String>();
		options.put("url", "jdbc:mysql:///test");
		options.put("driver", "com.mysql.jdbc.Driver");
		options.put("user", "root");
		options.put("password", "123456");
		// 读取第一个表
		options.put("dbtable", "emp");
		Dataset<Row> empDF = sqlContext.read().format("jdbc").options(options).load();
		// 读取第二个表
		options.put("dbtable", "dept");

		Dataset<Row> deptDF = sqlContext.read().format("jdbc").options(options).load();
		empDF.show();
		deptDF.show();

	}

}
