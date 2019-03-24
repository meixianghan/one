package cn.mrerror.yinuoc.scala

/**
 * 元组数据,主要用来传递单一有关数据的集合
 * 简单的说就是一组数据
 */
object OTuple {
  
  def main(args:Array[String]):Unit={
    //声明一个元组
    //注意:元组内容的位置与含义必须自动定义,因为没有key与之对应
    var tuple = ("张三",30,100000,"有车","有房");
    //获取元组单独元素方式
    //通过[_编号]获取,编号的大小由元组的元素个数决定
    println(tuple);
    println(tuple._1);
    println(tuple._2);
    println(tuple._3);
    println(tuple._4);
    println(tuple._5);
    //什么情况下需要遍历元组的所有元素
    //把元组当成数组来使用的时候
    var array = Array("张三","李四","王五");
    var tupleArray =("张三","李四","王五");
    //如何遍历
    println(tupleArray.productIterator.foreach((x)=>println(x)));
    for(i<-tupleArray.productIterator){
       println(i);
    }
    //==================常用方法===============
    var change = (3,8);
    println(change.swap);
    //=================声明方式===============
    //直接使用括号来声明
    var t1 = (1,2,31,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
    //使用对象来声明
    var t2 = new Tuple2(1,1);
  }
  
}