package cn.mrerror.one.utils;

import cn.mrerror.one.configuration.Applications;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

public class TencentSMS {

    public void sendMessage(String phonenumbers[], int templateId, String[] params) {
        try {
            params = new String[]{"5678"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(Applications.TENCENT_SMS_APPID, Applications.TENCENT_SMS_APPKEY);
            for (int i = 0; i < phonenumbers.length; i++) {
                SmsSingleSenderResult result = ssender.sendWithParam("86", phonenumbers[i],
                        templateId, params, "", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.println(result);
            }
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }
}
