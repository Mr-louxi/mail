package com.hx.imap;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 * @author lx
 *
 */
public class SendMail {
	public static void smtpSend(){  
        try {  
        	
    	  
        	String server = "smtp.163.com"; 
            Properties properties = new Properties();  
            properties.setProperty("mail.transport.protocol", "smtp");// 协议名称
            properties.setProperty("mail.smtp.auth", "true");// 
            properties.setProperty("mail.smtp.port", "25");  
            properties.setProperty("mail.smtp.host", server);// 协议对应的服务器地址  
            properties.setProperty("mail.debug", "true");//
            final String username_send = "";//发件人 邮箱
            final String password_send = "";  //发件人密码
            final String username_receive = "745720754@qq.com";//收件人邮箱
            // 后台输出邮件发送的过程  
            Session session = Session.getInstance(properties,  
                    new Authenticator() {  
                        protected PasswordAuthentication getPasswordAuthentication() {  
                            return new PasswordAuthentication(username_send,  
                            		password_send);  
                        }  
                    });  
            // 邮件信息 
            Message messgae = new MimeMessage(session);  
            messgae.setFrom(new InternetAddress(username_send));// 设置发送人   
            messgae.setText("X5O!P%@AP[4PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*");// 设置邮件内容    
            messgae.setSubject("来邮件了");// 设置邮件主题 
            // 发送邮件 
            Transport tran = session.getTransport();  
            tran.connect(server, username_send, password_send);// 连接到发件人邮箱服务器    
            tran.sendMessage(messgae, new Address[] { new InternetAddress(username_receive) });// 设置邮件接收人   
            tran.close();  
        } catch (Exception e) {  
        }  
    }  
	
	public static void main(String[] args) {
		smtpSend();
	}
}
