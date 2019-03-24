package cn.mrerror.yinuoc.scala

object SingleObject {

  //请求方法,直接打印值
  def apply(str: String): Unit = {
    println(str);
  }

  //请求方法,打印x*y的值
  def apply(x: Int, y: Int): Int = {
    return x * y;
  }

  //请求方法,将元素组织返回一个数组
  def apply(x: Int, y: Int*): Array[Int] = {
    var array: Array[Int] = new Array[Int](y.length + 1);
    array(0) = x;
    var i = 1;
    for (x <- y) {
      array(i) = x;
      i += 1;
    }
    return array;
  }

  //程序的主入口
  def main(args: Array[String]): Unit = {
    //生产一个单例对象
    var obj = SingleObject;
    println(obj);
    //重新获取同一个单例对象
    var obj2 = SingleObject;
    println(obj2);
    //调用单例的apply方法,并返回一个Unit的值
    var obj3 = SingleObject("zhangsan");
    println(obj3);
    //调用单例的apply方法,将3*4值打印出来
    var obj4 = SingleObject(3, 4);
    println(obj4);
    //调用单例的apply方法,将元素添加至于数组
    var obj5 = SingleObject(3, 4, 3, 2, 3, 3, 3).toBuffer;
    println(obj5);
    //===============================================
    var l1 = List(12, 3, 4, 4)
    //List是一个object,自动会调用apply方法
    val a1 = Array(23, 2, 3, 2);
    //Array是一个object,自动会调用apply方法
    val a2 = new Array[Int](3);
    println(a1.toBuffer);
    println(a2.toBuffer);
  }
}
