package cn.mrerror.yinuoc.scala.OInterface

class Pig extends Eatable with Flyable{

  override def fly():Unit={
    println("筋斗云");
  }

}
