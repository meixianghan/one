package cn.mrerror.one.hadoop.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSTest {
	//hdfs namenode访问地址
	public static final String HADOOP_HDFS_ADDRESS="hdfs://188.131.134.186:9000";
	//hdfs 访问用户名
	public static final String HADOOP_HDFS_USER="root";
	//hdfs 访问用户组
	public static final String HADOOP_HDFS_GROUP="supergroup";
	//hdfs 用户访问的根目录
	public static final String HADOOP_HDFS_ROOT_DIR="/";
	//hdfs 自定义的上传的目录地址
	public static final String HADOOP_HDFS_DOC_DIR="/junit";
	//hdfs 本地需要上传文件的地址
	public static final String LOCAL_DIR_PATH="E:\\input\\";

	//hdfs 文件系统对象
	public FileSystem fs=null;
	//本地磁盘 文件系统对象
	public FileSystem localfs = null;

	/**
	 * Junit before方法,在执行任何@Test 方法之前先执行@before方法
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 */
	@Before
	public void beforeLoad() throws IOException, InterruptedException, URISyntaxException{
		//配置文件
		Configuration conf = new Configuration();
		//设置hdfs 默认访问地址
		conf.set("fs.defaultFS", HADOOP_HDFS_ADDRESS);

		//文件系统对象
		fs = FileSystem.get(new URI(HADOOP_HDFS_ADDRESS), conf, HADOOP_HDFS_USER);
		//设置文件系统拥有者信息
		fs.setOwner(new Path(HADOOP_HDFS_ROOT_DIR), HADOOP_HDFS_USER, HADOOP_HDFS_GROUP);
		//本地文件系统对象
		localfs = FileSystem.getLocal(new Configuration());
		//打印HDFS统计信息
		FileSystem.printStatistics();

	}

	/**
	 * 创建一个classone文件夹
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void createClassDir() throws IllegalArgumentException, IOException{
		//创建文件夹
		boolean result = fs.mkdirs(new Path(HADOOP_HDFS_DOC_DIR),new FsPermission(FsAction.ALL,FsAction.ALL, FsAction.ALL));
		//打印创建状态的结果
		System.out.println(result?"class文件夹创建成功":"class文件夹创建失败");
	}

	/**
	 * 递归显示HDFS指定目录下的所有文件
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void showDirList() throws FileNotFoundException, IllegalArgumentException, IOException{
		//所有HDFS目录下所有文件
		RemoteIterator<LocatedFileStatus> ri = fs.listFiles(new Path(HADOOP_HDFS_ROOT_DIR), true);
		//迭代输出
		while(ri.hasNext()){
			//所有文件状态对象
			LocatedFileStatus lfs = ri.next();
			//打印文件状态详细信息
			System.out.println(lfs.toString());
			//获取文件的Block信息
			BlockLocation blocks[] = lfs.getBlockLocations();
			//打印对应的block块
			for(BlockLocation item:blocks){
				System.out.println(item.toString());
			}
		}
	}

	/**
	 * 上传文件
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void uploadFile() throws IllegalArgumentException, IOException{
		//将本地的文件复制与HDFS 指定目录中
		fs.copyFromLocalFile(new Path(new StringBuilder().append(LOCAL_DIR_PATH).append("junit.txt").toString()), new Path(HADOOP_HDFS_DOC_DIR));
	}

	/**
	 * 公共资源的时候：线程安全
	 * 局部变量:私栈
	 * 全局变量:公栈
	 * 从HDFS中下载文件至本地
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void downloadFile() throws IllegalArgumentException, IOException{
		//显出本地已经存在的文件
		localfs.delete(new Path(new StringBuilder().append(LOCAL_DIR_PATH).append("junit.txt").toString()), false);
		//从服务器复制文件至本地指定路径
		fs.copyToLocalFile(new Path(new StringBuilder().append(HADOOP_HDFS_DOC_DIR).append("\\junit.txt").toString()), new Path("E:\\output\\"));
	}

	/**
	 * 通过流的方式上传文件
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void uploadStream() throws IllegalArgumentException, IOException{
		//在HDFS中创建一个空文件对象输出u流对象
		FSDataOutputStream fsdos = fs.create(new Path(new StringBuilder(HADOOP_HDFS_DOC_DIR).append("/CentOS").toString()));
		//本地创建输入流对象
		FileInputStream fis = new FileInputStream(new File(new StringBuilder(LOCAL_DIR_PATH).append("CentOS-6.7-x86_64-bin-DVD1.iso").toString()));
		//指定一个缓存我
		byte content[] = new byte[1024];
		//获取从输入流中读取实际大小
		int readNum=-1;
		//循环读取内容
		while((readNum=fis.read(content))!=-1){
			fsdos.write(content, 0, readNum);
		}
		//关闭所有流
		fis.close();
		fsdos.close();
		fs.close();
	}

	/**
	 * 通过流方式从HDFS中下载文件
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void donwloadStream() throws IllegalArgumentException, IOException{
		//从HDFS中打开一个流对象
		FSDataInputStream fsdis = fs.open(new Path(new StringBuilder("/soft/").append("/nginx-1.6.2.tar.gz").toString()));
		//在本地创建一个输出流对象
		FileOutputStream fos = new FileOutputStream(new File(new StringBuilder(LOCAL_DIR_PATH).append("\\nginx.tar.gz").toString()));
		//指定一个缓冲
		byte content[] = new byte[1024];
		//记录读取字节的数量
		int readNum=-1;
		//循环读取流输出至本地
		while((readNum=fsdis.read(content))!=-1){
			fos.write(content, 0, readNum);
		}
		//关闭所有的流
		fos.close();
		fsdis.close();
		fs.close();
	}

	/**
	 * 在HDFS中创建一个空文件
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void touchZeroFile() throws IllegalArgumentException, IOException{
		//创建一个空文件
		boolean result =fs.createNewFile(new Path(new StringBuilder(HADOOP_HDFS_DOC_DIR).append("/appendfile").toString()));
		//输出创建空文件的状态
		System.out.println(result?"append文件创建成功":"append文件创建失败");
	}

	/**
	 * 将本地文件的内容追加至HDFS中指定的文件中
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	@Test
	public void appendToFile() throws IllegalArgumentException, IOException{
		//创建一个需要追加文件的输出流对象
		FSDataOutputStream fsdos = fs.append(new Path(new StringBuilder(HADOOP_HDFS_DOC_DIR).append("/appendfile").toString()), 1024);
		//创建一个本地文件输入流对象
		FileInputStream fis = new FileInputStream(new File(new StringBuilder(LOCAL_DIR_PATH).append("使用必读.txt").toString()));
		//指定一个缓冲
		byte content[] = new byte[1024];
		//记录读取字节的数量
		int readNum=-1;
		//循环读取流输出至HDFS文件
		while((readNum=fis.read(content))!=-1){
			fsdos.write(content, 0, readNum);
		}
		//关闭所有的流
		fis.close();
		fsdos.close();
		fs.close();
	}
	
	
	

}
