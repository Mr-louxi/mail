package com.hx.main;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import com.hx.main.Stream;
import com.sun.mail.pop3.POP3Folder;

public class Pop3 {
	public static boolean flat = true;
	//Э�������û��������룬�ؼ���
	public Pop3(String pro,String u, String p, String key) throws Exception {
		if (pro.trim().equals("")||u.trim().equals("")||p.trim().equals("")||key.trim().equals(""))
			return;
		/**
		 * ���ڵ�ǰ�û���Ŀ¼���洴��һ���ļ�  tmp_mail.txt
		 * �����ļ��Ѵ���  �������Ҳ��Ӱ���Ѵ��ڵ��ļ�
		 */
		String path=System.getProperty("user.home")+"/tmp_mail.txt";//path�Ǿ���·��
		
		File f = new File(path);
		f.createNewFile();
		
		
		
		// ����pop3����������������Э�顢�û���������
		//String pop3Server = "pop3.126.com";
		String pop3Server = pro;
		String protocol = "pop3";
		/*
		 * String user = "hbqxtfwk@126.com"; String pwd = "qxtfwk1302";
		 */
		String user = u;
		String pwd = p;

		// ����һ���о���������Ϣ��Properties����
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		props.setProperty("mail.pop3.host", pop3Server);

		// ʹ��Properties������Session����
		Session session = Session.getInstance(props);
		session.setDebug(false);

		// ����Session������Store���󣬲�����pop3������
		Store store = session.getStore();
		store.connect(pop3Server, user, pwd);

		// ��������ڵ��ʼ���Folder������"ֻ��"��
		Folder folder = store.getFolder("inbox");
		POP3Folder inbox = (POP3Folder) folder;
		folder.open(Folder.READ_ONLY);

		// ����ʼ���Folder�ڵ������ʼ�Message����
		Message[] messages = folder.getMessages();

		// �����ļ�������
		Stream st = new Stream();
		// ���������е�����
		TreeSet<String> mail_set = new TreeSet<String>();
		
		// ��ȡ�ļ������ݾͱ����ж��ļ��Ƿ�Ϊ��
		if(st.fileisEmpty(path)){
			
			int mailCounts1 = messages.length;
			SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			for (int i = mailCounts1 - 1; i > 0; i--) {
				String uid = inbox.getUID(messages[i]);
				System.out.println("UID:" + uid);
				String subject = messages[i].getSubject();
				Date time = messages[i].getSentDate();
				String from = (messages[i].getFrom()[0]).toString();
				System.out.println("�� " + (i + 1) + "���ʼ������⣺" + subject);
				mail_set.add(uid);
			}
			st.saveSet(mail_set, path);
			return;
		}
		TreeSet<String> file_set = st.readSet(path);// -------
		System.out.println("--------" + file_set + "-----------");
	

		int mailCounts = messages.length;
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		for (int i = mailCounts - 1; i > 0; i--) {
			String uid = inbox.getUID(messages[i]);
			System.out.println("UID:" + uid);
			String subject = messages[i].getSubject();
			Date time = messages[i].getSentDate();
			String from = (messages[i].getFrom()[0]).toString();
			System.out.println("�� " + (i + 1) + "���ʼ������⣺" + subject);
			mail_set.add(uid);
			boolean bool = checkKey(key, subject);// ����ʼ����Ƿ�����ؼ���

			if (!file_set.contains(uid) && bool) {
				System.out.println("��Ҫ����");
				// ����
				JOptionPane sm = new JOptionPane();
				
				// ���������̵߳��̳߳أ������ǰ�߳���ִ������ʱͻȻ�жϣ���ᴴ��һ���µ��߳����������ִ������
				ExecutorService singleThreadPool = Executors
						.newSingleThreadExecutor();
				run(singleThreadPool);
				Pop3.flat = true;
				
				sm.showMessageDialog(sm, "����һ����Ҫ�ʼ�", "��ʾ", 3);
				Pop3.flat = false;
				// ����
				/*
				 * while(flag){ Thread.sleep(500);
				 * Toolkit.getDefaultToolkit().beep(); }
				 */
				// ��������֮����uid���뵽file_set�� ����ԭ�����ļ�����
				file_set.add(uid);
				st.deleteFile(path);// ��Դ�ļ�ɾ��
				st.saveSet(file_set,path);// ��������д��

			}

		}
		// st.saveSet(mail_set);
		System.out.println("----------------------");
		System.out.println(st.readSet(path));
		folder.close(false);
		store.close();
	}

	
	
	// ���̵߳�ִ���߼� �������ʼ�ʱ  ԭ�߳���ִֹ��  ���߳̾ͻ�����  ���������ļ�
	private void run(ExecutorService threadPool) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("####" + Pop3.flat);
				while (Pop3.flat) {
					try {
						// ��ȡ��Ƶ������
						AudioInputStream audioInputStream;
						audioInputStream = AudioSystem
								.getAudioInputStream(new File("music/8.wav"));
						// ��ȡ��Ƶ�������
						AudioFormat audioFormat = audioInputStream.getFormat();

						// ������������
						DataLine.Info dataLineInfo = new DataLine.Info(
								SourceDataLine.class, audioFormat,
								AudioSystem.NOT_SPECIFIED);
						SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
								.getLine(dataLineInfo);
						sourceDataLine.open(audioFormat);
						sourceDataLine.start();

						/*
						 * ���������ж�ȡ���ݷ��͵�������
						 */
						int count;
						byte tempBuffer[] = new byte[1024];
						while ((count = audioInputStream.read(tempBuffer, 0,
								tempBuffer.length)) != -1) {
							if (count > 0) {
								sourceDataLine.write(tempBuffer, 0, count);
							}
						}

						// ������ݻ���,���ر�����
						sourceDataLine.drain();
						sourceDataLine.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		threadPool.shutdown();// ����ִ����ϣ��ر��̳߳�

	}

	/**
	 * �ж��ʼ��������Ƿ��йؼ���
	 * 
	 * @param key
	 * @param title
	 * @return
	 */
	public boolean checkKey(String key, String title) {
		String[] str1 = key.split(",");
		for (int i = 0; i < str1.length; i++) {

			if (title.indexOf(str1[i]) > -1)
				return true;
		}
		return false;
	}
	/*
	 * public static void main(String[] args) throws Exception { pop3 p=new
	 * pop3("15907132852@163.com","qqgovmzlcrxvjxrr");
	 * 
	 * }
	 */
	public static void main(String[] args) {
		// ��ȡ��Ƶ������
		AudioInputStream audioInputStream;
		audioInputStream = AudioSystem
				.getAudioInputStream(new File("music/8.wav"));
		// ��ȡ��Ƶ�������
		AudioFormat audioFormat = audioInputStream.getFormat();
		// ������������
		DataLine.Info dataLineInfo = new DataLine.Info(
				SourceDataLine.class, audioFormat,
				AudioSystem.NOT_SPECIFIED);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
				.getLine(dataLineInfo);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();

		/*
		 * ���������ж�ȡ���ݷ��͵�������
		 */
		int count;
		byte tempBuffer[] = new byte[1024];
		while ((count = audioInputStream.read(tempBuffer, 0,
				tempBuffer.length)) != -1) {
			if (count > 0) {
				sourceDataLine.write(tempBuffer, 0, count);
			}
		}

		// ������ݻ���,���ر�����
		sourceDataLine.drain();
		sourceDataLine.close();
	}
}