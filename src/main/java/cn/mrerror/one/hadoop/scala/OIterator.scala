package cn.mrerror.yinuoc.scala

import scala.math.Ordering
import scala.collection.mutable.Iterable;

/**
 * 迭代器
 */
object OIterator {
  
  def main(args:Array[String]):Unit={
      //迭代器容器声明的方式
      var jesus = Iterator("I","Love","Jesus",2);    
      for(i<-jesus){
        println(i);
      }
      //================常ss用方法======================
      println(jesus.length);
      
  }
  
}