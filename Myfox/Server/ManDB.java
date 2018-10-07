package Myfox.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.sql.*;

public class ManDB {
   		// JDBC driver name and database DB_URL
	    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	    public static final String DB_URL = "jdbc:mysql://localhost:3306/MyfoxDB?autoReconnect=true&useSSL=false";
	    public static Connection conn;

   		//  Database credentials -- 数据库名和密码自己修改
	    public static final String USER = "root";
		public static final String PASS = "vti228**wp";

   		public static void connect(ServerSyn syn1) {
		   	   
		   	   Statement stmt = null;
			   try{
			      Class.forName("com.mysql.cj.jdbc.Driver"); 

			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			   }catch(Exception e){
			      synchronized(syn1.synobj1){
			      		e.printStackTrace();
			      		syn1.connectdbstate = false;
			      		syn1.synobj1.notify();
			      }
			      
			   }
			   synchronized(syn1.synobj1){
			      		syn1.connectdbstate = true;
			      		syn1.synobj1.notify();
			   }
		}

		public static String checkaccount(int id,String pwd){
			try{
					Statement stmt = null;
				  stmt = conn.createStatement();

			      String sql;
			      sql = "SELECT accname FROM accountinfo where " + "accnum " + "=" + id + " and " +" accpwd " + " = " + "\'"+pwd+"\'";
			      
			      System.out.println("ManDB.checkaccount:"+sql);
			      ResultSet rs = stmt.executeQuery(sql);
			      

			      String accnamestr = new String();
			      //STEP 5: Extract data from result set
			      if(rs.next()){
			      	accnamestr = rs.getString("accname");
			      	stmt.close();
			      	rs.close();
			      	return accnamestr;
			      }
			      else{
			      	stmt.close();
			      	rs.close();
			      	return null;
			      }
			      //STEP 6: Clean-up environment
			      
			     
			      
			}
			catch(SQLException se){
				System.out.println("登录账号信息异常-------------------------");
				
				se.printStackTrace();
				return null;
			}
			
			
			
		}

		//back : accnum, 0:error, >0:ok
		public static int addaccount(String name,String pwd){
		    try{
		    	Statement stmt = null;
				stmt = conn.createStatement();
		      	String sql = "insert accountinfo values(" + "NULL," +"\'" + name+"\'"+","+"\'"+pwd+"\'"+","+"\'normal\')";   
		      	System.out.println("ManDB.addaccount:"+sql);
		      	stmt.executeUpdate(sql);
							
				
				sql = "select max(accnum) from accountinfo";
				System.out.println("ManDB.addaccount:"+sql);
				ResultSet rs = stmt.executeQuery(sql);
				int genid = 0;
				if(rs.next()){
					genid = rs.getInt("max(accnum)");
				}
				stmt.close();
				rs.close();
				return genid;

				//return true;
			}catch(SQLException se){
				System.out.println("---------------Insert 异常");
				se.printStackTrace();
				return 0;
			}
		}

		public static String queryfriend(int id){
			try{
				Statement stmt = null;
				stmt = conn.createStatement();
				List<String> datalist = new ArrayList<String>();
				ResultSet rs = null;
		      	String sql = "Select accountinfo.accnum,accountinfo.accname from accountinfo inner join relation " + 
        " where accountinfo.accnum = relation.relanum and  relation.accnum = " + id +
        " union " + "Select accountinfo.accnum,accountinfo.accname from accountinfo inner join relation " +
        " where accountinfo.accnum = relation.accnum and relation.relanum = " + id;
		      	rs = stmt.executeQuery(sql);
				
		      	System.out.println("ManDB.queryfriend:"+sql);
				
				String resultstr = new String();
				int flag = 0;
				
					while(rs.next()){

						int accnum = rs.getInt("accnum");
						String accname = rs.getString("accname").trim();
						//state: online or offline
						String state = "0";
						if(ManMess.id2ch(accnum) != null)state = "1";
						if(flag == 0){
							flag = 1;
							resultstr = String.valueOf(accnum) + "==" + accname + "==" + state;
						}
						else{
							resultstr = resultstr + "++" + String.valueOf(accnum) + "==" + accname + "==" + state;
						}
						
					}
					
				System.out.println("ManDB.queryfriend resultstr : "+resultstr);
				stmt.close();
				rs.close();
				return resultstr;
			}catch(SQLException se){
				System.out.println("---------------query 异常");
				se.printStackTrace();
				return null;
			}
		}
		public static boolean addfriend(int sid,int rid){
			try{
				Statement stmt = null;
				stmt = conn.createStatement();
		      	String sql = "insert relation " + " values " + "(" + "\'" + sid +"\'"+ "," +"\'"+ rid +"\'"+ ")";
		      	System.out.println("ManDB.addfriend:"+sql);

		      	stmt.executeUpdate(sql);


							
				stmt.close();
				return true;
			}catch(SQLException se){
				System.out.println("---------------Insert 异常");
				se.printStackTrace();
				return false;
			}
		}
		public static String queryaccount(int id){
			try{
				Statement stmt = null;
				stmt = conn.createStatement();
				
				ResultSet rs = null;
		      	String sql = "select accname from accountinfo where accnum = "+id;
		      	rs = stmt.executeQuery(sql);

		      	System.out.println("ManDB.queryaccount:"+sql);

		      	String state = "0";
				if(ManMess.id2ch(id) != null)state = "1";

				String resultstr = new String();
				if(rs.next()){
					resultstr = rs.getString("accname");
					resultstr = resultstr + "==" + state;
				}
				System.out.println("ManDB.queryaccount result :"+resultstr);
				
				stmt.close();
				rs.close();
				return resultstr;
			}catch(SQLException se){
				System.out.println("---------------query 异常");
				se.printStackTrace();
				return null;
			}
		}


}