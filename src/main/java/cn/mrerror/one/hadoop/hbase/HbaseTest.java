package cn.mrerror.yinuoc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HbaseTest {

	/**
	 * 配置ss
	 */
	private Configuration config = null;
	private Connection connection = null;
	private Table table = null;

	@Before
	public void init() throws Exception {
		config = HBaseConfiguration.create();// 配置
        config.set("hbase.zookeeper.quorum", "hadoop1,hadoop2,hadoop3");// zookeeper地址
        config.set("hbase.zookeeper.property.clientPort", "2181");// zookeeper端口
        config.set("hbase.master","hadoop1:60000");
		connection = ConnectionFactory.createConnection(config);
		table = connection.getTable(TableName.valueOf("yinuoc_product"));
	}

	/**
	 * 向hbase中增加数据
	 * @throws Exception
	 */
	@Test
	public void insertData() throws Exception {
		Put put = new Put(Bytes.toBytes("y4|info"));//key
			put.add(Bytes.toBytes("basic"), Bytes.toBytes("name"), Bytes.toBytes("water"));
			put.add(Bytes.toBytes("basic"), Bytes.toBytes("price"), Bytes.toBytes(12));
			put.add(Bytes.toBytes("basic"), Bytes.toBytes("total"), Bytes.toBytes(1000));
		//插入数据
		table.put(put);
	}

	/**
	 * 修改数据
	 * @throws Exception
	 */
	@Test
	public void updateData() throws Exception {
        Put put = new Put(Bytes.toBytes("y3"));//key
            put.add(Bytes.toBytes("basic"), Bytes.toBytes("name"), Bytes.toBytes("router"));
            put.add(Bytes.toBytes("basic"), Bytes.toBytes("price"), Bytes.toBytes(456));
            put.add(Bytes.toBytes("basic"), Bytes.toBytes("total"), Bytes.toBytes(2000));
		//插入数据
		table.put(put);
	}

	/**
	 * 删除数据
	 * @throws Exception
	 */
	@Test
	public void deleteDate() throws Exception {
		Delete delete = new Delete(Bytes.toBytes("y4"));
		table.delete(delete);
	}

	/**
	 * 单条查询
	 * @throws Exception
	 */
	@Test
	public void queryData() throws Exception {
		//id
		Get get = new Get(Bytes.toBytes("y1"));
		Result result = table.get(get);
		System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("name"))));
		System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("price"))));
	}

	/**
	 * 全表扫描
	 * @throws Exception
	 */
	@Test
	public void scanData() throws Exception {
		Scan scan = new Scan();
//		     scan.addFamily(Bytes.toBytes("info"));
		//scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("password"));
		//id起始位置
		scan.setStartRow(Bytes.toBytes("y1"));
		scan.setStopRow(Bytes.toBytes("y4"));
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("name"))));
			System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("price"))));
		}
	}

	/**
	 * 全表扫描的过滤器
	 * 列值过滤器
	 * @throws Exception
	 */
	@Test
	public void scanDataByFilter1() throws Exception {
		// 创建全表扫描的scan
		Scan scan = new Scan();
		//过滤器：列值过滤器
		SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("basic"),
				Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,
				Bytes.toBytes("phone"));
		// 设置过滤器
		scan.setFilter(filter);
		// 打印结果集
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("name"))));
			System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("price"))));
		}
	}
	/**
	 * CompareFilter:比较过滤器
	 * EQUAL:等于
	 * GREATER:大于
	 * GREATER_OR_EQUAL:大于或等于
	 * LESS:小于
	 * LESS_OR_EQUAL:小于等于
	 * NO_OP:
	 * NOT_EQUAL:不等于
	 * rowkey过滤器
	 * user1|ts1 //组合key
	 * @throws Exception
	 * 例如:
	 *   ROW                          COLUMN+CELL
		 user1|ts1                   column=sf:c1, timestamp=1409122354868, value=sku1
		 user1|ts2                   column=sf:c1, timestamp=1409122354918, value=sku188
		 user1|ts3                   column=sf:s1, timestamp=1409122354954, value=sku123
		 user2|ts4                   column=sf:c1, timestamp=1409122354998, value=sku2
		 user2|ts5                   column=sf:c2, timestamp=1409122355030, value=sku288
		 user2|ts6                   column=sf:s1, timestamp=1409122355970, value=sku222
	 */
	@Test
	public void scanDataByFilter2() throws Exception {
		// 创建全表扫描的scan
		Scan scan = new Scan();
		//匹配rowkey
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^y\\d+\\|info$"));
		// 设置过滤器
		scan.setFilter(filter);
		// 打印结果集
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("name"))));
			System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("basic"), Bytes.toBytes("price"))));
		}
	}

	@After
	public void close() throws Exception {
		table.close();
		connection.close();
	}
}