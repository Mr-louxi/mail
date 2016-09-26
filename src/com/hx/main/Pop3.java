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
	//协议名，用户名，密码，关键字
	public Pop3(String pro,String u, String p, String key) throws Exception {
		if (pro.trim().equals("")||u.trim().equals("")||p.trim().equals("")||key.trim().equals(""))
			return;
		/**
		 * 先在当前用户的目录下面创建一个文件  tmp_mail.txt
		 * 若该文件已存在  下面代码也不影响已存在的文件
		 */
		String path=System.getProperty("user.home")+"/tmp_mail.txt";//path是绝对路径
		
		File f = new File(path);
		f.createNewFile();
		
		
		
		// 连接pop3服务器的主机名、协议、用户名、密码
		//String pop3Server = "pop3.126.com";
		String pop3Server = pro;
		String protocol = "pop3";
		/*
		 * String user = "hbqxtfwk@126.com"; String pwd = "qxtfwk1302";
		 */
		String user = u;
		String pwd = p;

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
		Message[] messages = folder.getMessages();

		// 保存文件的数据
		Stream st = new Stream();
		// 保存邮箱中的数据
		TreeSet<String> mail_set = new TreeSet<String>();
		
		// 读取文件的内容就必须判断文件是否为空
		if(st.fileisEmpty(path)){
			
			int mailCounts1 = messages.length;
			SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			for (int i = mailCounts1 - 1; i > 0; i--) {
				String uid = inbox.getUID(messages[i]);
				System.out.println("UID:" + uid);
				String subject = messages[i].getSubject();
				Date time = messages[i].getSentDate();
				String from = (messages[i].getFrom()[0]).toString();
				System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
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
			System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
			mail_set.add(uid);
			boolean bool = checkKey(key, subject);// 检测邮件中是否包含关键字

			if (!file_set.contains(uid) && bool) {
				System.out.println("需要弹窗");
				// 弹窗
				JOptionPane sm = new JOptionPane();
				
				// 创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替代它继续执行任务
				ExecutorService singleThreadPool = Executors
						.newSingleThreadExecutor();
				run(singleThreadPool);
				Pop3.flat = true;
				
				sm.showMessageDialog(sm, "您有一封重要邮件", "提示", 3);
				Pop3.flat = false;
				// 报警
				/*
				 * while(flag){ Thread.sleep(500);
				 * Toolkit.getDefaultToolkit().beep(); }
				 */
				// 弹窗完了之后将新uid加入到file_set中 并将原来的文件覆盖
				file_set.add(uid);
				st.deleteFile(path);// 将源文件删除
				st.saveSet(file_set,path);// 将新数据写入

			}

		}
		// st.saveSet(mail_set);
		System.out.println("----------------------");
		System.out.println(st.readSet(path));
		folder.close(false);
		store.close();
	}

	
	
	// 新线程的执行逻辑 当来新邮件时  原线程中止执行  该线程就会启动  播放音乐文件
	private void run(ExecutorService threadPool) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("####" + Pop3.flat);
				while (Pop3.flat) {
					try {
						// 获取音频输入流
						AudioInputStream audioInputStream;
						audioInputStream = AudioSystem
								.getAudioInputStream(new File("music/8.wav"));
						// 获取音频编码对象
						AudioFormat audioFormat = audioInputStream.getFormat();

						// 设置数据输入
						DataLine.Info dataLineInfo = new DataLine.Info(
								SourceDataLine.class, audioFormat,
								AudioSystem.NOT_SPECIFIED);
						SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
								.getLine(dataLineInfo);
						sourceDataLine.open(audioFormat);
						sourceDataLine.start();

						/*
						 * 从输入流中读取数据发送到混音器
						 */
						int count;
						byte tempBuffer[] = new byte[1024];
						while ((count = audioInputStream.read(tempBuffer, 0,
								tempBuffer.length)) != -1) {
							if (count > 0) {
								sourceDataLine.write(tempBuffer, 0, count);
							}
						}

						// 清空数据缓冲,并关闭输入
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
		threadPool.shutdown();// 任务执行完毕，关闭线程池

	}

	/**
	 * 判断邮件标题中是否含有关键字
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
		// 获取音频输入流
		AudioInputStream audioInputStream;
		audioInputStream = AudioSystem
				.getAudioInputStream(new File("music/8.wav"));
		// 获取音频编码对象
		AudioFormat audioFormat = audioInputStream.getFormat();
		// 设置数据输入
		DataLine.Info dataLineInfo = new DataLine.Info(
				SourceDataLine.class, audioFormat,
				AudioSystem.NOT_SPECIFIED);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem
				.getLine(dataLineInfo);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();

		/*
		 * 从输入流中读取数据发送到混音器
		 */
		int count;
		byte tempBuffer[] = new byte[1024];
		while ((count = audioInputStream.read(tempBuffer, 0,
				tempBuffer.length)) != -1) {
			if (count > 0) {
				sourceDataLine.write(tempBuffer, 0, count);
			}
		}

		// 清空数据缓冲,并关闭输入
		sourceDataLine.drain();
		sourceDataLine.close();
	}
}