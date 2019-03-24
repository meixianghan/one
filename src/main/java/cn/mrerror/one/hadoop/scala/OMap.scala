package cn.mrerror.yinuoc.scala

//import scala.collection.immutable.Map;
import scala.collection.mutable.Map;

/**
 * Map集合操作
 */
object OMap {
  
  
  def main(args:Array[String]):Unit={
     
    //map声明 key->value 格式
    //如何将一个实体Bean(业务)转变的一个集合对象[Map转换]
    var map:Map[String,String] =Map("name"->"zhangsan",
                                    "age"->"1",
                                    "sex"->"0",
                                    "address"->"bj");
    //任何追加元素
        map+=("card"->"gh");
        map+=("job"->"enger");
    //打印map集合
    println(map);
    //将map集合转变成Array集合
    println(map.toArray.toBuffer);
    //将map集合转变成List集合
    println(map.toList);
    //将map集合转变成Set集合
    println(map.toSet);
    //将map集合转变成Buffer
    println(map.toBuffer);
    
    //===================map常用的方法======================
    //map通过集合遍历就是一个元组对象
    for(i<-map){
      print("key:"+i._1);
      println("\tvalue:"+i._2);
    }
    //通过key获取指定元素的内容
    println(map.get("card").get);
    println(map.get("mend").isEmpty);
    //通过keys去获取所有的内容
    println(map.keys);//iterable返回的是具体的集合类型
    println(map.keySet);//直接返回集合的keys
    println(map.keysIterator);//返回集合keys的迭代器
    //遍历map的keys迭代器
    for(i<-map.keysIterator){
      println(i);
    }
    //调用map.keys的foreach遍历所有的key值
    println(map.keys.foreach(x=>{println(x)}));
    //遍历map的keys迭代器,并获取每一key所对应的值
    for(i<-map.keysIterator){
      println(map.get(i).get);
    }
    //=====================常用方法===================
    //判断key所对应的值是否存在
    println(map.contains("card"));
    //根据key删除指定元素
    println(map.remove("age"));
    //从头部获取指定个数的元素
    println(map.take(3));
    //从尾部获取指定个数的元素
    println(map.takeRight(3));
    //根据条件获取指定的元素
    println(map.takeWhile(x=>{if(x._1.equals("card"))true else false}))
    //将map中所有的元素key,value进行处理,并返回一个数组
    println(for((key,value)<- map) yield(key+"_iKey",value+"_ivalue"));
    //将map中所有的元素的value进行处理,并返回一个数组
    println(for((key,value)<- map) yield(value+"_ivalue"));
  }
}