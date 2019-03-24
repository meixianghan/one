package cn.mrerror.yinuoc.scala

import scala.collection.immutable.List;

object OArray {

  def main(args:Array[String]):Unit={
    println(OArray);
    println(OArray==OArray);
    println(OArray);
    
    //一维数组的操作
    var array1 = Array[Int](1,2,3,4,5,6,6,8);
    var array2 = Array[Int](1,2,3,4,5,6,6,8);
    println(Array.concat(array1,array2).toBuffer);
    println(array1.toBuffer);
    println(array2.toBuffer);
    println(array1.mkString("in(",",",")"))
    println(array1 ++: array2 toBuffer)

    println("---------------------------------------------");
    //二维数组操作
    var dimarray = Array.ofDim[Int](3,3);
    for(i<- 0 to 2;j<- 0 to 2){
       dimarray(i)(j)=i*j;
       println(dimarray(i)(j));
    }
    println("---------------------------------------------");
    //生成一个区间的范围
    var rangArray = Array.range(1, 20);
    var rangArray2 = Array.range(1, 20,2);//间隔为2
    println(rangArray.toBuffer);
    println(rangArray2.toBuffer);
    println(rangArray.filter((x)=>x%2==0).toBuffer)//显式偶数
    println("---------------------------------------------");
    for(i<- 0 until array1.length){
        println(array1(i));
    }
    
  }
}