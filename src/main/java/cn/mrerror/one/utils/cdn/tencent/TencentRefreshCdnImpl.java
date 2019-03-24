package cn.mrerror.one.utils.cdn.tencent;

import cn.mrerror.one.configuration.Applications;
import cn.mrerror.one.utils.cdn.RefreshCdn;
import cn.mrerror.one.utils.cdn.tencent.lib.Json.JSONObject;
import cn.mrerror.one.utils.cdn.tencent.lib.Request;

import java.util.TreeMap;

/**
 * 腾讯CDN 地址刷新
 */
public class TencentRefreshCdnImpl implements RefreshCdn {

    //服务器地址
    private static final String TENCENT_CDN_API_SERVER = "cdn.api.qcloud.com";
    //请求API
    private static final String TENCENT_CDN_SERVER_API = "/v2/index.php";
    //全路径刷新
    private static final String REFRESH_URL_ACTION = "RefreshCdnUrl";
    //目录刷新
    private static final String REFRESH_Dir_ACTION = "RefreshCdnDir";
    //默认HTTP请求方式
    private static final String TENCENT_CDN_REQUEST_DEFAULT_METHOD = "GET";

    /**
     * 刷新指定地址
     *
     * @param urls 刷新URL地址集合
     * @return 刷新状态
     */
    @Override
    public boolean refreshCdnUrl(String... urls) {
        TreeMap<String, Object> params = createMap("urls.", urls);
        params.put("Action", REFRESH_URL_ACTION);
        JSONObject result = new JSONObject(
                Request.send(params, Applications.TENCENT_YUN_CDN_API_ID,
                        Applications.TENCENT_YUN_CDN_API_KEY,
                        TENCENT_CDN_REQUEST_DEFAULT_METHOD,
                        TENCENT_CDN_API_SERVER,
                        TENCENT_CDN_SERVER_API,
                        null));
        return "Success".equals(result.getString("codeDesc"));
    }

    private TreeMap createMap(String prefix, String... urls) {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        for (int i = 0; i < urls.length; i++) {
            params.put(prefix + i, urls[i]);
        }
        return params;
    }

    /**
     * 刷新目录
     *
     * @param urls 刷新目录地址集合
     * @return 刷新状态
     */
    @Override
    public boolean refreshCdnDir(String... urls) {
        TreeMap<String, Object> params = createMap("dirs.", urls);
        params.put("Action", REFRESH_Dir_ACTION);
        JSONObject result = new JSONObject(
                Request.send(params, Applications.TENCENT_YUN_CDN_API_ID,
                        Applications.TENCENT_YUN_CDN_API_KEY,
                        TENCENT_CDN_REQUEST_DEFAULT_METHOD,
                        TENCENT_CDN_API_SERVER,
                        TENCENT_CDN_SERVER_API,
                        null));
        return "Success".equals(result.getString("codeDesc"));
    }
}