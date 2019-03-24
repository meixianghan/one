package cn.mrerror.yinuoc.scala

/**
 * 函数定义
 * 函数由方法来进行消费
 */
object OFunction {


  //函数的=等号(赋值)写法
  val showdate=(date:String)=>{
    "现在时间:"+date;
  }
  var showdate2=(date:String,time:String)=>{
    "现在时间:"+date+"\t"+time;
  }
  
  var showdateCount=(count:Int)=>{
    for(i<-1 to count){
      println("2018/05/11 3:38:00");
    }
    "打印完成";
  }
  //函数:冒号写法
  //分离写法第一步写参数类型,第二步写变量
  var showNames:(String)=>String = name => "我的名字叫:"+name;
  
  //打印名字的方法
  def printlnYourName(name:String)(f:String=>String){
    println(f(name));
  }

  //函数柯里化
  //打印日期几次的方法
  def printShowDate(content:Int)(f:Int=>String){
    println(f(content));
  }
  //打印日期的方法
  def printShowDate(f:String=>String){
    println(f("2018/05/11 3:36:00"));
  }
  //打印日期的方法并返回值
  def printShowDate(f:(String,String)=>String):String={
    f("2018/5/11","3:33:00")
  }
  //打印数字
  def printShowDate(f:Int=>String):String={
    f(5);
  }
  //可变参数长度
  def echo(args:String*) = {for (arg <- args) println(arg)}

  //参数默认值
  def defautValue(name:String="zhangsan")=println(name);

  def main(args:Array[String]):Unit={
      defautValue("王五");
      //打印多值
      echo("hello", "world!")
      //数组内容
      val arr = Array("I", "love", "jesus");
      //将数组的每个元素当做参数
      echo(arr:_*);
      //匿名函数调用
      println(printShowDate((x:Int)=>x+":显示的值"));
      //非匿名函数调用
      println(printShowDate(showdate2));
      //单参数的非匿名函数
      printShowDate(showdate);
      //多参数的非匿名函数
      printShowDate(5)(showdateCount);
      //函数的分离式写法
      printlnYourName("jack")(showNames);
  }
  
}