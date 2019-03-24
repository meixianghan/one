package cn.mrerror.one.utils.path;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * md5路径算法
 */
public class Md5Path extends AbstractPath {

    private static final Logger logger = Logger.getLogger(Md5Path.class);

    /**
     * 生成路径
     *
     * @param content 需要转换路径的内容
     * @return 路径
     */
    @Override
    public String generatorPath(String content) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(content.getBytes());
            return changeToMd5Path(toHexString(mdTemp.digest()));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 生成路径
     *
     * @param content 需要转换路径的内容
     * @param suffer  后缀
     * @return 路径
     */
    @Override
    public String generatorPath(String content, String suffer) {
        return generatorPath(content).concat(suffer);
    }


    /**
     * 生成 /A/B/C/D/E/F/ABCDEF 文件路径
     *
     * @param path 路径内容
     * @return 目录结构路径结果
     */
    private String changeToMd5Path(String path) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (char c : path.toCharArray()) {
            if (i % 4 == 0) {
                sb.append("/");
            }
            sb.append(c);
            i++;
        }
        sb.append(path);
        return sb.toString();
    }
}