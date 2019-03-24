package cn.mrerror.one.jdtl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.UUID;

/**
 * 重复提交令牌标签
 * 原理：跟公司领取福利性质一致
 */
public class TokenRepeat extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        //获得上下文
        PageContext page = (PageContext) this.getJspContext();
        //生成UUID
        String uuid = UUID.randomUUID().toString();
        //将UUID存入当前会话
        page.getSession().setAttribute("token", uuid);
        //将UUID通过隐藏域标签写入
        page.getOut().write(String.format("<input type=\"hidden\" name=\"token\" value=\"%s\" />",uuid));
    }
}
