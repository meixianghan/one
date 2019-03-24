package cn.mrerror.yinuoc.tools.hadoop.mapreduce.partition;


/**
 * 重写 apache.io.Text对象的hashcode方法
 * 改变partition内容分发方式
 */
public class Text extends org.apache.hadoop.io.Text {

    public Text(String name){
        super(name);
    }
    /**
     * 无惨构造方法，必写,因为框架反射对象的时候使用
     */
    public Text(){}

    /**
     * 重写hashcode算法
     */
    @Override
    public int hashCode() {
        String name = new String(this.getBytes());
        if(name.isEmpty()){
            return 0;
        }
        return name.substring(0,1).hashCode();
    }
}