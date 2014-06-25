package com.tf.mobile;
import java.sql.*;
//import javax.sql.*;
import java.util.Vector;
public class USeDB {
	private static DBUtil db = null;
	private static Connection con = null;
	private static Statement sta = null;
	private static PreparedStatement prs = null;
	private static ResultSet rs = null;
	public USeDB() {
		// TODO 自动生成构造函数存根
	}
	//用户登录
	public static boolean logoon(String name,String password){
		boolean b = false;
		b = DBUtil.check(name,password);
		return b;
	}
	//删除用户
	public static boolean delUser(String name){
		if(DBUtil.isExist("select user_name from user where user_name='"+name+"'")){
			DBUtil.update("delete from user where user_name='"+name+"'");
			return true;
		}
		return false;
	}
	//更新数据库不返回任何数据
	public static void UpdateDB(String sql){
		if(sql.length()<=0){return;}
		try{
			db = new DBUtil();
			con = db.getConnection();
			sta = con.createStatement();
			sta.executeUpdate(sql);
		}catch(SQLException e){e.printStackTrace();}
		finally{db.closeCon();}
	}
	//更新并返回数据
	public static boolean Update(String sql){
		boolean b = false;
		if(sql.length()<=0){return b;}
		try{
			db = new DBUtil();
			con = db.getConnection();
			sta = con.createStatement();
			b =sta.execute(sql);
			if(b){return b;}
		}catch(SQLException e){e.printStackTrace();}
		finally{db.closeCon();}
		return b;
	}
	//获取向量
	public static Vector getTable(String sql){
		Vector str = new Vector();
		Vector vector = new Vector();
		try{
			db = new DBUtil();
			con = db.getConnection();
			sta = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = sta.executeQuery(sql);
			rs.last();
			int row = rs.getRow();
			rs.beforeFirst();
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
		finally{db.closeCon();}
		return str;
	}
	//获取数组
	public static Object[][] getData(String sql){
		Object data[][] = null;
		//Object oo [] = null;
		//Vector v = null;
		//Vector vv = new Vector();
		try{
			db = new DBUtil();
			con = db.getConnection();
			sta = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs = sta.executeQuery(sql);
			rs.last();
			int row = rs.getRow();
			rs.beforeFirst();
			java.sql.ResultSetMetaData rsm = rs.getMetaData();
			int col = rsm.getColumnCount();
			data = new Object[row][col];
			int j =0;
			//v = new Vector();
			while(rs.next()){
				/*for(int i=1;i<=col;i++){
					v.add(rs.getObject(i));
				}
				oo = new Object[v.size()-1];
				oo = v.toArray();
				v.clear();
				for(int n=0;n<col;n++){
					data[j][n] = oo[n];
				}*/
				for(int i=0;i<col;i++){
					data[j][i]=rs.getObject(i+1);
				}
			j++;
			}
		}catch(SQLException sqe){sqe.printStackTrace();}
		finally{db.closeCon();}
		return data;
	}
	
}
