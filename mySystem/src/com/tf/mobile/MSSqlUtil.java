/**
Download by http://www.codefans.net 
 */
package com.tf.mobile;
//import java.util.*;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
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
public class MSSqlUtil{

	/**
	 * 
	 */
	private static String driver="net.sourceforge.jtds.jdbc.Driver";//�����������ַ���
	//�������ݿ������ַ���
	private static String url="jdbc:jtds:sqlserver://localhost:1433/HSRegistrations";
	//private static String url="jdbc:jtds:sqlserver://localhost:2335/tfweb";
	private static Connection con=null;//�������ݿ����Ӷ�������
	private static Statement stat=null;//��������������
	private static PreparedStatement psInsert=null;//����Ԥ��������������
	private static ResultSet rs=null;//�����������������
	public MSSqlUtil() {
		// TODO �Զ����ɹ��캯����� T1_1
	}
	public static Connection getConnection()//�õ����ݿ����ӵķ���
	{
		try
		{
			Class.forName(driver);//����������
			DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
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
	public static Vector<Vector<Object>> getTable(String sql){
		Vector<Vector<Object>> str = new Vector<Vector<Object>>();
		Vector<Object> vector = new Vector<Object>();
		try{
			con = getConnection();
			//stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stat = con.createStatement();
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
			//stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
			//rs.last();
			int row = rs.getRow();
			//rs.
			//rs.beforeFirst();
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
				//Clob clob = rs.getClob(1);
				value = rs.getInt(1);
			}
		}catch(SQLException sq){sq.printStackTrace();}
		return value;
	}
	public static void main(String[] args) throws SQLException, UnsupportedEncodingException, ClassNotFoundException {
		// TODO Auto-generated method stub
		/*Class.forName("net.sourceforge.jtds.jdbc.Driver");
        DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());

		Connection con =DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:2335/tfweb");
		//conn.createStatement()
		//con = java.sql.DriverManager.getConnection(dbUrl);
        
		Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM T1_1");
        
        System.out.println("<table border='1' width='400'>");
        while(rs.next()){
            String col1 = new String(rs.getString(1).getBytes("ISO8859-1"));
            String col2 = rs.getString(2);
            String col3 = rs.getString(3);
            String col4 = rs.getString(4);
            
            System.out.println("<tr><td>" + col1 + "</td><td>" + col2 + "</td><td>" + col3 + "</td><td>" + col4 + "</td></tr>");
        }
        
        System.out.println("</table>");
        
        rs.close();
        stmt.close();
        con.close();*/
		
		String sql = "select top 5 RegistrationKey,F100513,F300102,F300114,F300401 from T1_1 order by RegistrationKey desc";
		System.out.println("sql="+sql);
		/*Object[][] result = MSSqlUtil.getData(sql);
		for(int i=0;i<result.length;i++){
			//log.info(result[i][0]+"|");
			for(int j=0;j<result[0].length;j++){
				System.out.println(result[i][j]+"|");
			}
		}*/
		Vector<Vector<Object>> result = MSSqlUtil.getTable(sql);
		for(Vector<Object> res:result){
			for(Object obj:res){
				System.out.println(obj==null?"":obj .toString()+"|");
			}
			Clob clob = (Clob)res.get(4);
			System.out.println( clob.getSubString(1,1000));
		}
		
		//println(new Date());
	}
}
