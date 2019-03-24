package cn.mrerror.yinuoc.scala

/**
 * object 关键词
 * 用来定义一个对象，
 * 单例对象,object用来定义一个单例实体对象
 */
object HelloWord {
  
  /**
   * def:关键词
   * 作用：用来定义一个方法
   * :冒号指的是变量的数据类型(条件:变量申明)
   * :指返回类型(条件：定义方法)
   * []:定义范型类型
   * =指方法体内容
   */
  def main(args:Array[String]):Unit={
    println(HelloWord);
    println(HelloWord);
    println("-----------------------");
    println("HelloWorld");
    System.out.println("HelloWorld");
  }
}