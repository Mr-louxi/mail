package com.hx.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;
import java.util.Vector;
 
public class Stream {
  //  static TreeSet<String> v = new TreeSet<String>();
//  static Vector<String> v = new Vector<String>();
	//��һ��set����д�뵽�ļ���
	public void saveSet(TreeSet<String> v,String path) throws IOException{//set�����д�����ʼ���uid
		
		File f = new File(path);
	    /* f.mkdirs();
	     f = new File("io/temp.txt");
	     f.createNewFile();*/
	     FileOutputStream fos=new FileOutputStream(f);
	     ObjectOutputStream oos=new ObjectOutputStream(fos);
	     oos.writeObject(v);//������д����
	     oos.close();
	}
	@SuppressWarnings({ "unchecked", "resource" })
	/**
	 * ��ȡָ��Ŀ¼���ļ�
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public TreeSet<String> readSet(String path) throws IOException{
		File f=new File(path);
		FileInputStream fis=new FileInputStream(f);
		ObjectInputStream ois=new ObjectInputStream(fis);
		TreeSet<String> treeset=null;
		try {
			treeset = (TreeSet<String>)ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return treeset;
		
	}
	//ɾ���ļ� ɾ��ָ��Ŀ¼���ļ�
	public void deleteFile(String path){
		//File f=new File("io/temp.txt");
		File f=new File(path);
		if(f.exists())f.delete();
	}
	public boolean fileisEmpty(String path) throws IOException{
		/*FileInputStream fis=new FileInputStream("io/temp.txt");*/
		FileInputStream fis=new FileInputStream(path);
		byte[] by=new byte[1000];
        int size=fis.available();
        if(size==0){
           return true;//�ļ�Ϊ��
        }else{
           return false;//�ļ���Ϊ��
        }
				
	}
   public static void main(String[] args) throws IOException {
	/*   File f = new File("io");
	     f.mkdirs();
	     f = new File("io/temp.txt");
	     f.createNewFile(); */
	   String path=System.getProperty("user.home");
	   System.out.println(path);
	   File f = new File(path+"/ttt.txt");
	   f.createNewFile();
	     System.out.println("ok");
	     //ȡ�ø�Ŀ¼·��  
	 
}
  
   
}