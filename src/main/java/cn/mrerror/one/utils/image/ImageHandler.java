package cn.mrerror.one.utils.image;

import java.io.File;

/**
 * 图片处理接口
 */
public interface ImageHandler {

    public boolean uploadImage(File file);

    public boolean uploadImage(File file, String subDirAndName);

    public boolean removeImage(String path);

    public File downloadImage(String remotePath, String localPath);

}