package cn.mrerror.yinuoc.spark.dataframe;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class RowRDD {

	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf();

		sparkConf.setMaster("local");
		sparkConf.setAppName("sadsfv");

		JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

		List<Row> rows = new ArrayList<>();
		Row row1 = RowFactory.create("1", "zhangsan", "3000", "20");

		Row row2 = RowFactory.create("2", "lisi", "3400", "10");

		Row row3 = RowFactory.create("3", "wangwu", "5700", "20");
		rows.add(row1);
		rows.add(row2);
		rows.add(row3);

		JavaRDD<Row> rowRDD = javaSparkContext.parallelize(rows);

		SQLContext sqlContext = new SQLContext(javaSparkContext);

		StructType structType = new StructType(
				new StructField[] { DataTypes.createStructField("empno", DataTypes.StringType, true),
						DataTypes.createStructField("ename", DataTypes.StringType, true),
						DataTypes.createStructField("sal", DataTypes.StringType, true),
						DataTypes.createStructField("deptno", DataTypes.StringType, true) });

		Dataset<Row> dataFrame = sqlContext.createDataFrame(rowRDD, structType);
		dataFrame.registerTempTable("emp");

		sqlContext.sql("select * from emp").show();
	}
}
