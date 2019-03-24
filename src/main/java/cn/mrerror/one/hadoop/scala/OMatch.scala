package cn.mrerror.yinuoc.scala

object OMatch {
  
  def showTime(time:String,times:Int):String = time match{
    case "5" =>{
      var r:String = time;
      for(i <- 1 to times){
         r+=1;
      }
      "调用5次 \t".concat(r);
    }
    case _=>"不知道需要调用多少次"
  }
  
  /**
   * _符号相当于default
   */
  def main(args:Array[String]):Unit={
      println(showTime("count",5));
    
    var result=3;
    var name="zhangsan"
    var msg=("lisi",30,"北京");
    var msgArray= Array(1,2,3,4);
    
    result match{
      case 1=>println("1")
              println("已经找到");
      case _=>println("其他值");
    }
    
    name match{
      case "zhangsan"=>println("张三已经找到");
      case _=>println("无法匹配人员");
    }
    //_在元组里面是任意的
    msg match{
      case ("lisi",30,"南京")=>println("李四基本信息已经找")
      case ("lisi",_,_) =>println("匹配一部分");
      case (_,_,_)=>println("任意匹配人员");
      case _=>println("未找到匹配信息");
    }
    
    msgArray match{
      case Array(x, t,g,w)=>println("数组已匹配"+x+"\t"+t+"\t"+g+"\t"+w);
      case _=>println("数组未匹配");
    }
    var types:Any="";
    //根据值进行类型匹配
    var result3 = types match{
      case ""=>println("空字符串");
      case 1=>println("数字");
      case Int=>println("整型");
      case OMatch=>println("类");
      case (x,y)=>{println("元组");println(x)};
      case List(1,y,x)=>println("list");
      case Array(_,3,4)=>println("Array");
      case _=>println("所有");"类型不匹配"
    }
  }
  
}