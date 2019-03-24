package cn.mrerror.yinuoc.scala

import java.io.{File, PrintWriter}

import scala.io.Source

//IO操作
object OIO {
  //读取文件
  def scalaRead(){
    println("文件内容为:" )
    Source.fromFile("D:/scala/first/src/cn/com/scala/oio/test.txt" ).foreach{
      print
    }
  }
  //屏幕读取内容
  def scanRead(){
    print("开始读入" )
    val line = Console.readLine
    println("内容: " + line)
  }
  //程序入口
  def main(args: Array[String]) {
    scalaRead();
    val writer = new PrintWriter(new File("test.txt" ))
        writer.write("内容")
        writer.close()
  }
}
