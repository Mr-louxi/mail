
    package com.hx.imap;  
      
    import java.util.Properties;  
      

    import javax.mail.Flags;  
import javax.mail.Folder;  
import javax.mail.Message;  
import javax.mail.Session;  
      

    import com.sun.mail.imap.IMAPFolder;  
import com.sun.mail.imap.IMAPStore;  
      
    /** 
     * ʹ��imapЭ���ȡδ���ʼ��� 
     *  
     * @author w 
     *  
     */  
    public class Email3 {  
      
        public static void main(String[] args) throws Exception {  
         /*   String user = "username@sohu.com";// ������û���  
            String password = "password"; // ���������  
          
*/          
        	// ����imap����������������Э�顢�û���������  
    	    String imapServer = "imap.163.com";  
    	    String protocol = "imap"; 
        	String user = "15907132852@163.com";// ������û���  
            String password = "qqgovmzlcrxvjxrr"; // ���������  
      
            // ����һ���о���������Ϣ��Properties����  
            Properties prop = System.getProperties();  
            prop.put("mail.store.protocol", "imap");  
            prop.put("mail.imap.host", "imap.163.com");  
      
            Session session = Session.getInstance(prop);  
              
            int total = 0;  
            IMAPStore store = (IMAPStore) session.getStore("imap"); // ʹ��imap�Ự���ƣ����ӷ�����  
            store.connect(user, password);  
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // �ռ���  
            folder.open(Folder.READ_WRITE);  
            // ��ȡ���ʼ���  
            total = folder.getMessageCount();  
            System.out.println("-----------------�����ʼ���" + total  
                    + " ��--------------");  
            // �õ��ռ����ļ�����Ϣ����ȡ�ʼ��б�  
            System.out.println("δ���ʼ�����" + folder.getUnreadMessageCount());  
            Message[] messages = folder.getMessages();  
            int messageNumber = 0;  
            for (Message message : messages) {  
                System.out.println("����ʱ�䣺" + message.getSentDate());  
                System.out.println("���⣺" + message.getSubject());  
                System.out.println("���ݣ�" + message.getContent());  
                Flags flags = message.getFlags();  
                if (flags.contains(Flags.Flag.SEEN))  
                    System.out.println("����һ���Ѷ��ʼ�");  
                else {  
                    System.out.println("δ���ʼ�");  
                }  
                System.out  
                        .println("========================================================");  
                System.out  
                        .println("========================================================");  
                //ÿ���ʼ�����һ��MessageNumber������ͨ���ʼ���MessageNumber���ռ�������ȡ�ø��ʼ�  
                messageNumber = message.getMessageNumber();  
            }  
            Message message = folder.getMessage(messageNumber);  
            System.out.println(message.getContent()+message.getContentType());  
            // �ͷ���Դ  
            if (folder != null)  
                folder.close(true);   
            if (store != null)  
                store.close();  
        }  
      
    }  
