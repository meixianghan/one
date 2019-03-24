package cn.mrerror.yinuoc.scala

import java.io.InputStream

object OType {
  
  def main(args:Array[String]):Unit={
    
      var age=30;
      var money=10;
      var result = if(age==20)"已存在"else"未找到"
        
      var result2={
        "已准备"
      }
      
      println(result2);
     
      println(result);
      
      var result3:Any = if(age==20) "年龄" else true
      
      println(result3);
      
      var result4:AnyVal = if(age<20) 10 else ()
      
      var tuple = ("age",3,"44",false);
      
      println(tuple._1);
      println(tuple._2);
      println(tuple._3);
      println(tuple._4);
      println(tuple);
      
      println(result4);
      
  }
}