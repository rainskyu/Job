package coursework2.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class petitionDB {
	
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
			    	ResultSet set =connect.getMetaData().getTables(null, null, "PETITIONS", null);
				    ){		
				if(!set.next()) {
			    		statement.execute("CREATE TABLE `PETITIONS` (\r\n" + 
			    				"  `ID` int(11) NOT NULL,\r\n" + 
			    				"  `TITLE` varchar(80) DEFAULT NULL,\r\n" + 
			    				"  `CONTENT` longtext,\r\n" + 
			    				"  `DATE` date DEFAULT NULL,\r\n" + 
			    				"  `CREATOR` varchar(45) DEFAULT NULL,\r\n" + 
			    				"  `SIGN` int(11) DEFAULT NULL,\r\n" + 
			    				"  PRIMARY KEY (`ID`)\r\n" + 
			    				") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
				}
			    	}catch(Exception ex){
			    		ex.printStackTrace();
			    	}
		}
	    public void updateDatabase(petition petition){
	    	
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
	    	  ResultSet set =connect.getMetaData().getTables(null, null, "PETITIONS", null);
		    ){
statement.execute("INSERT INTO `PETITIONS` VALUES ("+petition.getID()+",'"+petition.getTitle()+"','"+petition.getContent()+"','"+petition.getDate()+"','"+petition.getCreator()+"','"+petition.getSign()+"','"+petition.getComment()+"')");
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	    
	    public boolean checktitlecontent(String title,String content) throws SQLException {
	    	boolean check=false;
	    	Connection connect = getConnection();
	    	Statement statement = connect.createStatement();
	    	ResultSet rs = statement.executeQuery("SELECT * from PETITIONS");
	    	while(rs.next()) {
	    		if(rs.getString("TITLE")==title&&rs.getString("CONTENT")==content) {
	    			check=true;
	    			break;
	    		}
	    		
	    		
	    	}
	    	
	    	
	    	return check;
	    }
	    

	    
	    
	    
	    public void updateSign(String id,int sign){
	    	
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
	    	  ResultSet set =connect.getMetaData().getTables(null, null, "PETITIONS", null);
		    ){
	    		statement.execute("UPDATE `PETITIONS` SET SIGN="+sign+" WHERE ID="+String.valueOf(id));
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	    public void updatecomment(String id,int comment){
	    	
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
	    	  ResultSet set =connect.getMetaData().getTables(null, null, "PETITIONS", null);
		    ){
	    		statement.execute("UPDATE `PETITIONS` SET COMMENT="+comment+" WHERE ID="+String.valueOf(id));
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	    
	    public void updateEdite(String title,String content,String id){
	    	//System.out.println("here "+id);
	    	try(
		      // setup the connection with the DB.
		      Connection connect = getConnection();
		      // statements allow to issue SQL queries to the database
		      Statement statement = connect.createStatement();
	    	  ResultSet set =connect.getMetaData().getTables(null, null, "PETITIONS", null);
		    ){
	    		//statement.execute("UPDATE PETITIONS SET TITLE="+title+", "+"CONTENT="+content+" WHERE ID="+String.valueOf(id));
	    		statement.execute("UPDATE `PETITIONS` SET `TITLE`='"+title+"'"+","+"CONTENT='"+content+"'"+ " Where ID="+String.valueOf(id));
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	    
	    
	    
	    
	    
	    /*public petition addSigntorow(int id,int sign) {
	    	petition temp=null;
	    	try {
	    		Connection connect = getConnection();
	    		Statement statement = connect.createStatement();
	    		ResultSet resultSet = statement.executeQuery("SELECT * from PETITIONS");
	    		while(resultSet.next()) {
	    			if(id==resultSet.getInt("ID")) {
	    				//System.out.println("check");
	    				temp=new petition();
	    				temp.setID(id);
	    				temp.setTitle(resultSet.getString("TITLE"));
	    				temp.setContent(resultSet.getString("CONTENT"));
	    				temp.setCreator(resultSet.getString("CREATOR"));
	    				temp.setDate(resultSet.getDate("DATE"));
	    				temp.setSign(sign);
	    				System.out.println("ID "+resultSet.getInt("ID"));
	    				System.out.println("ID "+resultSet.getString("TITLE"));
	    				System.out.println("ID "+resultSet.getString("CONTENT"));
	    				System.out.println("ID "+resultSet.getString("CREATOR"));
	    				System.out.println("ID "+resultSet.getDate("DATE"));
	    			}
	    		}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
			return temp;
	    	
	    }*/
		public int searchsign(int id){
			int temp=0;
			try {
			      // this will load the MySQL driver, each DB has its own driver
			      // setup the connection with the DB.
			      Connection connect = getConnection();
			      // statements allow to issue SQL queries to the database
			      Statement statement = connect.createStatement();
			      // resultSet gets the result of the SQL query
			      ResultSet resultSet = statement.executeQuery("SELECT * from PETITIONS");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  if(resultSet.getInt("ID")==id) {
			    		   temp=resultSet.getInt("SIGN");
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
			return temp;
		}
		public int searchcomment(int id){
			int temp=0;
			try {
			      // this will load the MySQL driver, each DB has its own driver
			      // setup the connection with the DB.
			      Connection connect = getConnection();
			      // statements allow to issue SQL queries to the database
			      Statement statement = connect.createStatement();
			      // resultSet gets the result of the SQL query
			      ResultSet resultSet = statement.executeQuery("SELECT * from PETITIONS");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  if(resultSet.getInt("ID")==id) {
			    		  System.out.println(resultSet.getDate(4).getDate());
			    		   temp=resultSet.getInt("COMMENT");
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
			return temp;
		}
	    
		public int searchID(String title,String content){
			int temp=0;
			try {
			      // this will load the MySQL driver, each DB has its own driver
			      // setup the connection with the DB.
			      Connection connect = getConnection();
			      // statements allow to issue SQL queries to the database
			      Statement statement = connect.createStatement();
			      // resultSet gets the result of the SQL query
			      ResultSet resultSet = statement.executeQuery("SELECT * from PETITIONS");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  if(resultSet.getString("TITLE")==title&&resultSet.getString("CONTENT")==content) {
			    		   temp=resultSet.getInt("ID");
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
			return temp;
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
			      ResultSet resultSet = statement.executeQuery("SELECT * from PETITIONS");      
			      // resultSet is initialised before the first data set
			 
			      while(resultSet.next()){
			    	  
			    	   // it also is possible to get the columns via name
			          // also possible to get the columns via the column number
			          // which starts at 1
			          // e.g., resultSet.getString(2) is the same as resultSet.getString("username"); 
			    	  rows = resultSet.getInt("ID");
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
