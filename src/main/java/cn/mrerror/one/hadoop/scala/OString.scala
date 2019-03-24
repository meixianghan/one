package cn.mrerror.yinuoc.scala

import scala.util.matching.Regex

/**
 * 字符串处理
 */
object  OString {
  
  def main(args:Array[String]):Unit={
    //建立正则的方式1
    println("hello world".r.findAllIn("hello world to finded").toBuffer)
    //迭代循环打印匹配的内容
    for(i<-"hello".r.findAllIn("hello world to finded hello")){
      println(i);
    }
    //建立正则的方式2
    var pattern = new Regex("(H|h)ello");
    val result = pattern.findAllIn("hello world to finded Hello ,jack saying hello").toList;
    
    println(result);
    //循环打印匹配的结果
    for(i<-result){
      println("finded word:"+i);
    }
    //替换
    println("jack".r.replaceAllIn("jack here,jack", "susam").toList);
    //替换首各单词
    println("jack".r.replaceFirstIn("jack here,jack", "susam").toList);
      //字符串性能提升的处理对象
      var sbd:StringBuilder = new StringBuilder();
          sbd+='h';
          sbd++="ello";
          sbd append "\tjack" append "\thow are you "
      println(sbd);
      //====================格式化内容======================
      //原始方法，手动代码拼接
      println("hi ".concat(" jack ").concat("i'm here"));
      //格式化方法
      //消息模版,内容模版
      println("hi %s i'm here ,$%d".format("jack",1000));

      
  }
}