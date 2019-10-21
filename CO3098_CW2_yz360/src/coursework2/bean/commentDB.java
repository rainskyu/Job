package coursework2.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class commentDB {
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
			    	ResultSet set =connect.getMetaData().getTables(null, null, "COMMENTS", null);
				    ){		
				if(!set.next()) {
			    		statement.execute("CREATE TABLE `COMMENTS` (\r\n" + 
			    				"  `ID` int(11) NOT NULL AUTO_INCREMENT,\r\n" + 
			    				"  `COMMENT` longtext,\r\n" + 
			    				"  `BY_MP` varchar(50) DEFAULT NULL,\r\n" + 
			    				"  `PETITIONID` int(11) NOT NULL,\r\n" + 
			    				"  PRIMARY KEY (`ID`)\r\n" + 
			    				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
				}
			    	}catch(Exception ex){
			    		ex.printStackTrace();
			    	}
		}
		
	    public void Insert(comment comment,int id){
	    	
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
	    	  ResultSet set =connect.getMetaData().getTables(null, null, "COMMENTS", null);
		    ){
statement.execute("INSERT INTO `COMMENTS` VALUES ("+comment.getId()+",'"+comment.getContent()+"','"+comment.getMps()+"','"+id+"')");
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	    
	    
		public int countID(){
			int rows=0;
			try {
			      // this will load the MySQL driver, each DB has its own driver
			      // setup the connection with the DB.
			      Connection connect = getConnection();
			      // statements allow to issue SQL queries to the database
			      Statement statement = connect.createStatement();
			      // resultSet gets the result of the SQL query
			      ResultSet resultSet = statement.executeQuery("SELECT * from COMMENTS");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  
			    	   // it also is possible to get the columns via name
			          // also possible to get the columns via the column number
			          // which starts at 1
			          // e.g., resultSet.getString(2) is the same as resultSet.getString("username"); 
			    	  rows = Integer.valueOf(resultSet.getString("ID"));
			    	 /* System.out.println("id "+resultSet.getInt("ID"));
			    	  System.out.println("TITLE "+resultSet.getString("TITLE"));
			    	  System.out.println("CONTENT "+resultSet.getString("CONTENT"));
			    	 /* String name=resultSet.getString("Name");			    	  
			    	  String address=resultSet.getString("Address");
			      	  System.out.println("name:"+name+" address:"+address); */ 
			    	  
			      }
			      //System.out.println(rows);
			   
			}catch(Exception ex){
				ex.printStackTrace();
			}
			  return rows;
		}
}
