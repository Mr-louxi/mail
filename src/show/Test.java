package show;

import javax.swing.*;

import com.hx.main.Main;






import com.hx.main.Pop3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

public class Test extends JFrame implements ActionListener

{

	GridBagLayout g = new GridBagLayout();

	GridBagConstraints c = new GridBagConstraints();

	Test(String str)

	{

		super(str);

		setSize(350, 220);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(g);

		// 调用方法

		addComponent();

		
	//	submit.addActionListener(this);

		setVisible(true);

		setLocationRelativeTo(null);// 设居中显示;

	}

	// 在这个方法中将会添加所有的组件;



	public void addComponent()

	{

		// 邮箱检测

	/*	noteInformation = new JLabel("邮箱：");

		add(g, c, noteInformation, 0, 0, 1, 1);*/

		//协议  用126  还是163默认126
		protocol=new JLabel("协  议：");
		add(g,c,protocol,0,0,1,1);
		protocol_text=new JTextField(25);
		protocol_text.setText("pop3.126.com");
		add(g,c,protocol_text,1,0,1,1);
		
		// 用户名

		userName = new JLabel("用户名：");

		add(g, c, userName, 0, 1, 1, 1);

		// 用户名输入框

		textUserName = new JTextField(25);

		//textUserName.setText("15907132852@163.com");
		textUserName.setText("hbqxtfwk@126.com");
		add(g, c, textUserName, 1, 1, 2, 1);

		// 密码：

		password =new JLabel("密  码：");
		add(g, c, password, 0, 2, 1, 1);

		// 密码输入框

		textUserPassword = new JPasswordField(25);
		//textUserPassword.setText("qyaotnsvqvhgayow");
		textUserPassword.setText("");

		add(g, c, textUserPassword, 1, 2, 2, 1);
		//关键字
		keyWord = new JLabel("关键字：");

		add(g, c, keyWord, 0,3, 1, 1);
		//关键字输入框
		textKeyWord=new JTextField(25);
		add(g, c, textKeyWord, 1, 3, 2, 1);
		
		//技术支持
		tel1=new JLabel("联系电话：");
		add(g,c,tel1,0,7,1,1);
		//技术支持电话
		tel2=new JLabel("027-87872268");
		add(g,c,tel2,1,7,2,1);
		
		//时间间隔
		un1=new JLabel("时间间隔：");
		add(g,c,un1,0,4,1,1);
		
		String[] str={"15分钟","10分钟","5分钟","1分钟"};
		change = new JComboBox(str);
		add(g,c,change,1,4,2,1);
		
		//
		un2=new JLabel(" ");
		add(g,c,un2,0,5,1,1);
		//公司信息
		info1=new JLabel("技术支持：");
		add(g,c,info1,0,6,1,1);
		info2=new JLabel("武汉华信联创技术工程有限公司");
		add(g,c,info2,1,6,1,1);
		//info.setBounds(100, 100, 100, 25); 
		//add(info);
		
		
		
	
	}

	

	public void add(GridBagLayout g, GridBagConstraints c, JComponent jc,
			int x, int y, int gw, int gh)

	{

		c.gridx = x;

		c.gridy = y;

		c.anchor = GridBagConstraints.WEST;

		c.gridwidth = gw;

		c.gridheight = gh;

		g.setConstraints(jc, c);

		add(jc);

	}

	public static void main(String args[])

	{
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//String lookAndFeel = UIManager.getSystemLookAndFeelClassName();//获取当前系统的风格
			//UIManager.setLookAndFeel(lookAndFeel);
			String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		
		final Test test= new Test("邮件监控报警程序");
		Timer tt=new Timer();//定时器类
		//指定定时器1分钟执行一次  当下拉框中的数据改变时  使目前线程睡眠相应时间来达到改变时间间隔的目的
		
        tt.schedule(new TimerTask(){
        	
 			@Override
 			public void run() {
 				
 				try {
 					String userName=textUserName.getText();//用户名
 					String protocol_t=protocol_text.getText();//获取协议名
 					 String pw = String.valueOf( textUserPassword.getPassword());//将char【】 转成String
 					 String time=(String) change.getSelectedItem();
 					 
 					 int t=Integer.parseInt(time.replace("分钟", " ").trim());
 					 System.out.println(t);
 					switch (t) {
					case 1:break;
					case 5:Thread.sleep(1000*60*4);break;
					case 10:Thread.sleep(1000*60*9);break;
					case 15:Thread.sleep(1000*60*14);break;
					default:
						break;
					}
 					 
 					String key=textKeyWord.getText();
 					System.out.println("******"+userName+"*********"+protocol_t+"*********"+pw+"********"+key+"*******"+time);
 					Pop3 p=new Pop3(protocol_t,userName,pw,key);
 				} catch (Exception e) {
 					//出现异常弹窗
 					JOptionPane sm = new JOptionPane();
 					System.out.println(e.getMessage());
 					sm.showMessageDialog(sm, getStackTrace(e), "提示", 3);
 					//e.printStackTrace();
 				}
 				
 				
 			}
         	
         },0,1000*60);

	}

	JLabel noteInformation, userName,keyWord,info1,info2,tel1,tel2,un1,un2,protocol;
	JLabel password;

	JLabel sex, birthday;

	static JTextField textUserName,protocol_text;

	static JPasswordField textUserPassword;

	static JTextField textKeyWord;
	static JComboBox change;
	static int time_Interval;
	JButton submit;

	JTextArea result;
	public static String getStackTrace(Exception t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }


	@Override
	public void actionPerformed(ActionEvent arg0)

	{

		String s = textUserName.getText();

		String t =String.valueOf( textUserPassword.getPassword());//将char【】 转成String

		String kw=textKeyWord.getText();
		

	

		

	}

}