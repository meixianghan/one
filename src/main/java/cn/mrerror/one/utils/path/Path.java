package cn.mrerror.one.utils.path;

/**
 * 路径接口
 */
public interface Path {

    /**
     * 生成路径
     *
     * @param content 需要转换路径的内容
     * @return 路径
     */
    public String generatorPath(String content);

    /**
     * 生成路径
     *
     * @param content 需要转换路径的内容
     * @param suffer  后缀
     * @return 路径
     */
    public String generatorPath(String content, String suffer);

}
