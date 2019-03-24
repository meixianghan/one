package cn.mrerror.yinuoc.scala

//import scala.collection.immutable.Set
import scala.collection.mutable.Set;
object OSet {
  
  def main(args:Array[String]):Unit={
    //scala.collection.immutable包下面的集合为不可编辑
    //不可编辑集合SET
    var imutableSet = Set(1,2,4,5,3);
    var imutableSetTwo = Set(6,7,6,5,9,5);
    //打印集合内容    
    println(imutableSet);
    //将集合内容变成Buffer数组
    println(imutableSet.toBuffer);
    //将集合内容变成Iterable迭代器对象
    println(imutableSet.toIterable);

    imutableSet+=5;
    imutableSet+=10;
    //追加元素并打印
    println(imutableSet);
    
    for(i<-imutableSet){
      println(i);
    }
    
    //====================常用方法=====================
     //判断是否存在某元素
     println(imutableSet.exists(_==3))
     println(imutableSet.exists((x)=>x==3))
     //返回集合头部元素
     println(imutableSet.head);
     //返回集合末尾元素
     println(imutableSet.last);
     //除尾部其他的元素内容
     println(imutableSet.init);
     //除了头部的其他元素内容
     println(imutableSet.tail);
     //集合中最小值
     println(imutableSet.min);
     //集合中最大值
     println(imutableSet.max);
     //将集合元素全部相乘
     println(imutableSet.product);
     //将集合元素全部累加
     println(imutableSet.sum);
     //统计集合中符合条件的记录数量
     println(imutableSet.count(_<5));
     println(imutableSet.count((x)=>x<5));
     //将两个集合的内容进行合并
     //{}代表语句块
     println({imutableSet++=imutableSetTwo;imutableSet});
     //求两个集合中的交集
     println(imutableSet&imutableSetTwo);
     //求两个集合中的差集
     println(imutableSet&~imutableSetTwo);
     //可编辑集合SET
     //add方法只能在可变集合中操作
     println({imutableSet.add(9);imutableSet})
     //remove方法只能在可变集合中操作
     println({imutableSet.remove(3);imutableSet});
  }
}