package cn.mrerror.one.configuration;

/**
 * 应用程序配置信息
 */
public class Applications {

    /**
     * 会话用户名标记
     */
    public static final String SESSION_USERNAME ="sessionName";

    /**
     * 平台默认用户名
     */
    public static final String PLATEFORM_DEFAULT_USER_NAME="mrerror";

    /**
     * 通过 浏览器访问的平台
     */
    public static final String PLATFORM_WEB="web";

    /**
     * 通过 移动客户端访问的平台
     */
    public static final String PLATFORM_API="api";

    public static final String EXCEPTION_501_CODE="501";

    public static final String EXCEPTION_501 ="服务器内部错误";

    /**
     * memcached 会话中的key值过期 30分钟
     */
    public static final int MEMCACHED_COOKIE_EXPTIME = 60*30;

    /**
     * 缓存时效 1天
     */
    public static final int MEMCACHED_EXP_DAY = 3600 * 24;

    /**
     * 缓存时效 1周
     */
    public static final int MEMCACHED_EXP_WEEK = 3600 * 24 * 7;

    /**
     * 缓存时效 永久
     */
    public static final int MEMCACHED_EXP_FOREVER = 0;

    /**
     * 平台默认COOKIE域名
     */
    public static final String PLATFORM_DEFAULT_COOKIE_DOMAIN=".mrerror.cn";

    /**
     * 平台默认COOKIE路径
     */
    public static final String PLATFORM_DEFAULT_COOKIE_PATH="/";

    /**
     * 平台集群会话key
     */
    public static final String PLATFORM_CROSS_SESSION_ID="mrerror_id";

    /**
     * memcached 地址
     */
    public static final String MEMCACHED_ADDRESS="188.131.134.186";

    /**
     * memcached 端口
     */
    public static final int MEMCACHED_PORT=11211;

    /**
     * 腾讯云COS访问ID
     */
    public static final String TENCENT_YUN_COR_API_ID ="AKIDd7vkj6KR8b3TxVgguDgwUwrNkPf1phnL";
//    public static final String TENCENT_YUN_COR_API_ID ="";

    /**
     * 腾讯云COS访问KEY
     */
    public static final String TENCENT_YUN_COR_API_KEY="hqvpDekngQNrQvVDxcAk4THvWFoD9fHu";
//    public static final String TENCENT_YUN_COR_API_KEY="";

    /**
     * 腾讯云COS 所在地区REGION区域
     */
    public static final String TENCNET_YUN_COR_REGION="ap-beijing";

    /**
     * 腾讯云COS 图片所在的桶名称
     */
    public static final String TENCENT_YUN_COR_IMAGE_BLUCK="img-1257594128";

    /**
     * 腾讯云CDN秘钥编号,通过这个来区分是哪一家企业
     */
    public static final String TENCENT_YUN_CDN_API_ID="AKIDd7vkj6KR8b3TxVgguDgwUwrNkPf1phnL";
//    public static final String TENCENT_YUN_CDN_API_ID="";
    /**
     * 腾讯云CDN秘钥, 处理数据加密
     */
    public static final String TENCENT_YUN_CDN_API_KEY="hqvpDekngQNrQvVDxcAk4THvWFoD9fHu";
//    public static final String TENCENT_YUN_CDN_API_KEY="";

    /**
     * TENCENT_SMS 短信服务id
     */
    public static final int TENCENT_SMS_APPID = 1400141396;

    /**
     * TENCENT_SMS 短信服务密钥
     */
    public static final String TENCENT_SMS_APPKEY = "bc6acdc98d31fbc7c22100ee4d704587";

}