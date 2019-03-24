package cn.mrerror.one.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailSend {
	
	public static void sentSimpleContent(String hostname,String username,String password,String subject,String toUser,StringBuilder msg) throws Exception {

		SimpleEmail email = new SimpleEmail();
		
		addBaseMsg(email, hostname, username, password, subject, toUser);
		
		email.setMsg(msg.toString());
		
		email.send();
	}
	
	public final static void addBaseMsg(Email email,String hostname,String username,String password,String subject,String toUser) throws Exception {
		email.setHostName(hostname);
		email.setAuthentication(username, password);// 邮件服务器验证：用户名/密码
		email.setCharset("UTF-8");// 必须放在前面，否则乱码
		email.setFrom(username);//发送人
		email.addTo(toUser);//接收人
		email.setSubject(subject);//主题
	}
	
	public static void sendHTMLContent(String hostname,String username,String password,String subject,String toUser,StringBuilder msg,String alertMsg) throws Exception {
		HtmlEmail htmlEmail = new HtmlEmail();
				  
		addBaseMsg(htmlEmail, hostname, username, password, subject, toUser);
				  
		//发送内容
		htmlEmail.setHtmlMsg(msg.toString());
		
		//当客户端不支持的时候提示此消息
		htmlEmail.setTextMsg(alertMsg);
		
		htmlEmail.send();
	}
	
	public static void sendAttachmentContent(String hostname,String username,String password,String subject,String toUser,StringBuilder msg,EmailAttachment... attachments) throws Exception {
				
		MultiPartEmail  mutilEmail = new MultiPartEmail();
		
		addBaseMsg(mutilEmail, hostname, username, password, subject, toUser);
				
		for(EmailAttachment attachment :attachments) {
			mutilEmail.attach(attachment);
		}
		
		mutilEmail.setMsg(msg.toString());

		mutilEmail.send();
	}


	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

		String hostname="SMTP.126.com";
		String username="mxhbetter@126.com";
		String password="Error54946";
		String subject="开发测试";
		String toUser="18842081870@163.com";


		//msgInfo.append  添加邮件内容
		StringBuilder   msgInfo = new StringBuilder();//内容
						msgInfo.append("测试时间：").append(sdf.format(new Date()));
		
		sentSimpleContent(hostname,username,password,subject,toUser,msgInfo);
		
		String alertMsg="客户端不支持HTML";
		
		msgInfo.setLength(0);
		msgInfo.append("<html>美女一枚 - <img src=\"http://a.hiphotos.baidu.com/image/pic/item/4a36acaf2edda3ccc4a53e450ce93901213f9216.jpg\"></html>");
		
		sendHTMLContent(hostname, username, password, subject, toUser, msgInfo, alertMsg);
		
		EmailAttachment attachment = new EmailAttachment();
						attachment.setPath("C:\\Users\\18842\\Pictures\\77.jpg");
						attachment.setDisposition(EmailAttachment.ATTACHMENT);
						attachment.setDescription("身份图片信息");
						attachment.setName("请求方式.png");
						
	   sendAttachmentContent(hostname,username,password,subject,toUser,msgInfo,attachment,attachment);

	}

}
