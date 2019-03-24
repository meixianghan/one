package cn.mrerror.yinuoc.scala.OInterface

abstract class Animal {
  //具体方法
  def run():Unit={
    println("两条腿走路");
  }
  //抽象方法
  def showTime():String
}