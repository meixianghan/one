package cn.mrerror.yinuoc.scala.OInterface

class Monkey extends Animal with Flyable with Eatable {
  //重写飞行方法
  override def fly():Unit={
    println("筋斗云");
  }
  //重写表演方法,可以选择性的添加override关键词
  override def showTime():String ={
    "show monkey time";
  }
  //Scala重写一个非抽象方法，必须用override修饰符。
  //重写行走方法,必须加override关键词
  override def run():Unit = {
    println("monkey walking");
  }
}
object Monkey{
  def main(args: Array[String]): Unit = {
    //多态
    var m:Animal = new Monkey();
    m.run();
  }
}

