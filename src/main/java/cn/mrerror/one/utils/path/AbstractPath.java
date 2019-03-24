package cn.mrerror.one.utils.path;

/**
 * 路径生成抽象类
 */
public abstract class AbstractPath implements Path {

    private final char hexDigits[] = {'0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f'};

    public AbstractPath() {
    }

    /**
     * 转十六进制
     *
     * @param md 二进制内容
     * @return 十六进制字符串
     */
    protected String toHexString(byte[] md) {
        int j = md.length;
        char str[] = new char[j * 2];
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[2 * i] = hexDigits[byte0 >>> 4 & 0xf];
            str[i * 2 + 1] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
