package cn.mrerror.one.utils;

public class KeyUtils {

    public static synchronized String getUUID(){
        return java.util.UUID.randomUUID().toString();
    }
}
