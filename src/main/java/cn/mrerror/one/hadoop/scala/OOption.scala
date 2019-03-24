package cn.mrerror.yinuoc.scala

/**
 * Option对象
 * 主要解决只有两种状态下的场景
 * 有或者没有
 * 真或者假
 */
object OOption {
  
  /**
   * 定义一个方法进行 Option的模式匹配
   * 功能:显示匹配的对象,并打印对应的内容
   */
  def showMsg(x:Option[String]) = x match{
    case Some("2")=>println("已经找到");
    case None=>println("空值");
    case _=>println("None");
  }
  
  def main(args:Array[String]):Unit={
    //调用方法传递Option的子类进行模式匹配
    showMsg(Some("2"));
    showMsg(None);
    showMsg(Some("你好"));
    //直接声明方式
    var o1:Option[String] = new Some("张三");
    var eo1:Option[String] = None;
    
  }
  
}