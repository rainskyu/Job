package coursework2.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class nicDB {
	
	  private static Connection connect = null;
//	  private Statement statement = null;
//	  private ResultSet resultSet = null;
	  private static String host="127.0.0.1";
	  private static String database="test";
	  private static String username="root";
	  private static String password="";
		
	  
	  public static Connection getConnection(){
			if(connect ==null){
				try{
				 Class.forName("com.mysql.jdbc.Driver");
			      String conn_string="jdbc:mysql://"+host+"/"+database;
			      Connection connect = DriverManager.getConnection(conn_string,username,password);
			      return connect;
				}catch(Exception ex){
					 return null;
					//ex.printStackTrace();
				}
			}else{
				return connect;
			}
	  }
	    
	  public boolean checkNICvalid(String nic) throws SQLException{
		  
		    boolean check=false;
		    Connection connect = getConnection();
		    Statement statement = connect.createStatement();
		    ResultSet rs = statement.executeQuery("SELECT * from NIC_RECORDS");
		    while(rs.next()) {
		    	//System.out.println("NIC "+rs.getString("NIC"));
		    	if(rs.getString("NIC").equals(nic)) {
		    		//System.out.println("valid");
		    		check=true;
		    		break;
		    	}
		    }
		    return check;
	  }
	  public boolean checkNICused(String nic) throws SQLException{
		    boolean check=false;
		    Connection connect = getConnection();
		    Statement statement = connect.createStatement();
		    ResultSet rs = statement.executeQuery("SELECT * from NIC_RECORDS");
		    while(rs.next()) {
		    	if(rs.getString("NIC").equals(nic)) {
		    		if(rs.getInt("USED")==0) {
		    			//System.out.println("unused");
		    			//System.out.println("used  "+rs.getInt("USED"));
		    			//System.out.println("nic  "+rs.getString("nic"));
			    		check=true;
			    		break;
		    		}
		    	}
		    }
		    return check;
	  }
	  
	  public boolean checkNICmps(String nic) throws SQLException{
		    boolean check=false;
		    Connection connect = getConnection();
		    Statement statement = connect.createStatement();
		    ResultSet rs = statement.executeQuery("SELECT * from NIC_RECORDS");
		    while(rs.next()) {
		    	if(rs.getString("NIC").equals(nic)) {
		    		if(rs.getInt("MP")==1) {
		    			//System.out.println("unused");
		    			//System.out.println("used  "+rs.getInt("USED"));
		    			//System.out.println("nic  "+rs.getString("nic"));
			    		check=true;
			    		break;
		    		}
		    	}
		    }
		    return check;
	  }
	  
	  
	  
	  public void updateused(String nic) throws SQLException {
		    Connection connect = getConnection();
		    Statement statement = connect.createStatement();
		    ResultSet rs = statement.executeQuery("SELECT * from NIC_RECORDS");
		    while(rs.next()) {
		    	if(rs.getString("NIC").equals(nic)) {
		    		statement.execute("UPDATE `NIC_RECORDS` SET USED="+1+" WHERE NIC='"+nic+"'");
		    		
		    		break;
		    	}
		    }
	  }
	  

	  
	  public boolean searchnumberofsign(String nic) throws SQLException {
		  boolean check=false;
		  Connection connect = getConnection();
		  Statement statement = connect.createStatement();
		  ResultSet rs = statement.executeQuery("SELECT * from NIC_RECORDS");
		  while(rs.next()) {
			  if(rs.getString("NIC").equals(nic))
				  if(rs.getInt("NUMBEROFSING")==1) {
				  check=true;
				  break;
			  }
		  }
		  
		  return check;
	  }
		

}
