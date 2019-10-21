																																							package coursework2.bean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserVerification {
	
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
	  public register checkcitizen2(String user,String password){
		  
		  String sql="SELECT * from CITIZEN WHERE Email=? AND Password=?";
		  register u=null;
	    	try( Connection connect = getConnection(); 
		    	 PreparedStatement pstmt = connect.prepareStatement(sql);
	    		){	
		    	 pstmt.setString(1,user);
		    	 pstmt.setString(2,MD5HashGenerator.getMD5Hash(password));
		    	 try (ResultSet rs = pstmt.executeQuery("SELECT * from CITIZEN");){
		    	   while(rs.next()){
		    		  if(rs.getString("Email").equals(user)&&rs.getString("Password").equals((password))) {
		    			  
			    	  String email=rs.getString("Email");		
			    	  String passwords=rs.getString("Password");
			    	  String fname = rs.getString("Fname");
			    	  String sname = rs.getString("Sname");
			    	  String address=rs.getString("Address");
			    	  String nic=rs.getString("NIC");
			    	  //System.out.println(fname);
			    	 // System.out.println(sname);
			    	  u=new register(email,passwords,fname,sname,address,nic);
			    	  break;
		    		  }
			    	
			      }
		    	 }
	    	}catch(SQLException ex){
	    		ex.printStackTrace();	
	    	}
	    	return u;
		  
	  }
	  public register checkcitizen(String user,String password){
		  
		  String sql="SELECT * from CITIZEN WHERE Email=? AND Password=?";
		  register u=null;
	    	try( Connection connect = getConnection(); 
		    	 PreparedStatement pstmt = connect.prepareStatement(sql);
	    		){	
		    	 pstmt.setString(1,user);
		    	 pstmt.setString(2,MD5HashGenerator.getMD5Hash(password));
		    	 try (ResultSet rs = pstmt.executeQuery("SELECT * from CITIZEN");){
		    	   while(rs.next()){
		    		  if(rs.getString("Email").equals(user)&&rs.getString("Password").equals(MD5HashGenerator.getMD5Hash(password))) {
		    			  
			    	  String email=rs.getString("Email");		
			    	  String passwords=rs.getString("Password");
			    	  String fname = rs.getString("Fname");
			    	  String sname = rs.getString("Sname");
			    	  String address=rs.getString("Address");
			    	  String nic=rs.getString("NIC");
			    	  //System.out.println(fname);
			    	 // System.out.println(sname);
			    	  u=new register(email,passwords,fname,sname,address,nic);
			    	  break;
		    		  }
			    	
			      }
		    	 }
	    	}catch(SQLException ex){
	    		ex.printStackTrace();	
	    	}
	    	return u;
		  
	  }
	  
	  public static void main(String[] args){
		 UserVerification DBoperator=new UserVerification();
		 DBoperator.checkcitizen("123", "123");
		  
	  }

}
