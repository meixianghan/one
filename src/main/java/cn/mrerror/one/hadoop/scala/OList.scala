package cn.mrerror.yinuoc.scala

import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.ListMap;

object OList {
  
  def main(args:Array[String]):Unit={
    
    //====================一二维数组的生成方式=========================
     var list:List[String] = List("zhangsan","lisi");
     var blist:List[String] = List("wangwu","laoba");
     println(list);
     println(blist);
     //::将元素追加至List集合中
     var changeList = "hello"::"jack"::Nil;
     println(changeList);
     //二维数组的两种写法
     var dimList = List(List(1,2,3),List(2,3,4),List(3,4,5));     
     var bdimList:List[List[Int]] = (1::0::1::Nil)::
                                    (2::(3::(4::Nil)))::
                                    (3::(1::(4::Nil)))::Nil
     println(dimList);
     println(bdimList);
     println("-----------------------------------------------------");
     //==================List常用方法===================
     //数组合并
     println(List.concat(list,blist));
     //直接填充固定值的数组
     println(List.fill(9)("0"));
     //根据函数生成数组
     println(List.tabulate(6)((x)=>{
       if(x<2) 1 else 2
     }));
     println(List("张三,李四","王五,赵六")
             .map(_.split(",").toBuffer)//对字符串内容进行分割
             .flatten//对二维数组进行压平成一个一维数组
             );
     println(List(3,2,1,9,7,7,7,8,44)
             .sorted //排序
             .reverse//反转
             .distinct//去重
             .map(_*10)//遍历处理
             .filter((x)=>x<50)//过滤处理
             .dropRight(1)//从右边删除
             .dropWhile(_==30)//按指定条件删除
             .exists(_==20));//判断是否存在具体的值
     println("-----------------------------------------------------");
     //=====================可变集合=========================
     //List不可变数组,减省空间
     var add = List(3,4);
         add.+:(5);
         println(add);
     //List可变数组
     var addBuffer = ListBuffer(3,5,3,9);
         addBuffer.remove(1);
         addBuffer+=55;
         addBuffer+=2;
         println(addBuffer);
     var listmap = ListMap('s'->'s','g'->'g','c'->'c');
         listmap+=('h'->'h');
         listmap+=('t'->'h');
         listmap+=('y'->'h');
         listmap-=('h');
         println(listmap);
     println("-----------------------------------------------------");
  }
}