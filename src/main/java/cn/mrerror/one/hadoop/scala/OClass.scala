package cn.mrerror.yinuoc.scala

/**
 * 类定义有两种方式
 * 第一种不定义任何参数
 * 第二种直接定义构造参数
 */
 class OClass{
   //声明两个私有的属性
   private var name:String=_;//名称
   private var job:String=_;//职位
   private var isWork:Boolean=false;//空闲状态
   //使用private[this]只能在类的内部使用
   private[this] var money:Int=10000;
   
   def this(name:String,job:String){
     this();
     this.name=name;
     this.job=job;
   }
   
   def getMoney():String = {
      money.toString()
   }
   
   override def toString = name+job+isWork;
  
}
/**
 * 所有的无惨构造方法为默认实现
 */
class OClass2(){
  
}

class Man{
   var name:String="";
   var active:String="";
}

class BigMan{
  
  var name:String=_;
  var active:String=_;
  
  def this(name:String,active:String){
    //有参构造方法必须先调用一下无惨的构造方法
    this();
    //设置属性值name
    this.name =name;
    //设置属性值active
    this.active=active;
  }

}

class OPerson(name:String,active:String){
  
}
/**
 * 默认构造方法里面的参数是[必须的]
 * 在放射过程中生成对象根据的构造方法是类后面定义的方式(name:String,active:String)
 */
class Female(name:String,active:String){
   var isWork:Boolean=false;

   def this(){
     this("李女士","旅游");
   }

   def this(name:String,active:String,isWork:Boolean){
     this(name,active);
     this.isWork = isWork;
   }

}
/**
 * 伴生对象
 * 主要作用:快速构建实体对象
 *       主要为Class类服务的
 */
object OClass{
  //无惨的apply不会被调用
  def apply(){
    println("调用我");
  }
  //apply方法会自动调用根据参数匹配
  def apply(name:String):String={
    "调用我";
  }
  //apply方法会自动调用根据参数匹配
  def apply(name:String,job:String):OClass={
     new OClass(name,job);
  }
  //apply方法会自动调用根据参数匹配
  def apply(name:String,job:String,isWork:Boolean):OClass={
     var oc =  new OClass(name,job);
         oc.isWork = isWork;
         oc;
  }
  
  def main(args:Array[String]):Unit={
       //() void
       println(OClass);
       println(OClass);
       println(OClass(""));
       println(OClass("张三","无业者"));
       println(OClass("张三","无业者"));
       println(OClass("张三","无业者",false));
       
     var oclass:OClass = new OClass();
         oclass.name ="伴生对象";
         
     var operson:OPerson = new OPerson("李四","排球");
     //张女士
     var female:Female = new Female();
     //张女士化妆
     var female1:Female = new Female("张女士","化妆");
     //张女士正在化妆
     var female3:Female = new Female("张女士","化妆",true);
     
     var man:Man = new Man();
         man.name="张三";
         man.active="篮球";
     var bigman1:BigMan = new BigMan();
     var bigman2:BigMan = new BigMan("","");
  }
}