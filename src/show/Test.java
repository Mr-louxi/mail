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

		// ���÷���

		addComponent();

		
	//	submit.addActionListener(this);

		setVisible(true);

		setLocationRelativeTo(null);// �������ʾ;

	}

	// ����������н���������е����;



	public void addComponent()

	{

		// ������

	/*	noteInformation = new JLabel("���䣺");

		add(g, c, noteInformation, 0, 0, 1, 1);*/

		//Э��  ��126  ����163Ĭ��126
		protocol=new JLabel("Э  �飺");
		add(g,c,protocol,0,0,1,1);
		protocol_text=new JTextField(25);
		protocol_text.setText("pop3.126.com");
		add(g,c,protocol_text,1,0,1,1);
		
		// �û���

		userName = new JLabel("�û�����");

		add(g, c, userName, 0, 1, 1, 1);

		// �û��������

		textUserName = new JTextField(25);

		//textUserName.setText("15907132852@163.com");
		textUserName.setText("hbqxtfwk@126.com");
		add(g, c, textUserName, 1, 1, 2, 1);

		// ���룺

		password =new JLabel("��  �룺");
		add(g, c, password, 0, 2, 1, 1);

		// ���������

		textUserPassword = new JPasswordField(25);
		//textUserPassword.setText("qyaotnsvqvhgayow");
		textUserPassword.setText("");

		add(g, c, textUserPassword, 1, 2, 2, 1);
		//�ؼ���
		keyWord = new JLabel("�ؼ��֣�");

		add(g, c, keyWord, 0,3, 1, 1);
		//�ؼ��������
		textKeyWord=new JTextField(25);
		add(g, c, textKeyWord, 1, 3, 2, 1);
		
		//����֧��
		tel1=new JLabel("��ϵ�绰��");
		add(g,c,tel1,0,7,1,1);
		//����֧�ֵ绰
		tel2=new JLabel("027-87872268");
		add(g,c,tel2,1,7,2,1);
		
		//ʱ����
		un1=new JLabel("ʱ������");
		add(g,c,un1,0,4,1,1);
		
		String[] str={"15����","10����","5����","1����"};
		change = new JComboBox(str);
		add(g,c,change,1,4,2,1);
		
		//
		un2=new JLabel(" ");
		add(g,c,un2,0,5,1,1);
		//��˾��Ϣ
		info1=new JLabel("����֧�֣�");
		add(g,c,info1,0,6,1,1);
		info2=new JLabel("�人�������������������޹�˾");
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
			//String lookAndFeel = UIManager.getSystemLookAndFeelClassName();//��ȡ��ǰϵͳ�ķ��
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
	
		
		
		final Test test= new Test("�ʼ���ر�������");
		Timer tt=new Timer();//��ʱ����
		//ָ����ʱ��1����ִ��һ��  ���������е����ݸı�ʱ  ʹĿǰ�߳�˯����Ӧʱ�����ﵽ�ı�ʱ������Ŀ��
		
        tt.schedule(new TimerTask(){
        	
 			@Override
 			public void run() {
 				
 				try {
 					String userName=textUserName.getText();//�û���
 					String protocol_t=protocol_text.getText();//��ȡЭ����
 					 String pw = String.valueOf( textUserPassword.getPassword());//��char���� ת��String
 					 String time=(String) change.getSelectedItem();
 					 
 					 int t=Integer.parseInt(time.replace("����", " ").trim());
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
 					//�����쳣����
 					JOptionPane sm = new JOptionPane();
 					System.out.println(e.getMessage());
 					sm.showMessageDialog(sm, getStackTrace(e), "��ʾ", 3);
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

		String t =String.valueOf( textUserPassword.getPassword());//��char���� ת��String

		String kw=textKeyWord.getText();
		

	

		

	}

}