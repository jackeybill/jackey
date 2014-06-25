/**
Download by http://www.codefans.net 
 */
package com.tf.mobile;
//import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
/**
 * @author aMeng
 *
 */
public class DBUtil{

	/**
	 * 
	 */
	private static String driver="sun.jdbc.odbc.JdbcOdbcDriver";//�����������ַ���
	//�������ݿ������ַ���
	private static String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=db/mydb.mdb";
	private static Connection con=null;//�������ݿ����Ӷ�������
	private static Statement stat=null;//��������������
	private static PreparedStatement psInsert=null;//����Ԥ��������������
	private static ResultSet rs=null;//�����������������
	public DBUtil() {
		// TODO �Զ����ɹ��캯�����
	}
	public static Connection getConnection()//�õ����ݿ����ӵķ���
	{
		try
		{
			Class.forName(driver);//����������
			con=DriverManager.getConnection(url);//�õ�����
		}
		catch(Exception e){e.printStackTrace();}
		return con;//��������
	}
	public static void closeCon()//�ر����ݿ����ӵķ���
	{
		try
		{
			if(rs!=null){rs.close(); rs=null;}//����������Ϊ�չرս��������ֵnull
			if(stat!=null){stat.close(); stat=null;}//���������Ϊ�չر������󲢸�ֵnull
			if(con!=null){con.close(); con=null;}//������Ӳ�Ϊ�չر����Ӳ���ֵnull				
		}
		catch(Exception e){e.printStackTrace();}
	}
	public static boolean check(String user,String pwd)//��½��֤
	{
		boolean flag=false;
		try
		{   
			con=getConnection();//�õ����ݿ�����
			stat=con.createStatement();//����������
			rs=stat.executeQuery("select password,u_role from user where user_name='"+user+"'");
			rs.next();
			String spwd=rs.getString(1);//�õ�����
			if(spwd.equals(pwd))
			{
				flag=true;//����ƥ�䣬��½�ɹ�
				SingletonClient.currentRole = rs.getString("u_role");
			}			
		}
		catch(Exception e)
		{	
			flag=false;//���κ��쳣��������½ʧ��
		}
		finally{closeCon();}//�ر����ݿ�����
		return flag;
	}
	public static int update(String sql)//�������ݿ�
	{
		int count=0;//��������ֵ
		try
		{
			con=getConnection();//�õ����ݿ�����
			stat=con.createStatement();//����������
			count=stat.executeUpdate(sql);//ִ�и���		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			count=-1;//����ʧ�ܷ���ֵΪ-1
		}
		finally{closeCon();}//�ر����ݿ�����	
		return count;//���ؽ��
	}
	public static boolean isExist(String sql)//ĳ����¼�Ƿ����
	{
		boolean flag=false;//���÷���ֵ
		try
		{   
			con=getConnection();//�õ����ݿ�����
			stat=con.createStatement();//����������
			rs=stat.executeQuery(sql);//ִ�в�ѯ
			if(rs.next())
			{
				flag=true;//���ڣ����÷���ֵΪtrue
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();		
			flag=false;//�����κ��쳣���÷��ؽ��Ϊfalse
		}
		finally{closeCon();}//�ر����ݿ�����
		return flag;//���ؽ��
	}
	public static Vector getTable(String sql){
		Vector str = new Vector();
		Vector vector = new Vector();
		try{
			con = getConnection();
			stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(sql);
			java.sql.ResultSetMetaData rsm = rs.getMetaData();
			int col = rsm.getColumnCount();
			while(rs.next()){
				vector.clear();
				for(int j=1;j<=col;j++){
						vector.add(rs.getObject(j));
				}
				str.add(vector);
			}
		}catch(SQLException e){e.printStackTrace();}
		finally{closeCon();}
		return str;
	}
	public static Object[][] getData(String sql){
		Object data[][] = null;
		try{
			con = getConnection();
			stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stat.executeQuery(sql);
			rs.last();
			int row = rs.getRow();
			rs.beforeFirst();
			java.sql.ResultSetMetaData rsm = rs.getMetaData();
			int col = rsm.getColumnCount();
			data = new Object[row][col];
			int j =0;
			while(rs.next()){
				for(int i=0;i<col;i++){
					data[j][i]=rs.getObject(i+1);
				}
			j++;
			}
		}catch(SQLException sqe){sqe.printStackTrace();}
		finally{closeCon();}
		return data;
	}
	public static String getOneFieldValue(String sql){
		String data = null;
		try{
			con = getConnection();
			stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = stat.executeQuery(sql);
			if(rs.next()){
				data = rs.getString(1);
			}
		}catch(SQLException sqe){sqe.printStackTrace();}
		finally{closeCon();}
		return data;
	}
	public static int getValue(String sql){
		int value = -1;
		try{
			con = getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			if(rs.next()){
				value = rs.getInt(1);
			}
		}catch(SQLException sq){sq.printStackTrace();}
		return value;
	}
}
