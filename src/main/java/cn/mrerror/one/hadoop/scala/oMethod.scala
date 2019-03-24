package cn.mrerror.yinuoc.scala

object oMethod {
  
  //定义一个方法isEmpty,这是scala里面方法的最简单版本的写法
  def isEmpty = false
  //定义一个返回值的isEmptyone方法
  def isEmptyone():Boolean= false;
  //但参数不带返回值的写法
  def showDate(date:String){
    println(date);
  }
  //多参数带返回值的写法
  def showDate(date:String,isadd:Boolean):String = {
    "北京时间:\t"+date
  }
  //带参数并且参数具有默认值的方法
  def showDate(date:Any="2018/05/10"){
    println(date);
  }
  //不带参数无括号的方法
  def showDate=println("2018/05/10 6:04:00");
  //参数不确定个数的方法
  def showDate(date:String*){
    println(date);
  }
  //多括弧的参数函数
  def showDate(count:Int)(date:String){
    for(i<-0 to count){
      println(date);
    }
  }
  //主函数程序执行的入口
  def main(args:Array[String]):Unit={
    showDate();
    showDate
    showDate("今天","我的手表是","2018/05/10 6:07");
  }
  
}