package cn.mrerror.yinuoc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class Test {
    public static void main(String[] args) throws IOException {
    	
    	Table table = null;
    	Configuration config = HBaseConfiguration.create();// 配置
					  config.set("hbase.zookeeper.quorum", "hadoop1,hadoop2,hadoop3");// zookeeper地址
					  config.set("hbase.zookeeper.property.clientPort", "2181");// zookeeper端口
					  config.set("hbase.master","hadoop1:60000");

		Connection connection = ConnectionFactory.createConnection(config);
		table = connection.getTable(TableName.valueOf("yinuoc_product"));
        Get get = new Get(Bytes.toBytes("y1"));
        Result result = table.get(get);
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("price"))));

        connection.close();
        System.out.println("close");
    }
}
