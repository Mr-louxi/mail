package com.hx.main;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.pop3.POP3Folder;

public class Main {  
    public static void main(String[] args) throws Exception {  
        // 连接pop3服务器的主机名、协议、用户名、密码  
        String pop3Server = "pop3.163.com";  
        String protocol = "pop3";  
        String user = "15907132852@163.com";  
        String pwd = "qqgovmzlcrxvjxrr";  
          
        // 创建一个有具体连接信息的Properties对象  
        Properties props = new Properties();  
        props.setProperty("mail.store.protocol", protocol);  
        props.setProperty("mail.pop3.host", pop3Server);  
          
        // 使用Properties对象获得Session对象  
        Session session = Session.getInstance(props);  
        session.setDebug(false);  
          
        // 利用Session对象获得Store对象，并连接pop3服务器  
        Store store = session.getStore();  
        store.connect(pop3Server, user, pwd);  
          
        // 获得邮箱内的邮件夹Folder对象，以"只读"打开  
        Folder folder = store.getFolder("inbox");  
        POP3Folder inbox = (POP3Folder) folder;
        folder.open(Folder.READ_ONLY);  
          
        // 获得邮件夹Folder内的所有邮件Message对象  
        Message [] messages = folder.getMessages();  
          
        int mailCounts = messages.length;  
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        for(int i = mailCounts-1; i > mailCounts - 10; i--) {  
        	String uid = inbox.getUID(messages[i]);
        	System.out.println("UID:"+uid);
            String subject = messages[i].getSubject();  
            Date time = messages[i].getSentDate();  
            String from = (messages[i].getFrom()[0]).toString();  
            System.out.println("第 " + (i+1) + "封邮件的主题：" + subject);  
            //报警
            Toolkit.getDefaultToolkit().beep();
            System.out.println("第 " + (i+1) + "封邮件的发件人地址：" + from);  
            System.out.println("第 " + (i+1) + "封邮件的发件时间：" + sdf.format(time).toString());  
           
        }  
        folder.close(false);  
        store.close();  
    }  
} 