package cn.mrerror.one.utils.cdn.tencent.lib;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 签名
 */
public class Sign {
    // 编码方式
    private static final String CONTENT_CHARSET = "UTF-8";

    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    /**
     * @param signStr 被加密串
     * @param secret  加密密钥
     * @return
     */
    public static String sign(String signStr, String secret)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        String sig = null;
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());

        mac.init(secretKey);
        byte[] hash = mac.doFinal(signStr.getBytes(CONTENT_CHARSET));
        sig = new String(Base64.encode(hash));

        return sig;
    }

    public static String makeSignPlainText(TreeMap<String, Object> requestParams, String requestMethod, String requestHost, String requestPath) {

        String retStr = "";
        retStr += requestMethod;
        retStr += requestHost;
        retStr += requestPath;
        retStr += buildParamStr1(requestParams, requestMethod);

        return retStr;
    }

    protected static String buildParamStr1(TreeMap<String, Object> requestParams, String requestMethod) {
        return buildParamStr(requestParams, requestMethod);
    }

    protected static String buildParamStr(TreeMap<String, Object> requestParams, String requestMethod) {

        String retStr = "";
        for (String key : requestParams.keySet()) {
            //排除上传文件的参数
            if (requestMethod == "POST" && requestParams.get(key).toString().substring(0, 1).equals("@")) {
                continue;
            }
            if (retStr.length() == 0) {
                retStr += '?';
            } else {
                retStr += '&';
            }
            retStr += key.replace("_", ".") + '=' + requestParams.get(key).toString();

        }
        return retStr;
    }
}
