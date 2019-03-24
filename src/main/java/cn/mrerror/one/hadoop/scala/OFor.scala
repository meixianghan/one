package cn.mrerror.yinuoc.scala

/**
 * for循环的编写方式
 */
object OFor {
  
  def main(args:Array[String]):Unit={
    
    //=================单循环====================
    var list = List(1,2,3,4,5);
    //遍历一个集合对象
    for(i <- List(1,2,3,4,5)){
      println(i);
    }
    for(i <- List(1,2,3,4,5)){
      println(i);
    }
    for(i <- Array(1,2,3,4,5)){
      println(i);
    }
    println("---------------------------------------------");
    //1 to 10 [等于1.to(10)] 是一个整体(集合对象)  <=
    for(i <- 0 to 10){
      println("to \t"+i);
    } 
    //1 to 10 [等于1.until(10)]是一个整体(集合对象) <
    for(i <- 0 until 10){
      println("until \t"+i);
    }
    println("---------------------------------------------");
    //=================多循环===================
    for(i<- 1 to 10;j<- 1 to 10;z<-1 to 10){
       println(i +"\t"+ j +"\t"+z);
    }
    //双重循环
    for(i <- 1 to 10 if i<5 if i>2 if i!=3){
         println(i);
    }
    //双重循环加条件判断
    for(i <- 1 to 10 ;j<- 1 to 10 if i>5 if i<2 if j!=3){
         println(i*j);
    }
    println("---------------------------------------------");
    //循环打印字符串（字符串其实就是char数组)
    for(i<- "abcdef"){
       println(i);
    }
    //循环执行元素*10,并返回结果集
    println(for(i<-1 to 10) yield i*10)

  }
  
}