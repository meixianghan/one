package cn.mrerror.one.utils.image;

/**
 * 图像Bean工厂
 */
public class ImageBeanFactory {
    /**
     * 返回图像处理对象
     *
     * @param imageSource
     * @return
     */
    public final ImageHandler getImageBean(ImageSource imageSource) {
        if (imageSource == ImageSource.TENCENT) {
            return new TencentImageHandler();
        }
        return null;
    }
}