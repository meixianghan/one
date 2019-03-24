package cn.mrerror.one.utils.cdn;

/**
 * one平台CDN 资源同步刷新接口
 */
public interface RefreshCdn {

    /**
     * 刷新CDN资源地址
     *
     * @param urls 刷新URL地址集合
     * @return 返回刷新的状态
     */
    public boolean refreshCdnUrl(String... urls);

    /**
     * 刷新CDN资源目录
     *
     * @param urls 刷新目录地址集合
     * @return 返回刷新状态
     */
    public boolean refreshCdnDir(String... urls);

}
