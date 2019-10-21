package coursework2.bean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class citizenDB {
	
	  private static Connection connect = null;
//	  private Statement statement = null;
//	  private ResultSet resultSet = null;
	  private static String host="127.0.0.1";
	  private static String database="test";
	  private static String username="root";
	  private static String password="";

	/*public static void main(String args[]){
		
		citizenDB app=new citizenDB();
		getConnection();
		//app.searchDatabase();
		app.updateDatabase();
		app.searchDatabase();
		app.preparedStatementSearch(2);
		
	}*/
	
	
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
		    	ResultSet set =connect.getMetaData().getTables(null, null, "CITIZEN", null);
			    ){		
			if(!set.next()) {
		    		statement.execute("CREATE TABLE `CITIZEN` (\r\n" + 
		    	  	      		"  `ID` int(11) NOT NULL,\r\n" + 
		    	  	      		"  `Fname` varchar(80) DEFAULT NULL,\r\n" + 
		    	  	      		"  `Sname` varchar(80) DEFAULT NULL,\r\n" +
		    	  	      		"  `Email` varchar(100) DEFAULT NULL,\r\n" +
		    	  	      		"  `Password` varchar(500) DEFAULT NULL,\r\n" +
		    	  	      		"  `Address` varchar(500) DEFAULT NULL,\r\n" +
		    	  	      		"  `Dateofbirth` varchar(300) DEFAULT NULL,\r\n" + 
		    	  	      		"  `NIC` varchar(80) DEFAULT NULL,\r\n" +  
		    	  	      		"  PRIMARY KEY (`ID`)\r\n" + 
		    	  	      		") ENGINE=InnoDB DEFAULT CHARSET=utf8;");		
		    		}
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}
	}
	
	    public boolean updateDatabase(register citizen){
	    	boolean check=false;
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
	    	  ResultSet set =connect.getMetaData().getTables(null, null, "CITIZEN", null);
		    ){
	    		statement.execute("INSERT INTO `CITIZEN` VALUES ("+citizen.getID()+",'"+citizen.getFname()+"','"+citizen.getSname()+"','"+citizen.getEmail()+"','"+citizen.getPassword()+"','"+citizen.getFulladdress()+"','"+citizen.getDataofbirth()+"','"+citizen.getNIC()+"')");
	    		check=true;
	    		/*if(set.next()) {
	    			statement.execute("INSERT INTO `CITIZEN` VALUES ("+citizen.getID()+",'"+citizen.getFname()+"','"+citizen.getSname()+"','"+citizen.getEmail()+"','"+citizen.getPassword()+"','"+citizen.getFulladdress()+"','"+citizen.getDataofbirth()+"','"+citizen.getNIC()+"')");
	    		}else{
	    			statement.execute("CREATE TABLE `CITIZEN` (\r\n" + 
	    	  	      		"  `ID` int(11) NOT NULL,\r\n" + 
	    	  	      		"  `Fname` varchar(80) DEFAULT NULL,\r\n" + 
	    	  	      		"  `Sname` varchar(80) DEFAULT NULL,\r\n" +
	    	  	      		"  `Email` varchar(100) DEFAULT NULL,\r\n" +
	    	  	      		"  `Password` varchar(500) DEFAULT NULL,\r\n" +
	    	  	      		"  `Address` varchar(500) DEFAULT NULL,\r\n" +
	    	  	      		"  `Dateofbirth` varchar(300) DEFAULT NULL,\r\n" + 
	    	  	      		"  `NIC` varchar(80) DEFAULT NULL,\r\n" +  
	    	  	      		"  PRIMARY KEY (`ID`)\r\n" + 
	    	  	      		") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
	    			statement.execute("INSERT INTO `CITIZEN` VALUES ("+citizen.getID()+",'"+citizen.getFname()+"','"+citizen.getSname()+"','"+citizen.getEmail()+"','"+citizen.getPassword()+"','"+citizen.getFulladdress()+"','"+citizen.getDataofbirth()+"','"+citizen.getNIC()+"')");
	    		}*/
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    	return check;
	    }
	    

	    
	    
	    /*public void preparedStatementSearch(int studentID){
	    	String sql="SELECT * from Student WHERE ID=?";
	    	try( Connection connect = getConnection(); 
		    	 PreparedStatement pstmt = connect.prepareStatement(sql);
	    		){	
		    	 pstmt.setInt(1,studentID);
		    	 try (ResultSet rs = pstmt.executeQuery();){
		    	   while(rs.next()){
			    	  String name=rs.getString("Name");			    	  
			    	  String address=rs.getString("Address");  	  
			    	  System.out.println("name:"+name+" address:"+address); 
			      }
		    	 }
	    	}catch(SQLException ex){
	    		ex.printStackTrace();	
	    	}	 
	    }*/
		public boolean checkemailinvalid(String email){
			boolean check=false;
			try {
			      // this will load the MySQL driver, each DB has its own driver
			      // setup the connection with the DB.
			      Connection connect = getConnection();
			      // statements allow to issue SQL queries to the database
			      Statement statement = connect.createStatement();
			      // resultSet gets the result of the SQL query
			      ResultSet resultSet = statement.executeQuery("SELECT * from CITIZEN");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  if(resultSet.getString("EMAIL").equals(email)) {
			    		   check=true;
			    		   break;
			    	  }
			    	   // it also is possible to get the columns via name
			          // also possible to get the columns via the column number
			          // which starts at 1
			          // e.g., resultSet.getString(2) is the same as resultSet.getString("username"); 
			    	  //rows = Integer.valueOf(resultSet.getString("ID"));
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
			return check;
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
			      ResultSet resultSet = statement.executeQuery("SELECT * from CITIZEN");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  
			    	   // it also is possible to get the columns via name
			          // also possible to get the columns via the column number
			          // which starts at 1
			          // e.g., resultSet.getString(2) is the same as resultSet.getString("username"); 
			    	  rows = Integer.valueOf(resultSet.getString("ID"));
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
		
		
//		public boolean checkUser(String user, String pass){
//			
//			try {
//
//			      Class.forName("com.mysql.jdbc.Driver");
//			      connect = DriverManager.getConnection("jdbc:mysql://localhost/AlstomDB","root","server2007!");
//			      statement = connect.createStatement();
//			      resultSet = statement.executeQuery("SELECT * from AlstomDB.User");
//
//			      while(resultSet.next()){
//	
//			    	  String username=resultSet.getString("username");			    	  
//			    	  String password=resultSet.getString("password");
//			    	  
//			    	  if(user!=null && password!=null){
//			    		  if(user.equals(username) && pass.equals(password)){
//			    			  return true;
//			    		  }
//			    	  }
//			    	  
//			      }
//			      
//			   
//			}catch(Exception ex){
//				return false;
//			}
//			
//			return false;
//			
//			
//		}
	

}
