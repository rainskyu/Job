package coursework2.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class signDB {
	
	
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
		
		public void create() {
			
			try(
					Connection connect = getConnection();
				    Statement statement = connect.createStatement();
			    	ResultSet set =connect.getMetaData().getTables(null, null, "SIGN", null);
				    ){		
				if(!set.next()) {
			    		statement.execute("CREATE TABLE `SIGN`(\r\n" + 
			    				"  `ID` int(11) NOT NULL,\r\n" + 
			    				"  `PETITIONID` int(11) NOT NULL,\r\n" + 
			    				"  `NIC` varchar(80) DEFAULT NULL,\r\n" + 
			    				"  PRIMARY KEY (`ID`)\r\n" + 
			    				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
				}
			    	}catch(Exception ex){
			    		ex.printStackTrace();
			    	}
		}
		
		public sign returnsign(int id,String nic) throws SQLException {
			sign s=null;
			Connection connect = getConnection(); 
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from PETITIONS");
			while(rs.next()) {
				//System.out.println(rs.getInt("ID"));
				if(rs.getInt("ID")==id) {
					s=new sign(rs.getString("TITLE"),rs.getString("CONTENT"),nic);
					break;
		    	}
			}
			return s;
		}
		
		public boolean isempty(String creator) throws SQLException {
			boolean check=true;
			int count =0;
			Connection connect = getConnection();
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from SIGN_"+creator);
			while(rs.next()) {
				count = rs.getInt("ID");
			}
			if(count!=0) {
				check=false;
			}
			return check;
		}
		
	    public void updatesign(String nic, int petitionid,int id){
	    	
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
		    ){
	    		/*System.out.println(sign.getId());
	    		System.out.println(sign.getTitle());
	    		System.out.println(sign.getContent());
	    		System.out.println(sign.getNIC());*/
	//statement.execute("INSERT INTO `SIGN_"+sign.getNIC()+"` VALUES ("+sign.getId()+",'"+sign.getTitle()+"','"+sign.getContent()+"','"+sign.getNIC()+"','"+"')");
	    		statement.execute("INSERT INTO `SIGN` VALUES ("+id+",'"+petitionid+"','"+nic+"')");  		
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	
	    
		public boolean search(int petition,String nic) throws SQLException {
			boolean check=true;
			Connection connect = getConnection();
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from SIGN");
				while(rs.next()/*||isempty(creator)*/) {
					//System.out.println("title "+rs.getString("TITLE")+"  "+title);
					//System.out.println("content "+rs.getString("CONTENT")+"  "+content);
					/*if(isempty(creator)) {
						check=true;
						break;
					}else*/
					if((Integer.valueOf(rs.getString("PETITIONID"))==petition&&rs.getString("NIC").equals(nic))) {
						//System.out.println("ss");
						check=false;
						break;
					}
			}
			return check;
		}
		
		public int searchId(String creator) throws SQLException {
			int temp=0;
			Connection connect = getConnection();
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from SIGN");
			while(rs.next()) {
				temp=rs.getInt("ID");
			}
			
			
			
			return temp;
		}
			
	    

}
