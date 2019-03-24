package cn.mrerror.yinuoc.scala

object OWhile {
  
  def main(args:Array[String]){

    var a =1;
    while(a<10){
      println(a);
      a+=1;
    }

    //do while 循环,执行b+=1不能直接输出,因为会返回()对象
    var b:Int = 1;
    do{
      b=b.+(1)
      println(b);
    }while(b<10)
      
      
  }
}