package cn.mrerror.yinuoc.scala.OInterface

class Dog {
}
object Dog{
  def main(args: Array[String]): Unit = {
    println(Dog)
    //动态实现一个接口
    var d = new Dog() with Eatable;
        d.eat();

    //动态实现一个接口
    var c = new Dog() with Flyable {
      override def fly(): Unit = {
        println("狗只能坐飞机飞");
      }
    };
  }
}
