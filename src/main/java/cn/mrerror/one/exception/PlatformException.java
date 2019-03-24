package cn.mrerror.one.exception;

import cn.mrerror.one.configuration.Applications;

public abstract class PlatformException extends Exception {
    protected  String platformType = Applications.PLATFORM_WEB;

    public PlatformException(){

    }
    public PlatformException(String platformType) {
        this.platformType = platformType;
    }

    public PlatformException(String message, String platformType) {
        super(message);
        this.platformType = platformType;
    }

    public void setPlatformType(String platformType){
        this.platformType = platformType;
    }

}
