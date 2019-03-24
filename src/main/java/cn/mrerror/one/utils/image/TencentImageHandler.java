package cn.mrerror.one.utils.image;

import cn.mrerror.one.configuration.Applications;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 腾讯COS 图片对象存储操作
 */
public class TencentImageHandler implements ImageHandler {

    /**
     * 客户端连接对象
     */
    private COSClient cosClient;

    public TencentImageHandler() {
        init();
    }

    private void init() {
        //访问凭证
        COSCredentials cred = new BasicCOSCredentials(Applications.TENCENT_YUN_COR_API_ID, Applications.TENCENT_YUN_COR_API_KEY);
        //客户端配置
        ClientConfig clientConfig = new ClientConfig(new Region(Applications.TENCNET_YUN_COR_REGION));
        //客户端对象
        cosClient = new COSClient(cred, clientConfig);
    }

    public static void main(String[] args) {
//        new TencentImageHandler().uploadImage(new File("C:\\Users\\Administrator\\Desktop\\faf2b2119313b07e93a465b501d7912397dd8c75.jpg"),"/s/a/b/c/d/sfesf.jpg");
//        new TencentImageHandler().removeImage("/s/a/b/c/d/sfesf.jpg");
//        new TencentImageHandler().downloadImage("/s/a/b/c/d/sfesf.jpg","C:\\Users\\Administrator\\Desktop\\tttt.jpg");
    }

    /**
     * 上传文件
     *
     * @param file 本地文件
     * @return 上传状态
     */
    @Override
    public boolean uploadImage(File file) {
        return uploadImage(file, file.getName());
    }

    /**
     * 上传文件
     *
     * @param file          本地文件对象
     * @param subDirAndName 子路径与名字
     * @return 上传状态
     */
    @Override
    public boolean uploadImage(File file, String subDirAndName) {
        //put请求对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(Applications.TENCENT_YUN_COR_IMAGE_BLUCK, subDirAndName, file);
        //发送put请求
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return StringUtils.isNotEmpty(putObjectResult.getETag());
    }

    /**
     * 删除图片
     *
     * @param path 被删除图片的路径
     * @return 返回删除状态
     */
    @Override
    public boolean removeImage(String path) {
        // 指定要删除的 bucket 和对象键
        cosClient.deleteObject(Applications.TENCENT_YUN_COR_IMAGE_BLUCK, path);
        return false;
    }

    /**
     * 下载图片
     *
     * @param remotePath 远程图片地址(不包含ip+端口)
     * @param localPath  本地存储路径
     * @return 下载文件
     */
    @Override
    public File downloadImage(String remotePath, String localPath) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(Applications.TENCENT_YUN_COR_IMAGE_BLUCK, remotePath);
        File file = new File(localPath);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, file);
        return file;
    }
}